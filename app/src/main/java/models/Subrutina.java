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
    @SerializedName("descanso")
    @Expose
    private String descanso;
    @SerializedName("detalle")
    @Expose
    private String detalle;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("repeticiones")
    @Expose
    private String repeticiones;
    @SerializedName("veces")
    @Expose
    private String veces;
    @SerializedName("estado")
    @Expose
    private String estado;

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

    public String getDescanso() {
        return descanso;
    }

    public void setdescanso(String descanso) {
        this.descanso = descanso;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getVeces() {
        return veces;
    }

    public void setVeces(String veces) {
        this.veces = veces;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return "Subrutina{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", detalle='" + detalle + '\'' +
                ", descanso='" + descanso + '\'' +
                ", repeticiones='" + repeticiones + '\'' +
                ", veces='" + veces + '\'' +
                ", descanso='" + descanso + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
