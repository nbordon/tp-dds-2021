package EstrategiasNotificacion.EstrategiaEmail;

import entidades.Contacto;

public interface EmailAdapterInterface {
    void notificarViaEmail(String asunto, String mensaje, Contacto contacto);
}
