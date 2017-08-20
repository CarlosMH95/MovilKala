package com.example.kala.movilkala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by HouSe on 12/08/2017.
 */

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getApplicationContext() != null) {
            sesion = getApplicationContext()
                    .getSharedPreferences("user_sesion", Context.MODE_PRIVATE);

            if(sesion.contains("token")){
                startActivity(new Intent(SplashActivity.this, ProgresoActivity.class));
                //Toast.makeText(getApplicationContext(), "OK " + sesion.getString("token","_") , Toast.LENGTH_LONG).show();
            }
            else{
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                //Toast.makeText(getApplicationContext(), "NOK " + sesion.getString("token","_") , Toast.LENGTH_LONG).show();
            }
            finish();
        }


    }
}