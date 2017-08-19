package models;

/**
 * Created by HouSe on 18/08/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dieta {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("condiciones_previas")
    @Expose
    private String condicionesPrevias;
    @SerializedName("plan_diario")
    @Expose
    private List<PlanDiario> planDiario = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondicionesPrevias() {
        return condicionesPrevias;
    }

    public void setCondicionesPrevias(String condicionesPrevias) {
        this.condicionesPrevias = condicionesPrevias;
    }

    public List<PlanDiario> getPlanDiario() {
        return planDiario;
    }

    public void setPlanDiario(List<PlanDiario> planDiario) {
        this.planDiario = planDiario;
    }

    @Override
    public String toString() {
        return "Dieta{" +
                "id=" + id +
                ", condicionesPrevias='" + condicionesPrevias + '\'' +
                ", planDiario=" + planDiario +
                '}';
    }
}
