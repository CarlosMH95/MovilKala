package models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by HouSe on 21/08/2017.
 */
public class FichaFis {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("creado")
    @Expose
    private String creado;
    @SerializedName("peso")
    @Expose
    private String peso;
    @SerializedName("imc")
    @Expose
    private String imc;
    @SerializedName("musculo")
    @Expose
    private String musculo;
    @SerializedName("grasa_visceral")
    @Expose
    private String grasaVisceral;
    @SerializedName("grasa_porcentaje")
    @Expose
    private String grasaPorcentaje;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public String getMusculo() {
        return musculo;
    }

    public void setMusculo(String musculo) {
        this.musculo = musculo;
    }

    public String getGrasaVisceral() {
        return grasaVisceral;
    }

    public void setGrasaVisceral(String grasaVisceral) {
        this.grasaVisceral = grasaVisceral;
    }

    public String getGrasaPorcentaje() {
        return grasaPorcentaje;
    }

    public void setGrasaPorcentaje(String grasaPorcentaje) {
        this.grasaPorcentaje = grasaPorcentaje;
    }

}
