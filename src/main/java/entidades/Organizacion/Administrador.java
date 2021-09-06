package entidades.Organizacion;

import entidades.Mascotas.CaracterisiticaDeMascotaRequerida;
import entidades.Usuario;
import entidades.UsuarioVoluntario;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "administrador")
public class Administrador extends Usuario {

    private String nombre;
    @ManyToOne
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacionPerteneciente;
    @Transient
    private List<UsuarioVoluntario> voluntariosDadosDeAlta;

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
