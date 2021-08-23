package entidades;

import publicaciones.EstadoPublicacion;
import publicaciones.Publicacion;

public class UsuarioVoluntario extends Usuario {

    private Persona personaVoluntaria;

    public UsuarioVoluntario(String email) {
        super(email);
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
