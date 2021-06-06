package entidades;

public class UsuarioVoluntario extends  Usuario {

private Persona personaVoluntaria;

    public Persona getPersonaVoluntaria() {
        return personaVoluntaria;
    }

    public void setPersonaVoluntaria(Persona personaVoluntaria) {
        this.personaVoluntaria = personaVoluntaria;
    }
}
