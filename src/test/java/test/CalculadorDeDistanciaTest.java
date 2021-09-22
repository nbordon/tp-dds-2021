package test;

import Api.services.entities.Ubicacion;
import org.junit.Assert;
import org.junit.Test;
import utils.localizador.CalculadorDeDistancia;

public class CalculadorDeDistanciaTest {

    @Test
    public void calcularDistanciaConExisto() {
        Ubicacion origen = new Ubicacion();
        Ubicacion destino = new Ubicacion();
        origen.latitud = -41.1333;
        origen.longitud = -71.3103;
        destino.latitud = -34.6083;
        destino.longitud = -58.3712;

        Double distancia = CalculadorDeDistancia.entre(origen,destino);

        Assert.assertTrue(distancia != 0);
    }

    @Test
    public void distanciaEntreDosPuntosEsCero() {
        Ubicacion origen = new Ubicacion();
        Ubicacion destino = new Ubicacion();
        origen.latitud = -41.1333;
        origen.longitud = -71.3103;
        destino.latitud = -41.1333;
        destino.longitud = -71.3103;

        Double distancia = CalculadorDeDistancia.entre(origen,destino);

        Assert.assertEquals(0, (double) distancia, 0.0);
    }
}
