import entidades.Mascotas.CaracterisiticaDeMascotaRequerida;
import entidades.Mascotas.CaracteristicaDeMascota;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Administrador;
import entidades.Organizacion.Organizacion;
import entidades.Persona;
import org.junit.Before;
import org.junit.Test;
import publicaciones.IntencionDeAdopcion;
import publicaciones.PublicacionMascotaEnAdopcion;

import java.util.ArrayList;
import java.util.List;

public class TestPublicaciones {
    Organizacion organizacion;
    CaracterisiticaDeMascotaRequerida colorPrincipal;
    CaracterisiticaDeMascotaRequerida colorSecundario;
    CaracterisiticaDeMascotaRequerida estaCastrada;
    Administrador administrador;
    Persona duenioMascota;
    Persona interesado;
    Mascota mascota;
    List<CaracteristicaDeMascota> listaCaracteristicas;
    PublicacionMascotaEnAdopcion publicacionAdopcion;
    String linkBaja;
    IntencionDeAdopcion publicacionIntencion;

    List<String> opciones1;
    List<String> opciones2;
    List<String> opciones3;
    CaracteristicaDeMascota resp1;
    CaracteristicaDeMascota resp2;
    CaracteristicaDeMascota resp3;

    @Before
    public void inicializar(){
        opciones1 = new ArrayList<>();
        opciones2 = new ArrayList<>();
        opciones3 = new ArrayList<>();

        opciones1.add("marron");
        opciones1.add("negro");
        opciones1.add("blanco");

        duenioMascota = new Persona("emailTrucho42069@usuario.com");
        mascota = new Mascota();
        listaCaracteristicas = new ArrayList<>();

        colorPrincipal = new CaracterisiticaDeMascotaRequerida();
        colorPrincipal.setValor(opciones1);
        colorPrincipal.setDescripcion("color principal");

        opciones2.add("marron");
        opciones2.add("negro");
        opciones2.add("blanco");
        colorSecundario = new CaracterisiticaDeMascotaRequerida();
        colorSecundario.setValor(opciones2);
        colorSecundario.setDescripcion("color secundario");

        resp1 = colorPrincipal.contestar("marron");
        resp2 = colorPrincipal.contestar("blanco");

        listaCaracteristicas.add(resp1);
        listaCaracteristicas.add(resp2);

        opciones3.add("si");
        opciones3.add("no");
        estaCastrada = new CaracterisiticaDeMascotaRequerida();
        estaCastrada.setValor(opciones3);
        estaCastrada.setDescripcion("esta castrada");

        mascota.setApodo("El comandante");
        mascota.setCaracteristicas(listaCaracteristicas);
        administrador = new Administrador("admin@admin.com");
        organizacion = new Organizacion();
        duenioMascota.setOrganizacion(organizacion);
        duenioMascota.registrarMascota(mascota);
        mascota.setDuenio(duenioMascota);
        administrador.setOrganizacionPerteneciente(organizacion);
        administrador.agregarCaracteristica(colorPrincipal);
        administrador.agregarCaracteristica(estaCastrada);
        interesado = new Persona("emailInteresado@email.com");
        linkBaja = "unLink";
        publicacionIntencion.cambiarEstadoAPendiente();
        publicacionIntencion.setPersonaInteresada(interesado);
        publicacionIntencion.setMascota(mascota);

    }

    @Test
    public void publicarMascotaEnAdopcion(){
        publicacionAdopcion.setMascota(mascota);
        publicacionAdopcion.notificar();
        //No esta cargando las caracteristicas a la mascota pq "this.mascotas" is null
        //TODO terminar test: agregar respuestas a pregunta
        //TODO ver que la publicacion se agregue a la lista en espera de aprobacion
        //TODO ver que se envie el mensaje
    }

    @Test
    public void interesDeAdopcion(){
        publicacionIntencion.notificar();

    //TODO terminar estrategiaDeEmail

    }

}
