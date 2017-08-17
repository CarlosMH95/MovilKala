package com.example.kala.movilkala;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import resource.DrawerK;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sesion;
    private Drawer drawer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        if(getSupportActionBar() != null ) {
            getSupportActionBar().hide(); //.setDisplayShowTitleEnabled(false);
        }
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getApplicationContext() != null) {
            sesion = getApplicationContext().getSharedPreferences("user_sesion", Context.MODE_PRIVATE);
        }
        //if you want to update the items at a later time it is recommended to keep it in a variable
        drawer = DrawerK.initDrawer(this, CitaActivity.class, toolbar);
    }
}
