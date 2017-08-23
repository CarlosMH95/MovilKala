package com.example.kala.movilkala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mikepenz.materialdrawer.Drawer;

import java.util.List;

import models.Dieta;
import models.PlanDiario;
import resource.DrawerK;
import resource.ExpandableView;
import resource.ExpandedListItemView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.RequestK;
import service.RestClient;

/**
 * Created by HouSe on 17/08/2017.
 */

public class DietaActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Drawer drawer = null;
    private View expandableView = null;
    private ExpandableView dietaExpandableView;
    private ExpandableView planDiarioExpandableView;
    private ExpandableView diasExpandableView;

    private RestClient restClient = null;
    SharedPreferences sesion;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dieta);

        if(getApplicationContext() != null) {
            sesion = getApplicationContext().getSharedPreferences("user_sesion", Context.MODE_PRIVATE);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_dietas));
        toolbar.inflateMenu(R.menu.view_update);

        drawer = DrawerK.initDrawer(this, toolbar);

        View updateView = findViewById(R.id.update);
        updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerDietas();
            }
        });

        obtenerDietas();
    }

    public void obtenerDietas(){
        final ProgressDialog progressDialog = new ProgressDialog(DietaActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.updating));
        progressDialog.show();

        RequestK.init();
        restClient = RequestK.createService(RestClient.class, sesion.getString("token", ""), false);
        Call<List<Dieta>> call = restClient.getDietas(sesion.getString("cedula", ""));

        call.enqueue(new Callback<List<Dieta>>() {
            @Override
            public void onResponse(Call<List<Dieta>> call, Response<List<Dieta>> response) {

                if(response.isSuccessful()) {
                    List<Dieta> data = response.body();
                    mostrarDietas(data);
                    progressDialog.dismiss();
                }
                else{
                    mostrarSinInformacion();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Dieta>> call, Throwable t) {
                mostrarSinInformacion();
                progressDialog.dismiss();
            }
        });
    }

    private void mostrarDietas(List<Dieta> dietas) {
        if(dietas == null || dietas.isEmpty()) return;

        if(expandableView == null) {

            for (Dieta dieta : dietas) {
                LinearLayout parent = (LinearLayout) findViewById(R.id.parent_expandable_id);
                expandableView = getLayoutInflater().inflate(R.layout.expandable_child_view, null);
                dietaExpandableView = (ExpandableView) expandableView;
                planDiarioExpandableView = new ExpandableView(this);
                diasExpandableView = new ExpandableView(this);

                crearDietaExpandableView(dieta);
                crearPlandDiarioExpandableView(dieta.getPlanDiario());

                planDiarioExpandableView.setOutsideContentLayout(dietaExpandableView.getContentLayout());
                diasExpandableView.setOutsideContentLayout(planDiarioExpandableView.getContentLayout());

                parent.addView(expandableView);
            }
        }
    }

    public void agregarContentView(ExpandableView view, String[] stringList, boolean showCheckbox) {

        for (int i = 0; i < stringList.length; i++) {
            ExpandedListItemView itemView = new ExpandedListItemView(this);
            itemView.setText(stringList[i], showCheckbox);
            view.addContentView(itemView);
        }
    }

     private void crearDietaExpandableView(Dieta dieta){

         dietaExpandableView.fillData(0, "Dieta: " + dieta.getId(), true);
         dietaExpandableView.getTextView().setTextColor(getResources().getColor(R.color.colorAccent));
         String[] condiciones = {"Condiciones previas: " + dieta.getCondicionesPrevias()};
         agregarContentView(dietaExpandableView, condiciones, false);
         dietaExpandableView.addContentView(planDiarioExpandableView);
    }

    private void crearPlandDiarioExpandableView(List<PlanDiario> planes) {

        planDiarioExpandableView.setBackgroundResource(R.color.light_gray);
        planDiarioExpandableView.fillData(0, "    Plan Diario", false);

        for(PlanDiario plandiario: planes){
            ExpandableView diaExpandableView = new ExpandableView(this);
            diaExpandableView.fillData(0, "         "+plandiario.getDia(), false);
            String[] plan ={"       Desayuno: " + plandiario.getDesayuno(),
                            "       Colación 1: " + plandiario.getColacion1(),
                            "       Almuerzo: " + plandiario.getAlmuerzo(),
                            "       Colación 2: " + plandiario.getColacion2(),
                            "       Cena: " + plandiario.getCena() };
            agregarContentView(diaExpandableView, plan, false);
            planDiarioExpandableView.addContentView(diaExpandableView);
        }
    }

    public void mostrarSinInformacion(){
        if(findViewById(R.id.id_sin_informacion) == null) {
            LinearLayout parent = (LinearLayout) findViewById(R.id.parent_expandable_id);
            View singleView = getLayoutInflater().inflate(R.layout.no_info_view, null);
            parent.addView(singleView);
        }
    }
}