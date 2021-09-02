package entidades;

import entidades.publicaciones.EstadoPublicacion;
import entidades.publicaciones.Publicacion;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario_voluntario")
public class UsuarioVoluntario extends Usuario {
    @OneToOne
    private Persona personaVoluntaria;

    public UsuarioVoluntario(String email) {
        super(email);
    }

    public UsuarioVoluntario() {

    }

    public Persona getPersonaVoluntaria() {
        return personaVoluntaria;
    }

    public void setPersonaVoluntaria(Persona personaVoluntaria) {
        this.personaVoluntaria = personaVoluntaria;
    }
    public void aprobarPublicacion(Publicacion publicacion){
        personaVoluntaria.getOrganizacion().getContenedorPublicaciones().agregarPublicacionAprobada(publicacion);
        publicacion.setEstado(EstadoPublicacion.APROBADA);
    }
}
