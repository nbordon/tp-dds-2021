package domain.entities.EstrategiasNotificacion.EstrategiaWhatsApp;

import domain.entities.Contacto;

public interface WhatsAppAdapterInterface {

    void notificarViaWhatsApp(String asunto, String mensaje, Contacto contacto);
}
