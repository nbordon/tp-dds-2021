package entidades.Organizacion;

import entidades.Usuario;
import entidades.UsuarioVoluntario;

import java.util.List;

public class Administrador extends Usuario {

    private String nombre;
    private Organizacion organizacionPerteneciente;
    private List<UsuarioVoluntario> voluntariosDadosDeAlta;

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
}
