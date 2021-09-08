package Api.services.entities;

import com.google.gson.annotations.SerializedName;
import entidades.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ubicacion")
public class Ubicacion extends EntidadPersistente {

    @SerializedName("direccion")
    public String direccion;
    @SerializedName("lat")
    public Double latitud;
    @SerializedName("long")
    public Double longitud;
}
