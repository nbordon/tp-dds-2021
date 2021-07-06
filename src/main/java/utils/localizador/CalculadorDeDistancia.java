package utils.localizador;

import Api.services.entities.Ubicacion;

public class CalculadorDeDistancia {

    private static final double RADIO_TIERRA = 6371.01;

    public static double entre(Ubicacion origen, Ubicacion destino) {
        double latitudPunto1 = Math.toRadians(origen.latitud);
        double longitudPunto1 = Math.toRadians(origen.longitud);
        double latitudPunto2 = Math.toRadians(destino.latitud);
        double longitudPunto2 = Math.toRadians(destino.longitud);

        double distancia = RADIO_TIERRA * Math.acos(Math.sin(latitudPunto1) * Math.sin(latitudPunto2)
                + Math.cos(latitudPunto1) * Math.cos(latitudPunto2) * Math.cos(longitudPunto1 - longitudPunto2));

        return distancia;
    }
}