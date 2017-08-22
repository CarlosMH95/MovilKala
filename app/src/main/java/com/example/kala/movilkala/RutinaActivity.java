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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import models.Dieta;
import models.Dieta;
import models.Subrutina;
import models.Rutina;
import models.PlanDiario;
import models.Rutina;
import resource.DrawerK;
import resource.ExpandableListAdapter;
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

public class RutinaActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private Drawer drawer = null;
    private View expandableView = null;
    private ExpandableView rutinaExpandableView;
    private ExpandableView subrutinaExpandableView;
    private ExpandableView ejerciciosExpandableView;

    private RestClient restClient = null;
    SharedPreferences sesion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_rutinas);
        toolbar.inflateMenu(R.menu.view_update);

        View updateView = findViewById(R.id.update);
        updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerRutinas();
            }
        });

        if(getApplicationContext() != null) {
            sesion = getApplicationContext().getSharedPreferences("user_sesion", Context.MODE_PRIVATE);
        }

        drawer = DrawerK.initDrawer(this, toolbar);

        obtenerRutinas();
    }

    public void obtenerRutinas(){
        final ProgressDialog progressDialog = new ProgressDialog(RutinaActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.updating));
        progressDialog.show();

        RequestK.init();
        restClient = RequestK.createService(RestClient.class, sesion.getString("token", ""), false);
        Call<List<Rutina>> call = restClient.getRutinas(sesion.getString("cedula", ""));

        call.enqueue(new Callback<List<Rutina>>() {
            @Override
            public void onResponse(Call<List<Rutina>> call, Response<List<Rutina>> response) {

                if(response.isSuccessful()) {
                    List<Rutina> data = response.body();
                    mostrarRutinas(data);
                    progressDialog.dismiss();
                }
                else{
                    mostrarSinInformacion();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Rutina>> call, Throwable t) {
                mostrarSinInformacion();
                progressDialog.dismiss();
            }
        });
    }

    private void mostrarRutinas(List<Rutina> rutinas) {
        if(rutinas == null || rutinas.isEmpty()) return;

        if(expandableView == null) {

            for (Rutina rutina : rutinas) {
                LinearLayout parent = (LinearLayout) findViewById(R.id.parent_expandable_id);
                expandableView = getLayoutInflater().inflate(R.layout.expandable_child_view, null);
                rutinaExpandableView = (ExpandableView) expandableView;
                subrutinaExpandableView = new ExpandableView(this);
                ejerciciosExpandableView = new ExpandableView(this);

                crearRutinaExpandableView(rutina);
                crearSubrutinaExpandableView(rutina.getSubrutina());
                subrutinaExpandableView.setOutsideContentLayout(rutinaExpandableView.getContentLayout());
                ejerciciosExpandableView.setOutsideContentLayout(subrutinaExpandableView.getContentLayout());

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

    private void crearRutinaExpandableView(Rutina rutina){


        rutinaExpandableView.fillData(0, "Rutina: " + rutina.getId(), true);
        String[] condiciones = {"Condiciones previas: " + rutina.getCondicionesPrevias()};
        agregarContentView(rutinaExpandableView, condiciones, false);
        rutinaExpandableView.addContentView(subrutinaExpandableView);
    }

    private void crearSubrutinaExpandableView(List<Subrutina> planes) {

        subrutinaExpandableView.setBackgroundResource(android.R.color.background_light);
        subrutinaExpandableView.fillData(0, "       Ejercicios", false);

        for(Subrutina subrutina: planes){
            ExpandableView ejerciciosExpandableView = new ExpandableView(this);
            ejerciciosExpandableView.fillData(0, "     "+subrutina.getNombre(), false);
            String[] plan ={"       Detalles: " + subrutina.getDetalle(),
                    "       Sets: " + subrutina.getVeces(),
                    "       Repeticiones: " + subrutina.getRepeticiones(),
                    "       Descanso entre sets: " + subrutina.getDescanso(),
                    "       Link de video Ayuda: " + subrutina.getLink() };

            agregarContentView(ejerciciosExpandableView, plan, false);
            subrutinaExpandableView.addContentView(ejerciciosExpandableView);
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