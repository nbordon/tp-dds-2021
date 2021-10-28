package domain.entities;

import domain.entities.Organizacion.Organizacion;
import domain.entities.publicaciones.EstadoPublicacion;
import domain.entities.publicaciones.Publicacion;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="usuario_voluntario")
public class UsuarioVoluntario extends Usuario {
    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Persona personaVoluntaria;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name="organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;

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
