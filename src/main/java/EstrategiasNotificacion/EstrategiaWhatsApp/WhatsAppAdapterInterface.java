package EstrategiasNotificacion.EstrategiaWhatsApp;

import entidades.Contacto;

public interface WhatsAppAdapterInterface {
    public void notificarViaWhatsApp(String mensaje, Contacto contacto);
}
