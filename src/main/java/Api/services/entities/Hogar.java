package Api.services.entities;

import domain.entities.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import lombok.*;
@Entity
@Table(name="hogar")
public class Hogar extends EntidadPersistente {
    @Getter @Setter
    public String nombre;
    @Getter @Setter
    @OneToOne
    public Ubicacion ubicacion;
    @Getter @Setter
    public String telefono;
    @Transient
    public Admisiones admisiones;
    @Getter @Setter
    public int capacidad;
    @Getter @Setter
    public int lugares_disponibles;
    @Getter @Setter
    public Boolean patio;
    @Transient
    public List<String> caracteristicas;
}
