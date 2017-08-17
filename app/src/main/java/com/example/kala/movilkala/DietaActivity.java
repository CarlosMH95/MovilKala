package com.example.kala.movilkala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.alamkanak.weekview.WeekView;
import com.mikepenz.materialdrawer.Drawer;

import models.Token;
import models.Usuario;
import resource.DrawerK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.RequestK;
import service.RestClient;

/**
 * Created by HouSe on 17/08/2017.
 */

public class DietaActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Drawer drawer = null;

    private RestClient restClient = null;
    SharedPreferences sesion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);


        if(getApplicationContext() != null) {
            sesion = getApplicationContext().getSharedPreferences("user_sesion", Context.MODE_PRIVATE);
        }

        drawer = DrawerK.initDrawer(this, DietaActivity.class, toolbar);

        final ProgressDialog progressDialog = new ProgressDialog(DietaActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);//AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Actualizando...");
        progressDialog.show();

        RequestK.init();
        restClient = RequestK.createService(RestClient.class, sesion.getString("token", ""));
        Call<String> call = restClient.getPlanesDieta(sesion.getString("46", ""));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()) {
                    String data = response.body();
                    Log.e(TAG, data.toString());
                    //Toast.makeText(getApplicationContext(), "Usuario: " + usuario.getUsuario() , Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    //onLoginSuccess();
                }
                else{
                    Log.e(TAG, "Not Succesful " + response.toString() + " " + response.body().toString());
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.toString());
                progressDialog.dismiss();

            }
        });

    }
}
