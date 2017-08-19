package com.example.kala.movilkala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ExpandableListView;

import com.alamkanak.weekview.WeekView;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.Dieta;
import models.Token;
import models.Usuario;
import resource.DrawerK;
import resource.ExpandableListAdapter;
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

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;

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
        restClient = RequestK.createService(RestClient.class, sesion.getString("token", ""), false);
        Call<List<Dieta>> call = restClient.getDietas(sesion.getString("cedula", ""));

        call.enqueue(new Callback<List<Dieta>>() {
            @Override
            public void onResponse(Call<List<Dieta>> call, Response<List<Dieta>> response) {

                if(response.isSuccessful()) {
                    List<Dieta> data = response.body();

                    for(Dieta dieta : data){

                        Log.e(TAG, dieta.getId() + " "+ dieta.getCondicionesPrevias()+" "+dieta.getPlanDiario()+"");
                    }
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
            public void onFailure(Call<List<Dieta>> call, Throwable t) {
                Log.e(TAG, t.toString());
                progressDialog.dismiss();

            }
        });

        //expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);
        // Setting group indicator null for custom indicator
        //expandableListView.setGroupIndicator(null);

        //setItems();
    //setListener();
        // Setting headers and childs to expandable listview


    }
    void setItems(){

        // Array list for header
        ArrayList<String> header = new ArrayList<String>();

        // Array list for child items
        List<String> child1 = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();
        List<String> child3 = new ArrayList<String>();
        List<String> child4 = new ArrayList<String>();

        // Hash map for both header and child
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();

        // Adding headers to list
        for (int i = 1; i < 5; i++) {
            header.add("Group " + i);
        }
        // Adding child data
        for (int i = 1; i < 5; i++) {
            child1.add("Group 1  " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 5; i++) {
            child2.add("Group 2  " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 6; i++) {
            child3.add("Group 3  " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 7; i++) {
            child4.add("Group 4  " + " : Child" + i);
        }

        // Adding header and childs to hash map
        hashMap.put(header.get(0), child1);
        hashMap.put(header.get(1), child2);
        hashMap.put(header.get(2), child3);
        hashMap.put(header.get(3), child4);

        adapter = new ExpandableListAdapter(DietaActivity.this, header, hashMap);
        // Setting adpater over expandablelistview
        expandableListView.setAdapter(adapter);

    }
}
