package Api.services;

import Api.services.entities.ListadoHogares;
import Api.services.entities.Usuario;
import Api.services.entities.UsuarioResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface RefugioDDS {
    @GET("hogares")
    Call<ListadoHogares> hogares(@Header("Authorization") String token, @Query("offset") String offset);

  @POST("usuarios")
    Call<UsuarioResponse> usuarios(@Body Usuario email);
}
