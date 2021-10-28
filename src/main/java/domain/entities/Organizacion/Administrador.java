package domain.entities.Organizacion;

import domain.entities.Mascotas.CaracterisiticaDeMascotaRequerida;
import domain.entities.Usuario;
import domain.entities.UsuarioVoluntario;

import javax.persistence.*;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="administrador")
public class Administrador extends Usuario {

    private String nombre;
    @ManyToOne
    private Organizacion organizacionPerteneciente;
    @Transient
    private List<UsuarioVoluntario> voluntariosDadosDeAlta = new ArrayList<>();


    public Administrador(String email) {
        super(email);
    }

    public Administrador() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Organizacion getOrganizacionPerteneciente() {
        return organizacionPerteneciente;
    }

    public void setOrganizacionPerteneciente(Organizacion organizacionPerteneciente) {
        this.organizacionPerteneciente = organizacionPerteneciente;
    }

    public List<UsuarioVoluntario> getVoluntariosDadosDeAlta() {
        return voluntariosDadosDeAlta;
    }

    public void setVoluntariosDadosDeAlta(List<UsuarioVoluntario> voluntariosDadosDeAlta) {
        this.voluntariosDadosDeAlta = voluntariosDadosDeAlta;
    }

    public void aprobarVoluntario(UsuarioVoluntario voluntario) {
        if (organizacionPerteneciente.getUsuariosAprobados().contains(voluntario))
            voluntariosDadosDeAlta.add(voluntario);
    }

    public void quitarVoluntario(UsuarioVoluntario voluntario) {
        voluntariosDadosDeAlta.remove(voluntario);
    }

    public void agregarCaracteristica(CaracterisiticaDeMascotaRequerida caracteristica){
        organizacionPerteneciente.addCaracteristicaDeMascotasRequerida(caracteristica);
    }

    public void removerCaracteristica(CaracterisiticaDeMascotaRequerida caracteristica){
        organizacionPerteneciente.removeCaracteristicaDeMascotasRequerida(caracteristica);
    }
}
