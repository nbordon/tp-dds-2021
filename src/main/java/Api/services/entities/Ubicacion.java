package Api.services.entities;

import com.google.gson.annotations.SerializedName;
import domain.entities.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;
@Entity
@Table(name="ubicacion")
public class Ubicacion extends EntidadPersistente {
    @Getter @Setter
    @SerializedName("direccion")
    public String direccion;

    @Getter @Setter
    @SerializedName("lat")
    public Double latitud;

    @Getter @Setter
    @SerializedName("long")
    public Double longitud;
}
