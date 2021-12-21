package Api.services.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class HogarResponse {
    @Getter @Setter
    public String id;
    @Getter @Setter
    public String nombre;
    @Getter @Setter
    public Ubicacion ubicacion;
    @Getter @Setter
    public String telefono;
    @Getter @Setter
    public Admisiones admisiones;
    @Getter @Setter
    public int capacidad;
    @Getter @Setter
    public int lugares_disponibles;
    @Getter @Setter
    public Boolean patio;
    @Getter @Setter
    public List<String> caracteristicas;

    public static Hogar toHogarPersistente(HogarResponse hogarResponse) {
        Hogar hogar = new Hogar();
        hogar.setNombre(hogarResponse.getNombre());
        hogar.setUbicacion(hogarResponse.getUbicacion());
        hogar.setTelefono(hogarResponse.getTelefono());
        hogar.setAdmisiones(hogarResponse.getAdmisiones());
        hogar.setCapacidad(hogarResponse.getCapacidad());
        hogar.setLugares_disponibles(hogarResponse.getLugares_disponibles());
        hogar.setPatio(hogarResponse.getPatio());
        hogar.setCaracteristicas(hogarResponse.getCaracteristicas());
        return hogar;
    }
}
