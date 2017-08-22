package com.example.kala.movilkala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.SimpleSpinnerTextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lecho.lib.hellocharts.formatter.AxisValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import models.FichaFis;
import resource.DrawerK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.RequestK;
import service.RestClient;

/**
 * Created by HouSe on 17/08/2017.
 */

public class ProgresoActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();
    private SharedPreferences sesion;
    private Drawer drawer;
    private RestClient restClient;
    private List<FichaFis> fichas = null;
    private NiceSpinner niceSpinner = null;
    AdapterView.OnItemSelectedListener onItemSelectedListener = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progreso);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_progreso);
        toolbar.inflateMenu(R.menu.view_update);

        View updateView = findViewById(R.id.update);
        updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerFichas();
            }
        });

        if(getApplicationContext() != null) {
            sesion = getApplicationContext().getSharedPreferences("user_sesion", Context.MODE_PRIVATE);
        }

        drawer = DrawerK.initDrawer(this, toolbar);

        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("Peso", "Indice de masa corporal (IMC)",
                "Porcentaje de músculo", "Grasa visceral", "Porcentaje grasa total"));
        niceSpinner.setTintColor(getResources().getColor(R.color.colorPrimary));
        niceSpinner.attachDataSource(dataset);

        onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                graficar(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        niceSpinner.setOnItemSelectedListener(onItemSelectedListener);

        obtenerFichas();
    }

    public void obtenerFichas(){
        final ProgressDialog progressDialog = new ProgressDialog(ProgresoActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.updating));
        progressDialog.show();

        RequestK.init();
        restClient = RequestK.createService(RestClient.class, sesion.getString("token", ""), false);
        Call<List<FichaFis>> call = restClient.getFichasFis(sesion.getString("cedula", ""));

        call.enqueue(new Callback<List<FichaFis>>() {
            @Override
            public void onResponse(Call<List<FichaFis>> call, Response<List<FichaFis>> response) {
                if(response.isSuccessful()) {
                    List<FichaFis> data = response.body();
                    fichas = data;
                    if(niceSpinner != null){
                        graficar(niceSpinner.getSelectedIndex());
                    }
                    progressDialog.dismiss();
                }
                else{
                    mostrarSinInformacion();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<FichaFis>> call, Throwable t) {
                mostrarSinInformacion();
                progressDialog.dismiss();
            }
        });
    }

    public void mostrarSinInformacion(){
        if(findViewById(R.id.id_sin_informacion) == null) {
            LinearLayout parent = (LinearLayout) findViewById(R.id.parent_expandable_id);
            View singleView = getLayoutInflater().inflate(R.layout.no_info_view, null);
            parent.addView(singleView);
        }
    }

    private void graficar(int item) {
        if(fichas == null || fichas.isEmpty()) return;

        List<PointValue> values = new ArrayList<PointValue>();
        LineChartData data = new LineChartData();
        List<AxisValue> axisValues = new ArrayList<>();

        for(int index = 0; index < fichas.size(); index++)
            axisValues.add(new AxisValue(index).setLabel("d" + (index + 1) ));


        AxisValueFormatter formatter = new SimpleAxisValueFormatter(0);
        Axis axisX = new Axis(axisValues)
                .setName("Diagnósticos")
                .setTextSize(18)
                .setFormatter(formatter)
                .setTextColor(getResources().getColor(R.color.colorAccent));
        Axis axisY = new Axis()
                .setHasLines(true)
                .setTextSize(18)
                .setTextColor(getResources().getColor(R.color.colorAccent));

        try {
            for (int index = 0; index < fichas.size(); index++) {
                switch (item) {
                    case 0: {
                        PointValue point = new PointValue(index, Float.parseFloat(fichas.get(index).getPeso()));
                        point.setLabel(fichas.get(index).getPeso() + "Kg   ("+fichas.get(index).getCreado().substring(0,10) + ")");
                        values.add(point);
                        axisY.setName("Peso (Kg)");
                        break;
                    }
                    case 1: {
                        PointValue point = new PointValue(index, Float.parseFloat(fichas.get(index).getImc()));
                        point.setLabel(fichas.get(index).getImc() + "   ("+fichas.get(index).getCreado().substring(0,10) + ")");
                        values.add(point);
                        axisY.setName("Indice de masa corporal");
                        break;
                    }
                    case 2: {
                        PointValue point = new PointValue(index, Float.parseFloat(fichas.get(index).getMusculo()));
                        point.setLabel(fichas.get(index).getMusculo() + "%   ("+fichas.get(index).getCreado().substring(0,10) + ")");
                        values.add(point);
                        axisY.setName("Porcentaje de Músculo (%)");
                        break;
                    }
                    case 3: {
                        PointValue point = new PointValue(index, Float.parseFloat(fichas.get(index).getGrasaVisceral()));
                        point.setLabel(fichas.get(index).getGrasaVisceral() + "%   ("+fichas.get(index).getCreado().substring(0,10) + ")");
                        values.add(point);
                        axisY.setName("Grasa Visceral (%)");
                        break;
                    }
                    case 4: {
                        PointValue point = new PointValue(index, Float.parseFloat(fichas.get(index).getGrasaPorcentaje()));
                        point.setLabel(fichas.get(index).getGrasaPorcentaje() + "%   ("+fichas.get(index).getCreado().substring(0,10) + ")");
                        values.add(point);
                        axisY.setName("Porcentaje de grasa corporal (%)");
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        catch (Exception e){}

        Line line = new Line(values)
                .setColor(getResources()
                        .getColor(R.color.colorPrimaryDark))
                .setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        line.setFilled(true);
        line.setAreaTransparency(50);
        line.setHasLabels(true);
        lines.add(line);

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setLines(lines);

        LineChartView chart = findViewById(R.id.chart);
        chart.setZoomEnabled(true);
        chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        chart.setLineChartData(data);
    }
}