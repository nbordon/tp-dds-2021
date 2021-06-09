package EstrategiasNotificacion.EstrategiaWhatsApp;

import entidades.Contacto;

public interface WhatsAppAdapterInterface {

    void notificarViaWhatsApp(String mensaje, Contacto contacto);
}
