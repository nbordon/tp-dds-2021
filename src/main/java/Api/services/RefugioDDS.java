package Api.services;

import Api.services.entities.ListadoHogares;
import Api.services.entities.UsuarioResponse;
import entidades.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RefugioDDS {

    @GET("hogares")
    Call<ListadoHogares> muestroHogares(@Header("Authorization") String token, @Query("offset") String offset);

    @POST("usuarios")
    Call<UsuarioResponse> envioUsuarios(@Body Usuario email);
}
