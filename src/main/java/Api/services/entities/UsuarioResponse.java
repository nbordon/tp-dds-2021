package Api.services.entities;

import com.google.gson.annotations.SerializedName;

public class UsuarioResponse {
    @SerializedName("bearer_token")
    String token;

    public String getToken() {
        return token;
    }

}
