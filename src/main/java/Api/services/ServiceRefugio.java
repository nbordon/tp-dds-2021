package Api.services;

import Api.services.entities.ListadoHogares;
import Api.services.entities.Usuario;
import Api.services.entities.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServiceRefugio {
    private static ServiceRefugio instance = null;
    private Retrofit retrofit;
    private static final String apiUrl = "https://api.refugiosdds.com.ar/api/";
    private String token = "Bearer JUQBBYJmaKfEFnOLhvjyVp2EqVeEb3vdNQdXBFYEwTBZbqCPBP8krsoXhqPM";

    private ServiceRefugio(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServiceRefugio getInstance(){
        if(instance == null){
            instance= new ServiceRefugio();
        }
        return instance;
    }

    public ListadoHogares listadoHogares(String numOffset) throws IOException {
        RefugioDDS refugioDDS = this.retrofit.create(RefugioDDS.class);
        Call<ListadoHogares> requestHogaresDisponibles = refugioDDS.hogares(token, numOffset);
        ListadoHogares listadoHogares = null;

        try {
            Response<ListadoHogares> responseHogares = requestHogaresDisponibles.execute();
            listadoHogares = responseHogares.body();
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return listadoHogares;

    }

    public UsuarioResponse postUsuarios(String emailUser) throws IOException {
        RefugioDDS refugioDDS = this.retrofit.create((RefugioDDS.class));
        UsuarioResponse respuesta = new UsuarioResponse();
        Response<UsuarioResponse> responseToken = null;
        Usuario userResponse = new Usuario(emailUser);
      try {

            Call<UsuarioResponse> crearUsuario = refugioDDS.usuarios(userResponse);
            responseToken = crearUsuario.execute();
            respuesta.setToken(responseToken.body().getToken());
      } catch (IOException exception){
          exception.printStackTrace();
      }catch (IllegalStateException a){
          a.printStackTrace();
      }
      return respuesta;
    }

}
