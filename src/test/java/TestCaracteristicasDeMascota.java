import entidades.Mascotas.CaracteristicaDeMascota;
import entidades.Mascotas.Mascota;
import entidades.Mascotas.MascotaBuilder;
import entidades.Organizacion.Administrador;
import entidades.Organizacion.Organizacion;
import exception.CaracteristicaRequeridaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCaracteristicasDeMascota {

    Organizacion organizacion;
    CaracteristicaDeMascota colorPrincipal;
    CaracteristicaDeMascota colorSecundario;
    CaracteristicaDeMascota estaCastrada;
    Administrador administrador;
    Mascota mascota;

    @Before
    public void inicializar(){
        colorPrincipal = new CaracteristicaDeMascota();
        colorPrincipal.setDescripcion("color principal");
        colorSecundario = new CaracteristicaDeMascota();
        colorSecundario.setDescripcion("color secundario");
        estaCastrada = new CaracteristicaDeMascota();
        estaCastrada.setDescripcion("esta castrada");
        administrador = new Administrador("admin@admin.com");
        organizacion = new Organizacion();
        administrador.setOrganizacionPerteneciente(organizacion);
        administrador.agregarCaracteristica(colorPrincipal);
        administrador.agregarCaracteristica(estaCastrada);

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
        colorPrincipal.setValor("marron");
        estaCastrada.setValor("si");
        mascota = MascotaBuilder.crear()
                .conCaracteristicasDeMascota(colorPrincipal)
                .conCaracteristicasDeMascota(estaCastrada)
                .conDescripcionFisica("grande")
                .conFotosUrl("/foto1")
                .construir();

        Assert.assertEquals(2,mascota.getCaracteristicas().size());
    }

    @Test(expected = CaracteristicaRequeridaException.class)
    public void faltaCargarUnaCaracteristicaDeLaMascota() throws CaracteristicaRequeridaException{
        estaCastrada.setValor("si");
        mascota = MascotaBuilder.crear()
                .conCaracteristicasDeMascota(colorPrincipal)
                .conCaracteristicasDeMascota(estaCastrada)
                .conDescripcionFisica("grande")
                .conFotosUrl("/foto1")
                .construir();
    }
}
