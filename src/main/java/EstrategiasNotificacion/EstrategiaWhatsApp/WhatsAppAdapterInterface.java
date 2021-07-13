package EstrategiasNotificacion.EstrategiaWhatsApp;

import entidades.Contacto;

public interface WhatsAppAdapterInterface {

    void notificarViaWhatsApp(String asunto, String mensaje, Contacto contacto);
}
