package Api.services.entities;

import com.google.gson.annotations.SerializedName;

public class Ubicacion {

    @SerializedName("direccion")
    public String direccion;
    @SerializedName("lat")
    public Double latitud;
    @SerializedName("long")
    public Double longitud;
}
