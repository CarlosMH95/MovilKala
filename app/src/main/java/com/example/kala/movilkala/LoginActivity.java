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
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import models.Result;
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
        }

        /*_signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });*/
    }

    public void login() {
        Log.e(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        Log.e(TAG, "OK 0.1");
        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);//AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        if(retrofit == null){
            RequestK.init();
            //restClient = retrofit.create(RestClient.class);
            Log.e(TAG, "OK 1");
            restClient = RequestK.createService(RestClient.class, "0987654321", "administrador1");
            Log.e(TAG, "OK 2");
            Call<String> call = restClient.autenticar("0987654321:administrador1");
            Log.e(TAG, "OK 3");

            Log.e(TAG, "OK 4 " + call.request().toString() + "_" + call.request().headers().toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()) {
                        String data = response.body();
                        Log.e(TAG, data.toString());
                        Toast.makeText(getApplicationContext(), data.toString() , Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        onLoginSuccess();
                    }
                    else{
                        Log.e(TAG, response.toString()+ "_" + response.body() + "_"+response.message() + "_" + response.headers() + "_" + response.code());
                        progressDialog.dismiss();
                        onLoginFailed();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error! " + t.toString() , Toast.LENGTH_LONG).show();
                    Log.e(TAG, t.toString());
                }
            });
        }

        /*new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);*/
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

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", "value");
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
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

        if (username.isEmpty() || username.length() != 1) {
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
