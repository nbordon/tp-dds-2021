import entidades.Mascotas.CaracterisiticaDeMascotaRequerida;
import entidades.Mascotas.CaracteristicaDeMascota;
import entidades.Mascotas.Mascota;
import entidades.Mascotas.MascotaBuilder;
import entidades.Organizacion.Administrador;
import entidades.Organizacion.Organizacion;
import entidades.Persona;
import exception.CaracteristicaRequeridaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCaracteristicasDeMascota {

    Organizacion organizacion;
    CaracterisiticaDeMascotaRequerida colorPrincipal;
    CaracterisiticaDeMascotaRequerida colorSecundario;
    CaracterisiticaDeMascotaRequerida estaCastrada;
    Administrador administrador;
    Mascota mascota;
    List<String> opciones1;
    List<String> opciones2;
    List<String> opciones3;
    CaracteristicaDeMascota resp1;
    CaracteristicaDeMascota resp2;
    CaracteristicaDeMascota resp3;
    Persona duenio;

    @Before
    public void inicializar(){
        opciones1 = new ArrayList<>();
        opciones2 = new ArrayList<>();
        opciones3 = new ArrayList<>();

        opciones1.add("marron");
        opciones1.add("negro");
        opciones1.add("blanco");

        colorPrincipal = new CaracterisiticaDeMascotaRequerida();
        colorPrincipal.setValor(opciones1);
        colorPrincipal.setDescripcion("color principal");

        opciones2.add("marron");
        opciones2.add("negro");
        opciones2.add("blanco");
        colorSecundario = new CaracterisiticaDeMascotaRequerida();
        colorSecundario.setValor(opciones2);
        colorSecundario.setDescripcion("color secundario");

        opciones3.add("si");
        opciones3.add("no");
        estaCastrada = new CaracterisiticaDeMascotaRequerida();
        estaCastrada.setValor(opciones3);
        estaCastrada.setDescripcion("esta castrada");

        administrador = new Administrador("admin@admin.com");
        organizacion = new Organizacion();
        administrador.setOrganizacionPerteneciente(organizacion);
        administrador.agregarCaracteristica(colorPrincipal);
        administrador.agregarCaracteristica(estaCastrada);

        resp1 = new CaracteristicaDeMascota();
        resp2 = new CaracteristicaDeMascota();
        resp3 = new CaracteristicaDeMascota();

        resp1.setPreguntaALaQuePertenece(colorPrincipal);
        resp2.setPreguntaALaQuePertenece(colorSecundario);
        resp3.setPreguntaALaQuePertenece(estaCastrada);

        duenio = new Persona("duenio@duenio.com");
        duenio.setOrganizacion(organizacion);
    }

    @Test
    public void lasCaracteristicasInicialesFueronAgregadasConExito() {
        Assert.assertEquals(2, organizacion.getCaracteristicasDeMascotasRequeridas().size());
    }

    @Test
    public void agregarNuevaCaracteristica() {
        administrador.agregarCaracteristica(colorSecundario);
        Assert.assertEquals(3, organizacion.getCaracteristicasDeMascotasRequeridas().size());
    }

    @Test
    public void removerCaracteristicas() {
        administrador.removerCaracteristica(estaCastrada);
        Assert.assertEquals(1, organizacion.getCaracteristicasDeMascotasRequeridas().size());
    }

    @Test
    public void laMascotaCargaTodasLasCaracteriticas() throws CaracteristicaRequeridaException {
        resp1 = colorPrincipal.contestar("marron");
        resp2 = estaCastrada.contestar("si");
        mascota = MascotaBuilder.crear()
                .conCaracteristicasDeMascota(resp1)
                .conCaracteristicasDeMascota(resp2)
                .conDescripcionFisica("grande")
                .conFotosUrl("/foto1")
                .conPersona(duenio)
                .construir();

        Assert.assertEquals(2,mascota.getCaracteristicas().size());
    }

    @Test(expected = RuntimeException.class)
    public void faltaCargarUnaCaracteristicaDeLaMascota() throws CaracteristicaRequeridaException{
        resp1 = colorPrincipal.contestar("marron");
        resp2 = estaCastrada.contestar("");
        mascota = MascotaBuilder.crear()
                .conCaracteristicasDeMascota(resp1)
                .conCaracteristicasDeMascota(resp2)
                .conDescripcionFisica("grande")
                .conFotosUrl("/foto1")
                .conPersona(duenio)
                .construir();
    }
}
