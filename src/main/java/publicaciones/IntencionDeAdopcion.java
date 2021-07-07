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
    private String linkBaja;
    private List<Respuesta> listaPreferencias;

    public IntencionDeAdopcion(){
        listaPreferencias = new ArrayList<>();
    }
    private void cargarPreferencias(Respuesta preferencia){
        listaPreferencias.add(preferencia);
    }
    public IntencionDeAdopcion(Persona personaInteresada,List<Respuesta> listaPreferencias,String linkBaja) {
        listaPreferencias = new ArrayList<>();
        this.setPersonaInteresada(personaInteresada);
        this.linkBaja = linkBaja;
        listaPreferencias.forEach(preferencia->this.cargarPreferencias(preferencia));
        this.cambiarEstadoAPendiente();
        this.notificar();
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
