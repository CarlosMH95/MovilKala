package com.example.kala.movilkala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import models.Token;
import models.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.RequestK;
import service.RestClient;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_username)
    EditText _usernameText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sesion;

    private static Retrofit retrofit = null;
    private RestClient restClient = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(getWindow() != null) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        if(getApplicationContext() != null) {
            sharedPreferences = getApplicationContext().getSharedPreferences("user_sesion", Context.MODE_PRIVATE);
            sesion = sharedPreferences.edit();
        }
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);//AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        final String username = _usernameText.getText().toString();
        final String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        if(retrofit == null){
            RequestK.init();
            restClient = RequestK.createService(RestClient.class, username, password);
            Call<Token> callToken = restClient.getToken(username, password);

            callToken.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {

                    if(response.isSuccessful()) {
                        Token data = response.body();
                        Log.e(TAG, "Token: " + data.getToken());
                        sesion.putString("token", data.getToken());

                        restClient = RequestK.createService(RestClient.class, data.getToken(), false);
                        Call<Usuario> callUsuario = restClient.getUsuario(data.getToken());

                        callUsuario.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                                if(response.isSuccessful()) {
                                    Usuario usuario = response.body();

                                    sesion.putString("nombre", usuario.getNombre());
                                    sesion.putString("apellido", usuario.getApellido());
                                    sesion.putString("cedula", usuario.getCedula());
                                    sesion.putString("user_id", usuario.getUsuario());
                                    sesion.commit();

                                    Log.e(TAG, sesion.toString());
                                    //Toast.makeText(getApplicationContext(), "Usuario: " + usuario.getUsuario() , Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    onLoginSuccess();
                                }
                                else{
                                    progressDialog.dismiss();
                                    onLoginFailed();
                                }


                            }

                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {
                                progressDialog.dismiss();
                                onLoginFailed();

                            }
                        });

                    }
                    else{
                        progressDialog.dismiss();
                        onLoginFailed();
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Log.e(TAG, t.toString());

                    progressDialog.dismiss();
                    onLoginFailed();
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);



        Intent intent = new Intent(this, ProgresoActivity.class);
        //intent.putExtra("ID", ContactLoggedIn.getContact().getId());
        //intent.putExtra("TYPE", "0");
        startActivity(intent);

        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty() || username.length() != 10) {
            _usernameText.setError("Ingrese número de cédula válido");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("Ingrese contraseña");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
