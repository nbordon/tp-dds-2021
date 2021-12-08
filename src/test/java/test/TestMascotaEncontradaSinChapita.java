package test;

import Api.services.entities.Ubicacion;
import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
import domain.entities.EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
import domain.entities.*;
import domain.entities.Mascotas.MascotaEncontradaSinChapita;
import domain.entities.Organizacion.ContenedorPublicaciones;
import domain.entities.Organizacion.Organizacion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import domain.entities.publicaciones.EstadoPublicacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestMascotaEncontradaSinChapita {
    Organizacion organizacion1;
    Organizacion organizacion2;
    Organizacion organizacion3;
    Ubicacion ubicacion1;
    Ubicacion ubicacion2;
    Ubicacion ubicacion3;
    Ubicacion ubicacion4;
    InformacionPersonal informacionPersonal;
    MascotaEncontradaSinChapita mascota;

    @Before
    public void init(){
        mascota = new MascotaEncontradaSinChapita();
        ubicacion1 = new Ubicacion();
        ubicacion1.direccion = "Corrientes 2320, CABA";
        ubicacion1.latitud = -34.604660;
        ubicacion1.longitud = -58.399792;

        organizacion1 = new Organizacion();
        organizacion1.setUbicacion(ubicacion1);
        organizacion1.setNombre("RescuePets");
        organizacion1.setContenedorPublicaciones(new ContenedorPublicaciones());

        ubicacion2 = new Ubicacion();
        ubicacion2.direccion = "Av. Mosconi 1328, Quilmes";
        ubicacion2.latitud = -34.742298;
        ubicacion2.longitud = -58.294991;

        organizacion2 = new Organizacion();
        organizacion2.setUbicacion(ubicacion2);
        organizacion2.setNombre("Los michis");
        organizacion2.setContenedorPublicaciones(new ContenedorPublicaciones());

        ubicacion3 = new Ubicacion();
        ubicacion3.direccion = "Arenales 1916, Florida";
        ubicacion3.latitud = -34.525610;
        ubicacion3.longitud = -58.489597;
        organizacion3 = new Organizacion();
        organizacion3.setUbicacion(ubicacion3);
        organizacion3.setNombre("Rescate Patito");
        organizacion3.setContenedorPublicaciones(new ContenedorPublicaciones());
    }

    @Test @Ignore
    public void unaMascotaEncontradaEnCABACentro(){
        Contacto contacto = new Contacto();
        contacto.setNombre("Juan");
        contacto.setApellido("Perez");
        contacto.setEmail("juanperez@gmail.com");
        contacto.setNumeroDeTelefono("11222333");

        List<Contacto> listaContactos = new ArrayList<>();
        listaContactos.add(contacto);

        List<EstrategiaDeNotificacion> estrategiasDeNotificacion = new ArrayList<>();
        estrategiasDeNotificacion.add(new EstrategiaDeWhatsApp());

        informacionPersonal = new InformacionPersonal(
                "Juan",
                "Perez",
                TipoDeDocumento.DNI,
                11222333,
                LocalDate.of(1989,01,25),
                "juanperez@gmail.com",
                estrategiasDeNotificacion,
                listaContactos
        );

        Rescatista rescatista = new Rescatista();
        rescatista.setInformacionPersonal(informacionPersonal);

        ubicacion4 = new Ubicacion();
        ubicacion4.direccion = "Arenales 1916, Florida";
        ubicacion4.latitud = -34.525610;
        ubicacion4.longitud = -58.489597;
        mascota.setRescatista(rescatista);
        mascota.setUbicacion(ubicacion4);

        mascota.notificar();

        Assert.assertEquals(0,organizacion1.getPublicacionesMascotaEncontrada(EstadoPublicacion.PENDIENTE).size());
        Assert.assertEquals(0,organizacion2.getPublicacionesMascotaEncontrada(EstadoPublicacion.PENDIENTE).size());
        Assert.assertEquals(1,organizacion3.getPublicacionesMascotaEncontrada(EstadoPublicacion.PENDIENTE).size());

    }
}
