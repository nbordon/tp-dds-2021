import entidades.mascotas.CaracteristicaDeMascota;
import entidades.organizacion.Organizacion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestCaracteristicasDeMascota {
    Organizacion organizacion = new Organizacion();
    CaracteristicaDeMascota colorPrincipal = new CaracteristicaDeMascota();
    CaracteristicaDeMascota colorSecundario = new CaracteristicaDeMascota();
    CaracteristicaDeMascota estaCastrada = new CaracteristicaDeMascota();

    @Before
    public void inicializar(){
        colorPrincipal.setDescripcion("Color Principal");
        colorSecundario.setDescripcion("Color Secundario");
        estaCastrada.setValor("Esta Castrado/a");

        organizacion.addCaracteristicasDeMascotasRequerida(colorPrincipal, colorSecundario, estaCastrada);
    }

    @Test
    public void agregarNuevasCaracteristicas(){
        List<CaracteristicaDeMascota> caracteristicasOrganizacion = organizacion.getCaracteristicasDeMascotasRequeridas();
        Assert.assertEquals(3,caracteristicasOrganizacion.size());
    }

    @Test
    public void removerCaracteristicas(){
        organizacion.removeCaracteristicaDeMascotasRequerida(estaCastrada);

        List<CaracteristicaDeMascota> caracteristicasOrganizacion = organizacion.getCaracteristicasDeMascotasRequeridas();
        Assert.assertEquals(2,caracteristicasOrganizacion.size());
    }
}
