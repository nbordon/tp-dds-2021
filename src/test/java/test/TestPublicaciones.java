package test;

import domain.entities.Mascotas.CaracterisiticaDeMascotaRequerida;
import domain.entities.Mascotas.CaracteristicaDeMascota;
import domain.entities.Mascotas.Mascota;
import domain.entities.Mascotas.MascotaBuilder;
import domain.entities.Organizacion.*;
import domain.entities.Persona;
import exception.CaracteristicaRequeridaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import domain.entities.publicaciones.EstadoPublicacion;
import domain.entities.publicaciones.PublicacionIntencionDeAdopcion;
import domain.entities.publicaciones.PublicacionMascotaEnAdopcion;

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
    PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion;

    List<String> opciones1;
    List<String> opciones2;

    CaracteristicaDeMascota resp1;
    CaracteristicaDeMascota resp2;

    Pregunta preguntaColorPrincipal;
    Respuesta preferencia1;
    ContenedorPublicaciones contenedor;

    @Before
    public void inicializar() {
        opciones1 = new ArrayList<>();
        opciones1.add("marron");
        opciones1.add("negro");
        opciones1.add("blanco");

        opciones2 = new ArrayList<>();
        opciones2.add("si");
        opciones2.add("no");

        preguntaColorPrincipal = new Pregunta();
        preguntaColorPrincipal.setValor(opciones1);

        preferencia1 = new Respuesta(preguntaColorPrincipal, "marron");

        colorPrincipal = new CaracterisiticaDeMascotaRequerida();
        colorPrincipal.setValor(opciones1);
        colorPrincipal.setDescripcion("color principal");

        estaCastrada = new CaracterisiticaDeMascotaRequerida();
        estaCastrada.setDescripcion("Esta castrado/a");
        estaCastrada.setValor(opciones2);

        organizacion = new Organizacion();

        duenio = new Persona("duenio@duenio.com");
        duenio.setOrganizacion(organizacion);

        personaInteresada = new Persona("interesado@interesado.com");
        personaInteresada.setOrganizacion(organizacion);

        resp1 = new CaracteristicaDeMascota();
        resp2 = new CaracteristicaDeMascota();

        publicacionMascotaEnAdopcion = new PublicacionMascotaEnAdopcion();

        publicacionIntencion = new PublicacionIntencionDeAdopcion();

        contenedor = new ContenedorPublicaciones();
        organizacion.setContenedorPublicaciones(contenedor);
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

        organizacion.agregarPublicacionEnEsperaDeAprobacion(publicacionMascotaEnAdopcion);
        Assert.assertEquals(1, organizacion.getPublicacionesEnEsperaDeAprobacion().size());

    }

    @Test
    public void PublicacioninteresDeAdopcion() {
        publicacionIntencion.setPersonaInteresada(personaInteresada);

        linkBaja = "www.linkBaja.com";

        publicacionIntencion.cargarComodidad(preferencia1);
        publicacionIntencion.setEstado(EstadoPublicacion.PENDIENTE);

        organizacion.agregarPublicacionIntencionDeAdopcion(publicacionIntencion);
        Assert.assertEquals(1, organizacion.publicacionesIntencionDeAdopcion(EstadoPublicacion.PENDIENTE).size());
    }

}
