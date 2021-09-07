package test;

import entidades.EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.EstrategiasNotificacion.EstrategiaEmail.EstrategiaDeEmail;
import entidades.EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
import entidades.Recomendaciones.NotificadorSemanal;
import entidades.Recomendaciones.Recomendacion;
import entidades.Contacto;
import entidades.InformacionPersonal;
import entidades.Mascotas.CaracterisiticaDeMascotaRequerida;
import entidades.Mascotas.CaracteristicaDeMascota;
import entidades.Mascotas.Mascota;
import entidades.Mascotas.MascotaBuilder;
import entidades.Organizacion.*;
import entidades.Persona;
import exception.CaracteristicaRequeridaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import entidades.publicaciones.EstadoPublicacion;
import entidades.publicaciones.PublicacionIntencionDeAdopcion;
import entidades.publicaciones.PublicacionMascotaEnAdopcion;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class TestRecomendacionesSemanales {

    Organizacion organizacion;
    ContenedorPublicaciones contenedorPublicaciones1;

    CaracterisiticaDeMascotaRequerida colorPrincipal;
    CaracterisiticaDeMascotaRequerida colorSecundario;
    CaracterisiticaDeMascotaRequerida estaCastrada;
    Administrador administrador;

    Mascota mascota1;
    Mascota mascota2;
    Mascota mascota3;

    List<String> opciones1;
    List<String> opciones2;
    List<String> opciones3;
    CaracteristicaDeMascota resp1;
    CaracteristicaDeMascota resp2;
    CaracteristicaDeMascota resp3;
    CaracteristicaDeMascota resp4;
    CaracteristicaDeMascota resp5;
    CaracteristicaDeMascota resp6;
    CaracteristicaDeMascota resp7;
    CaracteristicaDeMascota resp8;
    CaracteristicaDeMascota resp9;
    Persona duenio1;
    Persona duenio2;
    Persona duenio3;
    Persona interesado;

    List<String> opcionesComodidades;
    Pregunta patio;
    Pregunta juguetes;

    Respuesta rptaComodidadDuenio1;
    Respuesta rptaComodidadDuenio2;
    Respuesta rptaComodidadDuenio3;
    Respuesta rptaComodidadDuenio4;
    Respuesta rptaComodidadDuenio5;
    Respuesta rptaComodidadDuenio6;

    Respuesta rptaCaracteristicaIntencion1;
    Respuesta rptaCaracteristicaIntencion2;
    Respuesta rptaCaracteristicaIntencion3;

    Respuesta rptaComodidadIntencion1;
    Respuesta rptaComodidadIntencion2;

    PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion;
    PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion1;
    PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion2;
    PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion3;

    Recomendacion recomendacion;
    NotificadorSemanal notificadorSemanal;

    InformacionPersonal informacionPersonal;
    List<EstrategiaDeNotificacion> formaComunicacion;
    EstrategiaDeEmail estrategiaDeEmail;
    EstrategiaDeWhatsApp estrategiaDeWhatsApp;
    List<Contacto> contactosInteresado;
    Contacto contactoInteresado;


    @Before
    public void inicializar() throws CaracteristicaRequeridaException {

        //Publicaciones que se agregaran al contenedor
        publicacionIntencionDeAdopcion = new PublicacionIntencionDeAdopcion();
        publicacionMascotaEnAdopcion1 = new PublicacionMascotaEnAdopcion();
        publicacionMascotaEnAdopcion2 = new PublicacionMascotaEnAdopcion();
        publicacionMascotaEnAdopcion3 = new PublicacionMascotaEnAdopcion();

        publicacionMascotaEnAdopcion1.setTitulo("Mascota recomendada 1");
        publicacionMascotaEnAdopcion2.setTitulo("Mascota recomendada 2");
        publicacionMascotaEnAdopcion3.setTitulo("Mascota recomendada 3");

        //Inicializamos caracteristicas requeridas para la creacion de una mascota
        opciones1 = new ArrayList<>();
        opciones2 = new ArrayList<>();
        opciones3 = new ArrayList<>();
        opcionesComodidades = new ArrayList<>();

        opciones1.add("marron");
        opciones1.add("negro");
        opciones1.add("blanco");

        colorPrincipal = new CaracterisiticaDeMascotaRequerida();
        colorPrincipal.setValor(opciones1);
        colorPrincipal.setDescripcion("color principal");
        colorPrincipal.setId(1);

        opciones2.add("marron");
        opciones2.add("negro");
        opciones2.add("blanco");
        colorSecundario = new CaracterisiticaDeMascotaRequerida();
        colorSecundario.setValor(opciones2);
        colorSecundario.setDescripcion("color secundario");
        colorSecundario.setId(2);

        opciones3.add("si");
        opciones3.add("no");
        estaCastrada = new CaracterisiticaDeMascotaRequerida();
        estaCastrada.setValor(opciones3);
        estaCastrada.setDescripcion("esta castrada");
        estaCastrada.setId(3);

        administrador = new Administrador("admin@admin.com");
        organizacion = new Organizacion();

        //Asiganmos contenedor de publicaiones a la organizacion
        contenedorPublicaciones1 = new ContenedorPublicaciones();
        organizacion.setContenedorPublicaciones(contenedorPublicaciones1);

        administrador.setOrganizacionPerteneciente(organizacion);
        administrador.agregarCaracteristica(colorPrincipal);
        administrador.agregarCaracteristica(estaCastrada);

        duenio1 = new Persona("duenio1@duenio.com");
        duenio1.setOrganizacion(organizacion);
        duenio2 = new Persona("duenio2@duenio.com");
        duenio2.setOrganizacion(organizacion);
        duenio3 = new Persona("duenio3@duenio.com");
        duenio3.setOrganizacion(organizacion);

        contactoInteresado = new Contacto();
        contactoInteresado.setEmail("interesadoAdopcion@gmail.com");
        contactoInteresado.setNumeroDeTelefono("2994019614");
        contactoInteresado.setEsPrincipal(true);
        contactosInteresado = new ArrayList<>();
        contactosInteresado.add(contactoInteresado);

        interesado = new Persona("interesado@adopcion.com");
        interesado.setOrganizacion(organizacion);

        estrategiaDeEmail = new EstrategiaDeEmail();
        estrategiaDeWhatsApp= new EstrategiaDeWhatsApp();

        formaComunicacion = new ArrayList<>();
        formaComunicacion.add(estrategiaDeEmail);
        formaComunicacion.add(estrategiaDeWhatsApp);
        informacionPersonal = new InformacionPersonal("gabi","bori",null,123456789,null,"interesadoAdopcion@gmail.com",formaComunicacion,contactosInteresado);
        interesado.setInformacionPersonal(informacionPersonal);
        publicacionIntencionDeAdopcion.setPersonaInteresada(interesado);


        //Inicializamos comodidades para mascota en adopcion
        opcionesComodidades.add("si");
        opcionesComodidades.add("no");
        patio = new Pregunta();
        patio.setValor(opcionesComodidades);
        patio.setDescripcion("con patio");
        patio.setId(4);

        juguetes = new Pregunta();
        juguetes.setValor(opcionesComodidades);
        juguetes.setDescripcion("con juguetes");
        juguetes.setId(5);


        //Completamos caracteristicas e intencion de mascota de la intencion de adopcion
        rptaCaracteristicaIntencion1 = new Respuesta(colorPrincipal,"marron");
        rptaCaracteristicaIntencion2 = new Respuesta(colorSecundario,"blanco");
        rptaCaracteristicaIntencion3 = new Respuesta(estaCastrada,"si");

        rptaComodidadIntencion1 = new Respuesta(patio,"si");
        rptaComodidadIntencion2 = new Respuesta(juguetes,"si");

        //Creacion de mascotas para adopcion
        //Mascota 1
        resp1 = new CaracteristicaDeMascota(colorPrincipal,"marron");
        resp2 = new CaracteristicaDeMascota(colorSecundario,"blanco");
        resp3 = new CaracteristicaDeMascota(estaCastrada,"si");
        mascota1 = MascotaBuilder.crear()
                .conCaracteristicasDeMascota(resp1)
                .conCaracteristicasDeMascota(resp2)
                .conCaracteristicasDeMascota(resp3)
                .conDescripcionFisica("grande")
                .conFotosUrl("/foto1")
                .conPersona(duenio1)
                .construir();

        publicacionMascotaEnAdopcion1.setMascotaEnAdopcion(mascota1);

        //Mascota 2
        resp4 = new CaracteristicaDeMascota(colorPrincipal,"marron");
        resp5 = new CaracteristicaDeMascota(colorSecundario,"blanco");
        resp6 = new CaracteristicaDeMascota(estaCastrada,"si");
        mascota2 = MascotaBuilder.crear()
                .conCaracteristicasDeMascota(resp4)
                .conCaracteristicasDeMascota(resp5)
                .conCaracteristicasDeMascota(resp6)
                .conDescripcionFisica("mediano")
                .conFotosUrl("/foto2")
                .conPersona(duenio2)
                .construir();
        publicacionMascotaEnAdopcion2.setMascotaEnAdopcion(mascota2);

        //Mascota 3
        resp7 = new CaracteristicaDeMascota(colorPrincipal,"marron");
        resp8 = new CaracteristicaDeMascota(colorSecundario,"negro");
        resp9 = new CaracteristicaDeMascota(estaCastrada,"si");
        mascota3 = MascotaBuilder.crear()
                .conCaracteristicasDeMascota(resp4)
                .conCaracteristicasDeMascota(resp5)
                .conCaracteristicasDeMascota(resp6)
                .conDescripcionFisica("mediano")
                .conFotosUrl("/foto2")
                .conPersona(duenio2)
                .construir();
        publicacionMascotaEnAdopcion3.setMascotaEnAdopcion(mascota3);

        //Asiganmos contenedor de publicaiones a la organizacion
        contenedorPublicaciones1 = new ContenedorPublicaciones();
        organizacion.setContenedorPublicaciones(contenedorPublicaciones1);


        //Inicializamos comodidades que pide el duenio1
        rptaComodidadDuenio1 = new Respuesta(patio,"si");
        rptaComodidadDuenio2 = new Respuesta(juguetes,"si");
        publicacionMascotaEnAdopcion1.cargarRespuestasPreguntas(rptaComodidadDuenio1);
        publicacionMascotaEnAdopcion1.cargarRespuestasPreguntas(rptaComodidadDuenio2);

        //Inicializamos comodidades que pide el duenio2
        rptaComodidadDuenio3 = new Respuesta(patio,"si");
        rptaComodidadDuenio4 = new Respuesta(juguetes,"si");
        publicacionMascotaEnAdopcion2.cargarRespuestasPreguntas(rptaComodidadDuenio3);
        publicacionMascotaEnAdopcion2.cargarRespuestasPreguntas(rptaComodidadDuenio4);


        //Inicializamos comodidades que pide el duenio3
        rptaComodidadDuenio5 = new Respuesta(patio,"no");
        rptaComodidadDuenio6 = new Respuesta(juguetes,"si");
        publicacionMascotaEnAdopcion3.cargarRespuestasPreguntas(rptaComodidadDuenio5);
        publicacionMascotaEnAdopcion3.cargarRespuestasPreguntas(rptaComodidadDuenio6);

        publicacionMascotaEnAdopcion1.setEstado(EstadoPublicacion.APROBADA);
        publicacionMascotaEnAdopcion2.setEstado(EstadoPublicacion.APROBADA);
        publicacionMascotaEnAdopcion3.setEstado(EstadoPublicacion.APROBADA);
        publicacionIntencionDeAdopcion.setEstado(EstadoPublicacion.APROBADA);

        contenedorPublicaciones1.agregarPublicacionMascotaEnAdopcion(publicacionMascotaEnAdopcion1);
        contenedorPublicaciones1.agregarPublicacionMascotaEnAdopcion(publicacionMascotaEnAdopcion2);
        contenedorPublicaciones1.agregarPublicacionMascotaEnAdopcion(publicacionMascotaEnAdopcion3);

        rptaComodidadIntencion1 = patio.contestar("si");
        rptaComodidadIntencion2 = juguetes.contestar("si");
        publicacionIntencionDeAdopcion.cargarCaracteristicaMascotaDeseada(rptaCaracteristicaIntencion1);
        publicacionIntencionDeAdopcion.cargarCaracteristicaMascotaDeseada(rptaCaracteristicaIntencion2);
        publicacionIntencionDeAdopcion.cargarCaracteristicaMascotaDeseada(rptaCaracteristicaIntencion3);
        publicacionIntencionDeAdopcion.cargarComodidad(rptaComodidadIntencion1);
        publicacionIntencionDeAdopcion.cargarComodidad(rptaComodidadIntencion2);

        //Agregamos la publicacion de intencion de adopcion al contenedor
        contenedorPublicaciones1.agregarPublicacionIntecnioDeAdopcion(publicacionIntencionDeAdopcion);

        //Inicializamos recomendacion
        recomendacion = new Recomendacion();

        //Inicializamos notificador semanal
        notificadorSemanal = new NotificadorSemanal(recomendacion, organizacion);

    }

    @Test @Ignore
    public void recomendacionGeneradaCorrectamente(){
        List<PublicacionMascotaEnAdopcion> recomendaciones = recomendacion.obtenerPublicacionesRecomendadasPorIntencion(publicacionIntencionDeAdopcion);
        Assert.assertEquals(2,recomendaciones.size());
    }

    
    @Test @Ignore
    public void notificarSemanalMente() throws InterruptedException {
        notificadorSemanal = new NotificadorSemanal(recomendacion, organizacion);
        Timer temporizador = new Timer();
        Integer segundos = 20;

        temporizador.scheduleAtFixedRate(notificadorSemanal,0,1000*segundos);

        Thread.sleep(1000*60);
    }
}
