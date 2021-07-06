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
    CaracteristicaDeMascota colorPrincipal;
    CaracteristicaDeMascota colorSecundario;
    CaracteristicaDeMascota estaCastrada;
    Administrador administrador;
    Persona duenioMascota;
    Persona interesado;
    Mascota mascota;
    List<CaracteristicaDeMascota> listaCaracteristicas;
    String linkBaja;
    IntencionDeAdopcion publicacionIntencion;

    @Before
    public void inicializar(){
        duenioMascota = new Persona("emailTrucho42069@usuario.com");
        mascota = new Mascota();
        listaCaracteristicas = new ArrayList<>();
        colorPrincipal = new CaracteristicaDeMascota();
        colorPrincipal.setDescripcion("color principal");
        colorSecundario = new CaracteristicaDeMascota();
        colorSecundario.setDescripcion("color secundario");
        listaCaracteristicas.add(colorPrincipal);
        listaCaracteristicas.add(colorSecundario);
        estaCastrada = new CaracteristicaDeMascota();
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
        publicacionIntencion = new IntencionDeAdopcion();
        publicacionIntencion.cambiarEstadoAPendiente();
        publicacionIntencion.setPersonaInteresada(interesado);
        publicacionIntencion.setMascota(mascota);

    }

    @Test
    public void publicarMascotaEnAdopcion(){
        //PublicacionMascotaEnAdopcion publicacionAdopcion = new PublicacionMascotaEnAdopcion(mascota,"Adopcion de pepito", new ArrayList());
      //  publicacionAdopcion.setMascota(mascota);
        // publicacionAdopcion.notificar();
        //No esta cargando las caracteristicas a la mascota pq "this.mascotas" is null
        //TODO terminar test: agregar respuestas a pregunta
        //TODO ver que la publicacion se agregue a la lista en espera de aprobacion
        //TODO ver que se envie el mensaje
    }

    @Test
    public void interesDeAdopcion(){
        //publicacionIntencion.notificar();

    //TODO terminar estrategiaDeEmail

    }

}
