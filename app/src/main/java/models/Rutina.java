package models;

/**
 * Created by carlos on 19/08/17.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rutina {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("condiciones_previas")
    @Expose
    private String condicionesPrevias;
    @SerializedName("plan_diario")
    @Expose
    private List<Subrutina> subrutina = null;

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

    public List<Subrutina> getSubrutina() {
        return subrutina;
    }

    public void setSubrutina(List<Subrutina> subrutina) {
        this.subrutina = subrutina;
    }

    @Override
    public String toString() {
        return "Dieta{" +
                "id=" + id +
                ", condicionesPrevias='" + condicionesPrevias + '\'' +
                ", planDiario=" + subrutina +
                '}';
    }
}
