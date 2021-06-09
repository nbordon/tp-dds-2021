import entidades.Mascotas.CaracteristicaDeMascota;
import entidades.Organizacion.Organizacion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCaracteristicasDeMascota {

    Organizacion organizacion = new Organizacion();
    CaracteristicaDeMascota colorPrincipal = new CaracteristicaDeMascota("Color Principal", "Marron");
    CaracteristicaDeMascota colorSecundario = new CaracteristicaDeMascota("Color Secundario", "Blanco");
    CaracteristicaDeMascota estaCastrada = new CaracteristicaDeMascota("Esta Castrado/a", "No");
    List<CaracteristicaDeMascota> caracteristicas = new ArrayList<>();

    @Before
    public void inicializar() {
        caracteristicas.add(colorPrincipal);
        caracteristicas.add(estaCastrada);
        organizacion.addCaracteristicaDeMascotasRequerida(caracteristicas);
    }

    @Test
    public void lasCaracteristicasInicialesFueronAgregadasConExito() {
        Assert.assertEquals(2, organizacion.getCaracteristicasDeMascotasRequeridas().size());
    }

    @Test
    public void agregarNuevaCaracteristica() {
        organizacion.addCaracteristicaDeMascotasRequerida(colorSecundario);
        Assert.assertEquals(3, organizacion.getCaracteristicasDeMascotasRequeridas().size());
    }

    @Test
    public void removerCaracteristicas() {
        organizacion.removeCaracteristicaDeMascotasRequerida(estaCastrada);
        Assert.assertEquals(1, organizacion.getCaracteristicasDeMascotasRequeridas().size());
    }
}
