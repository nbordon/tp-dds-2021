package publicaciones;

import entidades.Organizacion.Respuesta;
import entidades.Persona;

import java.util.List;

public class PublicacionIntencionDeAdopcion extends Publicacion{
    private Persona personaInteresada;

    public Persona getPersonaInteresada() {
        return personaInteresada;
    }

    public void setPersonaInteresada(Persona personaInteresada) {
        this.personaInteresada = personaInteresada;
    }

    public List<Respuesta> getRepuestasPreguntas() {
        return repuestasPreguntas;
    }

    public void setRepuestasPreguntas(List<Respuesta> repuestasPreguntas) {
        this.repuestasPreguntas = repuestasPreguntas;
    }

    private List<Respuesta> repuestasPreguntas;
    //TODO agregar lista de preferencias
    @Override
    public void notificar() {

        //solo mando el link baja por email

    }
    public void notifSemanales(){
        personaInteresada.getOrganizacion().getCaracteristicasDeMascotasRequeridas();

    }


}
