package com.example.kala.movilkala;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;

import resource.DrawerK;

/**
 * Created by HouSe on 17/08/2017.
 */

public class ProgresoActivity extends AppCompatActivity {

    private SharedPreferences sesion;
    private Drawer drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_progreso);
        toolbar.inflateMenu(R.menu.view_update);

        View updateView = findViewById(R.id.update);
        updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(getApplicationContext() != null) {
            sesion = getApplicationContext().getSharedPreferences("user_sesion", Context.MODE_PRIVATE);
        }

        drawer = DrawerK.initDrawer(this, toolbar);
    }
}
