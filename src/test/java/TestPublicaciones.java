import EstrategiasNotificacion.EstrategiaDeNotificacion;
import EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
import entidades.Contacto;
import entidades.InformacionPersonal;
import entidades.Mascotas.CaracterisiticaDeMascotaRequerida;
import entidades.Mascotas.CaracteristicaDeMascota;
import entidades.Mascotas.Mascota;
import entidades.Mascotas.MascotaBuilder;
import entidades.Organizacion.*;
import entidades.Persona;
import entidades.TipoDeDocumento;
import exception.CaracteristicaRequeridaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import publicaciones.PublicacionIntencionDeAdopcion;
import publicaciones.PublicacionMascotaEnAdopcion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestPublicaciones {
    Organizacion organizacion;
    Persona duenio;
    Persona personaInteresada;
    CaracterisiticaDeMascotaRequerida colorPrincipal;
    String linkBaja;
    Mascota mascota;
    CaracterisiticaDeMascotaRequerida estaCastrada;
    PublicacionIntencionDeAdopcion publicacionIntencion;
    PublicacionMascotaEnAdopcion  publicacionMascotaEnAdopcion;

    List<String> opciones1;

    CaracteristicaDeMascota resp1;
    CaracteristicaDeMascota resp2;

    Pregunta preguntaColorPrincipal;
    Respuesta preferencia1;

    @Before
    public void inicializar(){
        opciones1 = new ArrayList<>();

        opciones1.add("marron");
        opciones1.add("negro");
        opciones1.add("blanco");

         preguntaColorPrincipal = new Pregunta();

        preferencia1 = new Respuesta(preguntaColorPrincipal,"marron");

        colorPrincipal = new CaracterisiticaDeMascotaRequerida();
        colorPrincipal.setValor(opciones1);
        colorPrincipal.setDescripcion("color principal");

        organizacion = new Organizacion();

        duenio = new Persona("duenio@duenio.com");
        duenio.setOrganizacion(organizacion);

        personaInteresada = new Persona("interesado@interesado.com");
        personaInteresada.setOrganizacion(organizacion);

        resp1 = new CaracteristicaDeMascota();
        resp2 = new CaracteristicaDeMascota();

        publicacionMascotaEnAdopcion = new PublicacionMascotaEnAdopcion();

        publicacionIntencion = new PublicacionIntencionDeAdopcion();

    }

    @Test
    public void publicarMascotaEnAdopcion() throws CaracteristicaRequeridaException {
        resp1 = colorPrincipal.contestar("marron");
        resp2 = estaCastrada.contestar("si");
        mascota = MascotaBuilder.crear()
                .conCaracteristicasDeMascota(resp1)
                .conCaracteristicasDeMascota(resp2)
                .conDescripcionFisica("grande")
                .conFotosUrl("/foto1")
                .conPersona(duenio)
                .construir();

        publicacionMascotaEnAdopcion.setMascota(mascota);
       // publicacionMascotaEnAdopcion.cargarRespuestasPreguntas(resp1);
       // publicacionMascotaEnAdopcion.cargarRespuestasPreguntas(resp2);

        organizacion.agregarPublicacionEnEsperaDeAprobacion(publicacionMascotaEnAdopcion);
        Assert.assertEquals(1,organizacion.getPublicacionesEnEsperaDeAprobacion().size());

    }

    @Test
    public void PublicacioninteresDeAdopcion(){
        publicacionIntencion.setPersonaInteresada(personaInteresada);

        linkBaja = "www.linkBaja.com";

        publicacionIntencion.cargarPreferencias(preferencia1);

        organizacion.agregarPublicacionIntencionDeAdopcion(publicacionIntencion);
        Assert.assertEquals(1,organizacion.getPublicacionesAprobadasIntencionDeAdopcion().size());

    }

}
