import Api.services.entities.Ubicacion;
import EstrategiasNotificacion.EstrategiaDeNotificacion;
import EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
import entidades.*;
import entidades.Mascotas.MascotaEncontrada;
import entidades.Mascotas.MascotaEncontradaSinChapita;
import entidades.Organizacion.ContenedorPublicaciones;
import entidades.Organizacion.Organizacion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestMascotaEncontradaSinChapita {
    Organizacion organizacion1;
    Organizacion organizacion2;
    Organizacion organizacion3;
    Ubicacion ubicacion;
    InformacionPersonal informacionPersonal;
    MascotaEncontradaSinChapita mascota;

    @Before
    public void init(){
        mascota = new MascotaEncontradaSinChapita();
        ubicacion = new Ubicacion();
        ubicacion.direccion = "Corrientes 2320, CABA";
        ubicacion.latitud = -34.604660;
        ubicacion.longitud = -58.399792;

        organizacion1 = new Organizacion();
        organizacion1.setUbicacion(ubicacion);
        organizacion1.setNombre("RescuePets");
        organizacion1.setContenedorPublicaciones(new ContenedorPublicaciones());

        ubicacion.direccion = "Av. Mosconi 1328, Quilmes";
        ubicacion.latitud = -34.742298;
        ubicacion.longitud = -58.294991;

        organizacion2 = new Organizacion();
        organizacion2.setUbicacion(ubicacion);
        organizacion2.setNombre("Los michis");
        organizacion2.setContenedorPublicaciones(new ContenedorPublicaciones());

        ubicacion.direccion = "Arenales 1916, Florida";
        ubicacion.latitud = -34.525610;
        ubicacion.longitud = -58.489597;
        organizacion3 = new Organizacion();
        organizacion3.setUbicacion(ubicacion);
        organizacion3.setNombre("Rescate Patito");
        organizacion3.setContenedorPublicaciones(new ContenedorPublicaciones());
    }

    @Test
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

        ubicacion.direccion = "Arenales 1916, Florida";
        ubicacion.latitud = -34.525610;
        ubicacion.longitud = -58.489597;
        mascota.setRescatista(rescatista);
        mascota.setUbicacion(ubicacion);

        mascota.addOrganizacionALocalizador(organizacion1);
        mascota.addOrganizacionALocalizador(organizacion2);
        mascota.addOrganizacionALocalizador(organizacion3);

        mascota.notificar();

        Assert.assertEquals(0,organizacion1.getPublicacionesAprobadasMascotaEncontradaSinChapita().size());
        Assert.assertEquals(0,organizacion2.getPublicacionesAprobadasMascotaEncontradaSinChapita().size());
        Assert.assertEquals(0,organizacion3.getPublicacionesAprobadasMascotaEncontradaSinChapita().size());

    }
}
