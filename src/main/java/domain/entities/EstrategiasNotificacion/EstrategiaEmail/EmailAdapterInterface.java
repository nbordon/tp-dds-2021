package domain.entities.EstrategiasNotificacion.EstrategiaEmail;

import domain.entities.Contacto;

public interface EmailAdapterInterface {
    void notificarViaEmail(String asunto, String mensaje, Contacto contacto);
}
