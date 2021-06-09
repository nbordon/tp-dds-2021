package Api.services.entities;
import java.util.List;

public class Hogar {
    public String id;
    public String nombre;
    public Ubicacion ubicacion;
    public String telefono;
    public Admisiones admisiones;
    public int capacidad;
    public int lugares_disponibles;
    public Boolean patio;
    public List<String> caracteristicas;

}
