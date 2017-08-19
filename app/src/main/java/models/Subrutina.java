package models;

/**
 * Created by carlos on 19/08/17.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subrutina {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("creado")
    @Expose
    private String creado;
    @SerializedName("actualizado")
    @Expose
    private String actualizado;
    @SerializedName("dia")
    @Expose
    private String dia;
    @SerializedName("desayuno")
    @Expose
    private String desayuno;
    @SerializedName("colacion1")
    @Expose
    private String colacion1;
    @SerializedName("almuerzo")
    @Expose
    private String almuerzo;
    @SerializedName("colacion2")
    @Expose
    private String colacion2;
    @SerializedName("cena")
    @Expose
    private String cena;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("dieta")
    @Expose
    private int dieta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getActualizado() {
        return actualizado;
    }

    public void setActualizado(String actualizado) {
        this.actualizado = actualizado;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(String desayuno) {
        this.desayuno = desayuno;
    }

    public String getColacion1() {
        return colacion1;
    }

    public void setColacion1(String colacion1) {
        this.colacion1 = colacion1;
    }

    public String getAlmuerzo() {
        return almuerzo;
    }

    public void setAlmuerzo(String almuerzo) {
        this.almuerzo = almuerzo;
    }

    public String getColacion2() {
        return colacion2;
    }

    public void setColacion2(String colacion2) {
        this.colacion2 = colacion2;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getDieta() {
        return dieta;
    }

    public void setDieta(int dieta) {
        this.dieta = dieta;
    }

    @Override
    public String toString() {
        return "Subrutina{" +
                "id=" + id +
                ", dia='" + dia + '\'' +
                ", desayuno='" + desayuno + '\'' +
                ", colacion1='" + colacion1 + '\'' +
                ", almuerzo='" + almuerzo + '\'' +
                ", colacion2='" + colacion2 + '\'' +
                ", cena='" + cena + '\'' +
                '}';
    }
}
