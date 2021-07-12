package publicaciones;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import EstrategiasNotificacion.EstrategiaEmail.EstrategiaDeEmail;
import entidades.Contacto;
import entidades.Organizacion.Respuesta;
import entidades.Persona;

import java.util.ArrayList;
import java.util.List;

public class PublicacionIntencionDeAdopcion extends Publicacion {
    private Persona personaInteresada;
    private String linkBaja;
    private List<Respuesta> respuestasCaracteristicasDeMascota;
    private List<Respuesta> listaPreferencias;

    public Persona getPersonaInteresada() {
        return personaInteresada;
    }

    public List<Respuesta> getListaPreferencias() {
        return listaPreferencias;
    }

    public PublicacionIntencionDeAdopcion() {
        listaPreferencias = new ArrayList<>();
    }

    public void cargarPreferencias(Respuesta preferencia) {
        listaPreferencias.add(preferencia);
    }

    public List<Respuesta> getRespuestasCaracteristicasDeMascota() {
        return respuestasCaracteristicasDeMascota;
    }

    private Contacto getContactoInteresado() {
        return personaInteresada.getInformacionPersonal().getContactoDuenio();
    }

    public void setPersonaInteresada(Persona unaPersonaInteresada) {
        personaInteresada = unaPersonaInteresada;
    }

    public void notificarLinkDeBaja() {
        personaInteresada.getOrganizacion().agregarPublicacionIntencionDeAdopcion(this);
        EstrategiaDeNotificacion comunicacionEmail = new EstrategiaDeEmail();
        comunicacionEmail.notificar(linkBaja, getContactoInteresado());
    }

    public PublicacionIntencionDeAdopcion(Persona personaInteresada, List<Respuesta> listaPreferencias, String linkBaja) {
        listaPreferencias = new ArrayList<>();
        this.setPersonaInteresada(personaInteresada);
        this.linkBaja = linkBaja;
        listaPreferencias.forEach(preferencia -> this.cargarPreferencias(preferencia));
        this.cambiarEstadoAPendiente();
    }
}
