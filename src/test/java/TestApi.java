import Api.services.ServiceRefugio;
import Api.services.entities.Hogar;
import Api.services.entities.ListadoHogares;
import Api.services.entities.Usuario;
import Api.services.entities.UsuarioResponse;
import org.junit.Test;

import java.io.IOException;

public class TestApi {

    public ServiceRefugio serviceRefugio = ServiceRefugio.getInstance();

    @Test
    public void getHogares() throws IOException {
        String numOffset = "1";
        System.out.println("Seleccione uno de los siguientes refugios:");

        ListadoHogares listadoHogares = serviceRefugio.listadoHogares(numOffset);

        for (Hogar unHogar : listadoHogares.hogares) {
            System.out.println(unHogar.nombre);
        }
    }

    @Test
    public void postUsuario() throws IOException {
        String emailPrueba = "pruebaTest4@frba.utn.edu.com.ar";
        Usuario usuarioPrueba = new Usuario(emailPrueba);
        UsuarioResponse usuarioRespuesta = serviceRefugio.postUsuarios(emailPrueba);
        System.out.println((usuarioRespuesta.getToken()));
    }
}
