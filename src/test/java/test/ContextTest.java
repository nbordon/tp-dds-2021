package test;

import Api.services.entities.Hogar;
import Api.services.entities.Ubicacion;
import db.EntityManagerHelper;
import domain.entities.*;
import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
import domain.entities.EstrategiasNotificacion.EstrategiaEmail.EstrategiaDeEmail;
import domain.entities.EstrategiasNotificacion.EstrategiaEmail.EstrategiaEmail;
import domain.entities.EstrategiasNotificacion.EstrategiaSms.EstrategiaDeSms;
import domain.entities.EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
import domain.entities.Mascotas.*;
import domain.entities.Organizacion.Administrador;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Organizacion.PreguntasAdopcion;
import domain.entities.Organizacion.Respuesta;
import domain.entities.publicaciones.EstadoPublicacion;
import domain.entities.publicaciones.PublicacionIntencionDeAdopcion;
import domain.entities.publicaciones.PublicacionMascotaEnAdopcion;
import domain.entities.publicaciones.PublicacionMascotaEncontradaSinChapita;
import exception.VerificadorException;
import org.junit.Ignore;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertNotNull;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

    @Test
    @Ignore
    public void contextUp() {
        assertNotNull(entityManager());
    }

    @Test
    @Ignore
    public void contextUpWithTransaction() throws Exception {
        withTransaction(() -> {
        });
    }

    @Test
    @Ignore
    public void persistirEstrategiasNotificacion() {
        InformacionPersonal ip = new InformacionPersonal();
        List<EstrategiaDeNotificacion> estrategias = new ArrayList<>();
        estrategias.add(new EstrategiaDeEmail());
        estrategias.add(new EstrategiaDeWhatsApp());
        ip.setApellido("Bori");
        ip.setEmail("gabo@gmail.com");
        ip.setNroDocumento(123435667);
        ip.setFormaComunicacion(estrategias);
        Contacto contacto = new Contacto();
        contacto.setNombre("Pedro");
        contacto.setApellido("Perez");
        contacto.setEmail("pedro@gmail.com");
        contacto.setNumeroDeTelefono("11998822");

        Contacto contacto2 = new Contacto();
        contacto2.setNombre("Lionel");
        contacto2.setApellido("Messi");
        contacto2.setEmail("lionel.messi@gmail.com");
        contacto2.setNumeroDeTelefono("101010101010");
        List<Contacto> contactosIp = new ArrayList<>();
        contactosIp.add(contacto);
        contactosIp.add(contacto2);
        ip.setContactos(contactosIp);

        List<EstrategiaDeNotificacion> estrategiasNotif = new ArrayList<>();
        estrategiasNotif.add(new EstrategiaDeWhatsApp());
        estrategiasNotif.add(new EstrategiaDeEmail());
        ip.setFormaComunicacion(estrategiasNotif);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(contacto);
        EntityManagerHelper.getEntityManager().persist(contacto2);
        EntityManagerHelper.getEntityManager().persist(ip);
        EntityManagerHelper.commit();
    }

    @Test
    @Ignore
    public void persistirMascotaConCaracteristica() {
        Mascota m = new Mascota();
        m.setApodo("huesos");
        m.setNombre("Ayudante de santa");
        m.setDescripcionFisica("El perro de Bart Simpson");
        m.setEstado(EstadoMascota.NO_PERDIDA);
        m.setTipoMascota(TipoMascota.PERRO);
        m.setFechaNacimiento(LocalDate.of(2020, 1, 8));
        //Se crea una caracteristica de mascota nueva y se le agregan los posibles valores
        CaracterisiticaDeMascotaRequerida carReq = new CaracterisiticaDeMascotaRequerida();
        carReq.setDescripcion("Esta castrado");
        List<String> valoresRespuesta = new ArrayList<>();
        valoresRespuesta.add("Si");
        valoresRespuesta.add("No");
        carReq.setValor(valoresRespuesta);


        CaracteristicaDeMascota c1 = new CaracteristicaDeMascota();
        c1.setPreguntaALaQuePertenece(carReq);
        c1.setValor(carReq.getValor().get(0));

        List<CaracteristicaDeMascota> caracteristicasC1 = new ArrayList<>();
        caracteristicasC1.add(c1);
        m.setCaracteristicas(caracteristicasC1);
        m.setSexo("Macho");


        Persona persona = new Persona();
        persona.setEmail("gabo@gmail.com");
        persona.setNombreUsuario("gaboxxz");
        persona.setContrasenia("12345678");
        InformacionPersonal infoPersonal = new InformacionPersonal();
        //infoPersonal.setTipoDoc(TipoDeDocumento.DNI);
        infoPersonal.setNroDocumento(ThreadLocalRandom.current().nextInt());
        infoPersonal.setEmail("gabot@gmail.com");
        infoPersonal.setApellido("Test");
        infoPersonal.setNombre("Gabriel");
        infoPersonal.setFechaNacimiento(LocalDate.of(1992, 1, 8));
        //Estrategias
        List<EstrategiaDeNotificacion> estrategias = new ArrayList<EstrategiaDeNotificacion>();
        EstrategiaDeNotificacion sms = new EstrategiaDeSms();
        estrategias.add(sms);
        EstrategiaDeNotificacion wsp = new EstrategiaDeWhatsApp();
        estrategias.add(wsp);
        EstrategiaDeNotificacion email = new EstrategiaEmail();
        estrategias.add(email);

        Contacto contacto = new Contacto();
        contacto.setEsPrincipal(true);
        contacto.setEmail(infoPersonal.getEmail());
        contacto.setNumeroDeTelefono("2994019614");
        List<Contacto> contactos = new ArrayList<Contacto>();
        contactos.add(contacto);
        infoPersonal.setContactos(contactos);
        infoPersonal.setFormaComunicacion(estrategias);
        persona.setInformacionPersonal(infoPersonal);

        Ubicacion ubicacionOrg = new Ubicacion();
        ubicacionOrg.setLongitud(1111.0);
        ubicacionOrg.setLatitud(11111.0);
        Organizacion organizacion = new Organizacion();
        organizacion.setNombre("Mi organizacion");
        organizacion.setUbicacion(ubicacionOrg);
        persona.setOrganizacion(organizacion);

        m.setDuenio(persona);
        Hogar hogar = new Hogar();
        hogar.setNombre("Hogar test 1");
        hogar.setCapacidad(50);
        hogar.setPatio(true);
        hogar.setLugares_disponibles(40);
        hogar.setTelefono("111555444");
        Ubicacion ubicacionHogar = new Ubicacion();
        ubicacionHogar.setLongitud(1312312312.123);
        ubicacionHogar.setLatitud(123123123.00);
        ubicacionHogar.setDireccion("Calle falsa 123, Caba");
        hogar.setUbicacion(ubicacionHogar);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(ubicacionHogar);
        EntityManagerHelper.getEntityManager().persist(hogar);
        EntityManagerHelper.getEntityManager().persist(carReq);
        EntityManagerHelper.getEntityManager().persist(ubicacionOrg);
        EntityManagerHelper.getEntityManager().persist(organizacion);
        EntityManagerHelper.getEntityManager().persist(c1);
        EntityManagerHelper.getEntityManager().persist(contacto);
        EntityManagerHelper.getEntityManager().persist(infoPersonal);
        EntityManagerHelper.getEntityManager().persist(persona);
        EntityManagerHelper.getEntityManager().persist(m);
        EntityManagerHelper.commit();
    }


    @Test
    @Ignore
    public void usuarioAdmin() throws VerificadorException {
        Password pass = new Password("admin");
        Administrador administrador = new Administrador();
        administrador.setNombreUsuario("admin");
        administrador.setContrasenia(pass.getPassword());
        administrador.setEmail("nbordon@frba.utn.edu.ar");

        pass = new Password("voluntario");
        UsuarioVoluntario voluntario = new UsuarioVoluntario();
        voluntario.setNombreUsuario("voluntario");
        voluntario.setContrasenia(pass.getPassword());
        voluntario.setEmail("nbordon@frba.utn.edu.ar");

        pass = new Password("persona");
        Persona persona = new Persona();
        persona.setNombreUsuario("persona");
        persona.setContrasenia(pass.getPassword());
        persona.setEmail("nbordon@frba.utn.edu.ar");

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(administrador);
        EntityManagerHelper.getEntityManager().persist(voluntario);
        EntityManagerHelper.getEntityManager().persist(persona);
        EntityManagerHelper.commit();
    }

    @Test
    @Ignore
    public void listadoMascota() {
        Organizacion organizacion = new Organizacion();

        organizacion.setNombre("Organizacion de prueba");
        organizacion.setAltoFotoEstandar(100);
        organizacion.setAnchoFotoEstandar(100);

        /**
         * Configuro Caracteristicas de mascota requeridas
         */
        CaracterisiticaDeMascotaRequerida estaCastrado = new CaracterisiticaDeMascotaRequerida();
        estaCastrado.setDescripcion("Castrado");
        List<String> valoresCastrado = new ArrayList<>();
        valoresCastrado.add("Si");
        valoresCastrado.add("No");

        CaracterisiticaDeMascotaRequerida colorPrincipal = new CaracterisiticaDeMascotaRequerida();
        colorPrincipal.setDescripcion("Color principal");
        List<String> valoresColorPrincipal = new ArrayList<>();
        valoresColorPrincipal.add("Blanco");
        valoresColorPrincipal.add("Marron");
        valoresColorPrincipal.add("Negro");

        CaracterisiticaDeMascotaRequerida colorSecundario = new CaracterisiticaDeMascotaRequerida();
        colorSecundario.setDescripcion("Color secundario");
        List<String> valoresColorSecundario = new ArrayList<>();
        valoresColorSecundario.add("Blanco");
        valoresColorSecundario.add("Marron");
        valoresColorSecundario.add("Negro");

        estaCastrado.setValor(valoresCastrado);
        colorPrincipal.setValor(valoresColorPrincipal);
        colorSecundario.setValor(valoresColorSecundario);

        organizacion.addCaracteristicaDeMascotasRequerida(estaCastrado, colorPrincipal, colorSecundario);

        /**
         * Configuro preguntas de adopcion
         */
        PreguntasAdopcion tienePatio = new PreguntasAdopcion();
        tienePatio.setDescripcion("¿Tiene patio?");
        List<String> valoresTienePatio = new ArrayList<>();
        valoresTienePatio.add("Si");
        valoresTienePatio.add("No");
        tienePatio.setValor(valoresTienePatio);

        PreguntasAdopcion tieneOtrasMascotas = new PreguntasAdopcion();
        tieneOtrasMascotas.setDescripcion("¿Cuantas mascotas tiene?");
        List<String> valoresTieneOtrasMascotas = new ArrayList<>();
        valoresTieneOtrasMascotas.add("Ninguna");
        valoresTieneOtrasMascotas.add("Una");
        valoresTieneOtrasMascotas.add("Mas de una");
        tieneOtrasMascotas.setValor(valoresTienePatio);

        organizacion.addPreguntasRequeridasDeAdopcion(tieneOtrasMascotas, tienePatio);

        /**
         * Configuro ubicacion
         */
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion("Calle falsa 123");
        ubicacion.setLatitud(-34.45691878581388);
        ubicacion.setLongitud(-58.693763435776745);
        organizacion.setUbicacion(ubicacion);

        /**
         * Persistimos
         */
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(estaCastrado);
        EntityManagerHelper.getEntityManager().persist(colorPrincipal);
        EntityManagerHelper.getEntityManager().persist(colorSecundario);

        Mascota m = new Mascota();
        m.setApodo("huesos");
        m.setNombre("Ayudante de santa");
        m.setDescripcionFisica("El perro de Bart Simpson");
        m.setEstado(EstadoMascota.NO_PERDIDA);
        m.setTipoMascota(TipoMascota.PERRO);
        m.setFechaNacimiento(LocalDate.of(2020, 1, 8));
        CaracterisiticaDeMascotaRequerida carReq = new CaracterisiticaDeMascotaRequerida();
        carReq.setDescripcion("Esta castrado");
        List<String> valoresRespuesta = new ArrayList<>();
        valoresRespuesta.add("Si");
        valoresRespuesta.add("No");
        carReq.setValor(valoresRespuesta);

        Persona persona = new Persona();
        persona.setNombreUsuario("persona");
        persona.setContrasenia("persona");
        persona.setEmail("nbordon@frba.utn.edu.ar");
        persona.setOrganizacion(organizacion);

        EntityManagerHelper.getEntityManager().persist(tieneOtrasMascotas);
        EntityManagerHelper.getEntityManager().persist(tienePatio);
        EntityManagerHelper.getEntityManager().persist(ubicacion);
        EntityManagerHelper.getEntityManager().persist(organizacion);
        EntityManagerHelper.commit();

    }

    @Test
    @Ignore
    public void publicaciones() {
        Organizacion organizacion = new Organizacion();
        organizacion.setNombre("patitas felices");

        PreguntasAdopcion tienePatio = new PreguntasAdopcion();
        tienePatio.setDescripcion("¿Tiene patio?");
        List<String> valoresTienePatio = new ArrayList<>();
        valoresTienePatio.add("Si");
        valoresTienePatio.add("No");
        tienePatio.setValor(valoresTienePatio);

        PreguntasAdopcion tieneOtrasMascotas = new PreguntasAdopcion();
        tieneOtrasMascotas.setDescripcion("¿Cuantas mascotas tiene?");
        List<String> valoresTieneOtrasMascotas = new ArrayList<>();
        valoresTieneOtrasMascotas.add("Ninguna");
        valoresTieneOtrasMascotas.add("Una");
        valoresTieneOtrasMascotas.add("Mas de una");
        tieneOtrasMascotas.setValor(valoresTieneOtrasMascotas);

        organizacion.addPreguntasRequeridasDeAdopcion(tieneOtrasMascotas, tienePatio);

        MascotaEncontradaSinChapita mascotaEncontradaSinChapita = new MascotaEncontradaSinChapita();
        mascotaEncontradaSinChapita.setDescripcionEstadoEncotrado("Fue encontrado en la plaza temblando de frio y sucio");
        mascotaEncontradaSinChapita.setFechaEnLaQueSeEncontro(new Date());

        PublicacionMascotaEncontradaSinChapita publicacionMascotaEncontradaSinChapita = new PublicacionMascotaEncontradaSinChapita();
        publicacionMascotaEncontradaSinChapita.setMascotaEncontradaSinChapita(mascotaEncontradaSinChapita);
        publicacionMascotaEncontradaSinChapita.setTitulo("Gatito encontrado");
        publicacionMascotaEncontradaSinChapita.setEstado(EstadoPublicacion.APROBADA);
        publicacionMascotaEncontradaSinChapita.setOrganizacion(organizacion);

        Persona otraPersona = new Persona();
        otraPersona.setEmail("Pepe@gmail.com");
        otraPersona.setNombreUsuario("pepito");
        otraPersona.setContrasenia("12345678");
        otraPersona.setOrganizacion(organizacion);
        InformacionPersonal otraInfoPersonal = new InformacionPersonal();
        //otraInfoPersonal.setTipoDoc(TipoDeDocumento.DNI);
        otraInfoPersonal.setNroDocumento(ThreadLocalRandom.current().nextInt());
        otraInfoPersonal.setEmail("pepito@gmail.com");
        otraInfoPersonal.setApellido("lopez");
        otraInfoPersonal.setNombre("pepe");
        otraInfoPersonal.setFechaNacimiento(LocalDate.of(1998, 9, 12));


        Persona persona = new Persona();
        persona.setEmail("larry@gmail.com");
        persona.setNombreUsuario("larryjv");
        persona.setContrasenia("12345678");
        InformacionPersonal infoPersonal = new InformacionPersonal();
        //infoPersonal.setTipoDoc(TipoDeDocumento.DNI);
        infoPersonal.setNroDocumento(ThreadLocalRandom.current().nextInt());
        infoPersonal.setEmail("larry@gmail.com");
        infoPersonal.setApellido("Vargas");
        infoPersonal.setNombre("Larry");
        infoPersonal.setFechaNacimiento(LocalDate.of(1994, 7, 12));


        UsuarioVoluntario usuarioVoluntario = new UsuarioVoluntario();
        usuarioVoluntario.setPersonaVoluntaria(persona);
        usuarioVoluntario.setOrganizacion(organizacion);

        Mascota m = new Mascota();
        m.setDuenio(otraPersona);
        m.setSexo("Macho");
        m.setApodo("manchas");
        m.setNombre("santa");
        m.setDescripcionFisica("Cachoroo caniche de 1 año, muy alegre y energico");
        m.setEstado(EstadoMascota.NO_PERDIDA);
        m.setTipoMascota(TipoMascota.PERRO);
        m.setFechaNacimiento(LocalDate.of(2020, 1, 8));
        //Se crea una caracteristica de mascota nueva y se le agregan los posibles valores
        CaracterisiticaDeMascotaRequerida carReq = new CaracterisiticaDeMascotaRequerida();
        carReq.setDescripcion("Esta castrado");
        List<String> valoresRespuesta = new ArrayList<>();
        valoresRespuesta.add("Si");
        valoresRespuesta.add("No");
        carReq.setValor(valoresRespuesta);

        CaracteristicaDeMascota c1 = new CaracteristicaDeMascota();
        c1.setPreguntaALaQuePertenece(carReq);
        c1.setValor(carReq.getValor().get(0));

        List<CaracteristicaDeMascota> caracteristicasC1 = new ArrayList<>();
        caracteristicasC1.add(c1);
        m.setCaracteristicas(caracteristicasC1);


        PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion = new PublicacionMascotaEnAdopcion();
        publicacionMascotaEnAdopcion.setMascotaEnAdopcion(m);
        publicacionMascotaEnAdopcion.setOrganizacion(organizacion);
        publicacionMascotaEnAdopcion.setEstado(EstadoPublicacion.PENDIENTE);
        publicacionMascotaEnAdopcion.setTitulo("Perrito busca hogar");

        Respuesta respuestaComodidad1 = new Respuesta();
        respuestaComodidad1.setPreguntaALaQuePertenece(tieneOtrasMascotas);
        respuestaComodidad1.setValor(tieneOtrasMascotas.getValor().get(0));

        Respuesta respuestaComodidad2 = new Respuesta();
        respuestaComodidad2.setPreguntaALaQuePertenece(tienePatio);
        respuestaComodidad2.setValor(tienePatio.getValor().get(0));

        publicacionMascotaEnAdopcion.cargarRespuestasPreguntas(respuestaComodidad1);
        publicacionMascotaEnAdopcion.cargarRespuestasPreguntas(respuestaComodidad2);

        Persona personaInteresada = new Persona();
        personaInteresada.setEmail("Mati@gmail.com");
        personaInteresada.setNombreUsuario("matii");
        personaInteresada.setContrasenia("12345678");
        personaInteresada.setOrganizacion(organizacion);
        InformacionPersonal infoPersonalPersonaInteresada = new InformacionPersonal();
        //infoPersonal.setTipoDoc(TipoDeDocumento.DNI);
        infoPersonalPersonaInteresada.setNroDocumento(ThreadLocalRandom.current().nextInt());
        infoPersonalPersonaInteresada.setEmail("Mati@gmail.com");
        infoPersonalPersonaInteresada.setApellido("Perez");
        infoPersonalPersonaInteresada.setNombre("Mati");
        infoPersonalPersonaInteresada.setFechaNacimiento(LocalDate.of(1994, 7, 12));
        personaInteresada.setInformacionPersonal(infoPersonalPersonaInteresada);


        PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion = new PublicacionIntencionDeAdopcion();
        publicacionIntencionDeAdopcion.setOrganizacion(organizacion);
        publicacionIntencionDeAdopcion.setTitulo("Busco gatito");
        publicacionIntencionDeAdopcion.setPersonaInteresada(personaInteresada);
        publicacionIntencionDeAdopcion.cargarCaracteristicaMascotaDeseada(c1);
        publicacionIntencionDeAdopcion.cargarComodidad(respuestaComodidad2);
        publicacionIntencionDeAdopcion.setEstado(EstadoPublicacion.PENDIENTE);


        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(organizacion);
        EntityManagerHelper.getEntityManager().persist(tienePatio);
        EntityManagerHelper.getEntityManager().persist(tieneOtrasMascotas);
        EntityManagerHelper.getEntityManager().persist(respuestaComodidad1);
        EntityManagerHelper.getEntityManager().persist(respuestaComodidad2);
        EntityManagerHelper.getEntityManager().persist(infoPersonal);
        EntityManagerHelper.getEntityManager().persist(infoPersonalPersonaInteresada);
        EntityManagerHelper.getEntityManager().persist(otraInfoPersonal);
        EntityManagerHelper.getEntityManager().persist(persona);
        EntityManagerHelper.getEntityManager().persist(otraPersona);
        EntityManagerHelper.getEntityManager().persist(personaInteresada);
        EntityManagerHelper.getEntityManager().persist(carReq);
        EntityManagerHelper.getEntityManager().persist(c1);
        EntityManagerHelper.getEntityManager().persist(m);
        EntityManagerHelper.getEntityManager().persist(usuarioVoluntario);
        EntityManagerHelper.getEntityManager().persist(mascotaEncontradaSinChapita);
        EntityManagerHelper.getEntityManager().persist(publicacionMascotaEncontradaSinChapita);
        EntityManagerHelper.getEntityManager().persist(publicacionMascotaEnAdopcion);
        EntityManagerHelper.getEntityManager().persist(publicacionIntencionDeAdopcion);
        EntityManagerHelper.commit();
    }

    @Test
    @Ignore
    public void otroVoluntario() throws VerificadorException {
        Password pass = new Password("voluntario2");
        Persona persona = new Persona();
        persona.setNombreUsuario("voluntario2");
        persona.setContrasenia(pass.getPassword());
        persona.setEmail("nbordon@frba.utn.edu.ar");

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(persona);
        EntityManagerHelper.commit();


    }

    @Test
    @Ignore
    public void infoPersonal() throws VerificadorException {
        List<EstrategiaDeNotificacion> estrategias = new ArrayList<>();
        estrategias.add(new EstrategiaDeEmail());
        Contacto contacto = new Contacto();
        contacto.setNombre("Pedro");
        contacto.setApellido("Perez");
        contacto.setEmail("pedro@gmail.com");
        contacto.setNumeroDeTelefono("11998822");
        contacto.setEsPrincipal(true);
        List<Contacto> contactos = new ArrayList<>();
        contactos.add(contacto);
        InformacionPersonal infoPersonal = new InformacionPersonal("ceci", "rocca", null, 1111100011, LocalDate.of(1994, 7, 12), "ceci@rocca", estrategias, contactos);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(contacto);

        EntityManagerHelper.getEntityManager().persist(infoPersonal);
        EntityManagerHelper.commit();
    }
}