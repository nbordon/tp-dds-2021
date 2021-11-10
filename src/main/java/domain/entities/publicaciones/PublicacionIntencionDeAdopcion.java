package domain.entities.publicaciones;

import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
import domain.entities.EstrategiasNotificacion.EstrategiaEmail.EstrategiaDeEmail;
import domain.entities.Contacto;
import domain.entities.Mascotas.CaracteristicaDeMascota;
import domain.entities.Organizacion.Respuesta;
import domain.entities.Persona;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "public_intenc_adop")
public class PublicacionIntencionDeAdopcion extends Publicacion {
    @ManyToOne
    private Persona personaInteresada;
    private String linkBaja;
    @OneToMany
    @JoinTable(name = "rts_intenc_adop")
    private List<Respuesta> respuestasCaracteristicasDeMascota;
    @OneToMany
    @JoinTable(name = "rta_com_intenc_adop")
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

    public void setLinkBaja(String linkBaja){
        this.linkBaja=linkBaja;
    }
    public void notificarLinkDeBaja() {
       /* personaInteresada.getOrganizacion().agregarPublicacionIntencionDeAdopcion(this);*/
        EstrategiaDeNotificacion notidicadorEmail = new EstrategiaDeEmail();
        notidicadorEmail.notificar(
                "Link de baja",
                "Para poder dar de baja la publicación, acceder al siguiente link: "+linkBaja,
                getContactoInteresado());
    }

    public PublicacionIntencionDeAdopcion(Persona personaInteresada, List<Respuesta> comodidades, List<CaracteristicaDeMascota>caracteristicas, String linkBaja) {
        respuestasComodidades = new ArrayList<>();
        respuestasCaracteristicasDeMascota=new ArrayList<>();
        this.setPersonaInteresada(personaInteresada);
        this.linkBaja = linkBaja;
        comodidades.forEach(this::cargarComodidad);
        caracteristicas.forEach(this::cargarCaracteristicaMascotaDeseada);
        this.cambiarEstadoAPendiente();
    }
}
