package test;

import Api.services.entities.Ubicacion;
import db.EntityManagerHelper;
import domain.entities.*;
import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
import domain.entities.EstrategiasNotificacion.EstrategiaEmail.EstrategiaDeEmail;
import domain.entities.EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
import domain.entities.Mascotas.CaracterisiticaDeMascotaRequerida;
import domain.entities.Organizacion.Administrador;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Organizacion.PreguntasAdopcion;
import exception.VerificadorException;
import org.junit.Ignore;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class TestInitInfo extends AbstractPersistenceTest implements WithGlobalEntityManager{
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
    public void initDataOrganizacionPatitasFelices() throws VerificadorException {
        /*
        * Caracteristicas de mascota
        * */
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

        /*
        * Preguntas de adopcion
        * */

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

        /*
         * Ubicacion
         * */
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion("Corrientes 2320");
        ubicacion.setLatitud(-34.6048201);
        ubicacion.setLongitud(-58.4019815);

        /*
        * Organizacion
        * */
        Organizacion organizacion1 = new Organizacion();
        organizacion1.setNombre("Patitas Felices");
        organizacion1.setAltoFotoEstandar(100);
        organizacion1.setAnchoFotoEstandar(100);
        organizacion1.setCaracteristicasDeMascotasRequeridas(new ArrayList<>());
        organizacion1.addCaracteristicaDeMascotasRequerida(estaCastrado);
        organizacion1.addCaracteristicaDeMascotasRequerida(colorPrincipal);
        organizacion1.addCaracteristicaDeMascotasRequerida(colorSecundario);
        organizacion1.addPreguntasRequeridasDeAdopcion(tieneOtrasMascotas,tienePatio);
        organizacion1.setUbicacion(ubicacion);

        /*
        * Informacion Personal
        * */

        ArrayList<EstrategiaDeNotificacion> notificaciones = new ArrayList<>();
        notificaciones.add(new EstrategiaDeEmail());
        notificaciones.add(new EstrategiaDeWhatsApp());

        ArrayList<Contacto> contactos = new ArrayList<>();
        Contacto contacto = new Contacto();
        contacto.setEmail("patitaFeliz@gmail.com");
        contacto.setNumeroDeTelefono("1163647340");
        contacto.setNombre("Patita");
        contacto.setApellido("Feliz");
        contacto.setEsPrincipal(true);
        contactos.add(contacto);

        InformacionPersonal info = new InformacionPersonal();
        info.setEmail("patitaFeliz@gmail.com");
        info.setNombre("Patita");
        info.setApellido("Feliz");
        info.setNroDocumento(11222333);
        info.setContactos(contactos);
        info.setTipoDoc(TipoDeDocumento.DNI);
        info.setFechaNacimiento(LocalDate.of(1992,3,12));
        info.setFormaComunicacion(notificaciones);


        /*
        * Usuarios de la organizacion
        * */
        Password passwordPersona = new Password("12345678Aa");
        Persona persona = new Persona();
        persona.setEmail("patitaFeliz@gmail.com");
        persona.setNombreUsuario("personafeliz");
        persona.setContrasenia(passwordPersona.getPassword());
        persona.setOrganizacion(organizacion1);
        persona.setInformacionPersonal(info);

        Password passwordVoluntario = new Password("12345678Aa");
        UsuarioVoluntario voluntario = new UsuarioVoluntario();
        voluntario.setEmail("voluntarioFeliz@gmail.com");
        voluntario.setNombreUsuario("voluntario");
        voluntario.setContrasenia(passwordVoluntario.getPassword());
        voluntario.setPersonaVoluntaria(persona);
        voluntario.setOrganizacion(organizacion1);

        Password passwordAdmin = new Password("12345678Aa");
        Administrador administrador = new Administrador();
        administrador.setNombreUsuario("adminfeliz");
        administrador.setEmail("administradorFeliz@gmail.com");
        administrador.setNombre("Administrador Patitas Felices");
        administrador.setContrasenia(passwordAdmin.getPassword());
        administrador.setOrganizacionPerteneciente(organizacion1);

        /*
        * Persistencia
        * */
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(estaCastrado);
        EntityManagerHelper.getEntityManager().persist(colorPrincipal);
        EntityManagerHelper.getEntityManager().persist(colorSecundario);
        EntityManagerHelper.getEntityManager().persist(tieneOtrasMascotas);
        EntityManagerHelper.getEntityManager().persist(tienePatio);
        EntityManagerHelper.getEntityManager().persist(ubicacion);
        EntityManagerHelper.getEntityManager().persist(organizacion1);
        EntityManagerHelper.getEntityManager().persist(contacto);
        EntityManagerHelper.getEntityManager().persist(info);
        EntityManagerHelper.getEntityManager().persist(persona);
        EntityManagerHelper.getEntityManager().persist(voluntario);
        EntityManagerHelper.getEntityManager().persist(administrador);
        EntityManagerHelper.commit();
    }

    @Test
    @Ignore
    public void initDataOrganizacionMascotasSOS() throws VerificadorException {
        /*
         * Caracteristicas de mascota
         * */
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

        CaracterisiticaDeMascotaRequerida colorOjos = new CaracterisiticaDeMascotaRequerida();
        colorOjos.setDescripcion("Color ojos");
        List<String> valorColorOjos = new ArrayList<>();
        valorColorOjos.add("Azul");
        valorColorOjos.add("Marron");
        valorColorOjos.add("Negro");

        estaCastrado.setValor(valoresCastrado);
        colorPrincipal.setValor(valoresColorPrincipal);
        colorSecundario.setValor(valoresColorSecundario);
        colorOjos.setValor(valorColorOjos);

        /*
         * Preguntas de adopcion
         * */

        PreguntasAdopcion tipoVivienda = new PreguntasAdopcion();
        tipoVivienda.setDescripcion("¿Tipo de vivienda?");
        List<String> valoresTipoVivienda = new ArrayList<>();
        valoresTipoVivienda.add("Casa");
        valoresTipoVivienda.add("Dapartamento");
        valoresTipoVivienda.add("Estancia");
        tipoVivienda.setValor(valoresTipoVivienda);


        PreguntasAdopcion tieneOtrasMascotas = new PreguntasAdopcion();
        tieneOtrasMascotas.setDescripcion("¿Cuantas mascotas tiene?");
        List<String> valoresTieneOtrasMascotas = new ArrayList<>();
        valoresTieneOtrasMascotas.add("Ninguna");
        valoresTieneOtrasMascotas.add("Una");
        valoresTieneOtrasMascotas.add("Mas de una");
        tieneOtrasMascotas.setValor(valoresTieneOtrasMascotas);

        /*
         * Ubicacion
         * */
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion("Av. Cabildo 3449");
        ubicacion.setLatitud(-34.550790);
        ubicacion.setLongitud(-58.466656);

        /*
         * Organizacion
         * */
        Organizacion organizacion1 = new Organizacion();
        organizacion1.setNombre("Mascotas SOS");
        organizacion1.setAltoFotoEstandar(100);
        organizacion1.setAnchoFotoEstandar(100);
        organizacion1.setCaracteristicasDeMascotasRequeridas(new ArrayList<>());
        organizacion1.addCaracteristicaDeMascotasRequerida(estaCastrado);
        organizacion1.addCaracteristicaDeMascotasRequerida(colorPrincipal);
        organizacion1.addCaracteristicaDeMascotasRequerida(colorSecundario);
        organizacion1.addCaracteristicaDeMascotasRequerida(colorOjos);
        organizacion1.addPreguntasRequeridasDeAdopcion(tieneOtrasMascotas,tipoVivienda);
        organizacion1.setUbicacion(ubicacion);

        /*
         * Informacion Personal
         * */

        ArrayList<EstrategiaDeNotificacion> notificaciones = new ArrayList<>();
        notificaciones.add(new EstrategiaDeEmail());
        notificaciones.add(new EstrategiaDeWhatsApp());

        ArrayList<Contacto> contactos = new ArrayList<>();
        Contacto contacto = new Contacto();
        contacto.setEmail("mascotaSOS@gmail.com");
        contacto.setNumeroDeTelefono("1163647340");
        contacto.setNombre("Mascotas");
        contacto.setApellido("SOS");
        contacto.setEsPrincipal(true);
        contactos.add(contacto);

        InformacionPersonal info = new InformacionPersonal();
        info.setEmail("mascotasSOS@gmail.com");
        info.setNombre("Mascota");
        info.setApellido("SOS");
        info.setNroDocumento(12123124);
        info.setContactos(contactos);
        info.setTipoDoc(TipoDeDocumento.DNI);
        info.setFechaNacimiento(LocalDate.of(1992,9,23));
        info.setFormaComunicacion(notificaciones);


        /*
         * Usuarios de la organizacion
         * */
        Password passwordPersona = new Password("12345678Aa");
        Persona persona = new Persona();
        persona.setEmail("mascotasSOS@gmail.com");
        persona.setNombreUsuario("mascotasos");
        persona.setContrasenia(passwordPersona.getPassword());
        persona.setOrganizacion(organizacion1);
        persona.setInformacionPersonal(info);

        Password passwordVoluntario = new Password("12345678Aa");
        UsuarioVoluntario voluntario = new UsuarioVoluntario();
        voluntario.setEmail("voluntarioSOS@gmail.com");
        voluntario.setNombreUsuario("voluntarisos");
        voluntario.setContrasenia(passwordVoluntario.getPassword());
        voluntario.setPersonaVoluntaria(persona);
        voluntario.setOrganizacion(organizacion1);

        Password passwordAdmin = new Password("12345678Aa");
        Administrador administrador = new Administrador();
        administrador.setNombreUsuario("adminsos");
        administrador.setEmail("adminSOS@gmail.com");
        administrador.setNombre("Administrador Mascotas SOS");
        administrador.setContrasenia(passwordAdmin.getPassword());
        administrador.setOrganizacionPerteneciente(organizacion1);

        /*
         * Persistencia
         * */
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(estaCastrado);
        EntityManagerHelper.getEntityManager().persist(colorPrincipal);
        EntityManagerHelper.getEntityManager().persist(colorSecundario);
        EntityManagerHelper.getEntityManager().persist(colorOjos);
        EntityManagerHelper.getEntityManager().persist(tieneOtrasMascotas);
        EntityManagerHelper.getEntityManager().persist(tipoVivienda);
        EntityManagerHelper.getEntityManager().persist(ubicacion);
        EntityManagerHelper.getEntityManager().persist(organizacion1);
        EntityManagerHelper.getEntityManager().persist(contacto);
        EntityManagerHelper.getEntityManager().persist(info);
        EntityManagerHelper.getEntityManager().persist(persona);
        EntityManagerHelper.getEntityManager().persist(voluntario);
        EntityManagerHelper.getEntityManager().persist(administrador);
        EntityManagerHelper.commit();
    }
}
