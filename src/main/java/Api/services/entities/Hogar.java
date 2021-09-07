package Api.services.entities;

import entidades.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class Hogar extends EntidadPersistente {

    public String id_hogar;
    public String nombre;
    @Transient
    public Ubicacion ubicacion;
    public String telefono;
    @Transient
    public Admisiones admisiones;
    public int capacidad;
    public int lugares_disponibles;
    public Boolean patio;
    @Transient
    public List<String> caracteristicas;
}
