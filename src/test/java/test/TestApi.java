package test;

import Api.services.ServiceRefugio;
import Api.services.entities.HogarResponse;
import Api.services.entities.ListadoHogares;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class TestApi {

    public ServiceRefugio serviceRefugio = ServiceRefugio.getInstance();

    @Test
    @Ignore
    public void getHogares() throws IOException {
        String numOffset = "1";
        System.out.println("Seleccione uno de los siguientes refugios:");

        ListadoHogares listadoHogares = serviceRefugio.listadoHogares(numOffset);

        for (HogarResponse unHogar : listadoHogares.hogares) {
            System.out.println(unHogar.nombre);
        }
    }

}
