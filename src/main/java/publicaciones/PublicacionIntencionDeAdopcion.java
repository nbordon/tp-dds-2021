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
    private List<Respuesta> respuestasComodidades;

    public Persona getPersonaInteresada() {
        return personaInteresada;
    }

    public List<Respuesta> getRespuestasComodidades() {
        return respuestasComodidades;
    }

    public PublicacionIntencionDeAdopcion() {
        respuestasComodidades = new ArrayList<>();
        respuestasCaracteristicasDeMascota = new ArrayList<>();
    }

    public void cargarComodidad(Respuesta comodidad) {
        respuestasComodidades.add(comodidad);
    }

    public void cargarCaracteristicaMascotaDeseada(Respuesta caracteristica){respuestasCaracteristicasDeMascota.add(caracteristica);}

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
        EstrategiaDeNotificacion notidicadorEmail = new EstrategiaDeEmail();
        notidicadorEmail.notificar(
                "Link de baja",
                "Para poder dar de baja la publicación, acceder al siguiente link: "+linkBaja,
                getContactoInteresado());
    }

    public PublicacionIntencionDeAdopcion(Persona personaInteresada, List<Respuesta> respuestasComodidades, String linkBaja) {
        respuestasComodidades = new ArrayList<>();
        this.setPersonaInteresada(personaInteresada);
        this.linkBaja = linkBaja;
        respuestasComodidades.forEach(comodidad -> this.cargarComodidad(comodidad));
        this.cambiarEstadoAPendiente();
    }
}
