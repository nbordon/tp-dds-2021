package publicaciones;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import EstrategiasNotificacion.EstrategiaEmail.EstrategiaDeEmail;
import entidades.Contacto;
import entidades.Organizacion.Respuesta;
import entidades.Persona;

import java.util.ArrayList;
import java.util.List;

public class IntencionDeAdopcion extends Publicacion {
    private Persona personaInteresada;
    private List<Respuesta> preferencias;
    private List<Respuesta> respuestasCaracteristicasDeMascota;
    private String linkBaja;
    
        public IntencionDeAdopcion(){
        preferencias = new ArrayList<>();
    }
    private void cargarPreferencias(Respuesta preferencia){
        preferencias.add(preferencia);
    }
    public IntencionDeAdopcion(Persona personaInteresada,List<Respuesta> preferencias,String linkBaja) {
        preferencias = new ArrayList<>();
        this.setPersonaInteresada(personaInteresada);
        this.linkBaja = linkBaja;
        preferencias.forEach(preferencia->this.cargarPreferencias(preferencia));
        this.cambiarEstadoAPendiente();
        this.notificar();
    }
  
    public Persona getPersonaInteresada() {
        return personaInteresada;
    }
  
    public List<Respuesta> getPreferencias() {
        return preferencias;
    }

    public List<Respuesta> getRespuestasCaracteristicasDeMascota() {
        return respuestasCaracteristicasDeMascota;
    }

    private Contacto getContactoInteresado() {
        return personaInteresada.getInformacionPersonal().getContactoDuenio();
    }

    public void setPersonaInteresada(Persona unaPersonaInteresada){
        personaInteresada = unaPersonaInteresada;
    }


    @Override
    public void notificar() {
        personaInteresada.getOrganizacion().agregarPublicacionIntencionDeAdopcion(this);
        EstrategiaDeNotificacion comunicacionEmail = new EstrategiaDeEmail();
        comunicacionEmail.notificar(linkBaja, getContactoInteresado());
    }
}
