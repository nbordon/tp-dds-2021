package Api.services.entities;

import com.google.gson.annotations.SerializedName;

public class UsuarioResponse {

    @SerializedName("bearer_token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
