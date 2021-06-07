package EstrategiasNotificacion.EstrategiaWhatsApp;

import EstrategiasNotificacion.EstrategiaNotificacionInterface;
import entidades.Contacto;

public class EstrategiaWhatsApp implements EstrategiaNotificacionInterface {

    private static final WhatsAppAdapterInterface notificadorWhatsApp = new WhastAppTwilioAdapter();

    @Override
    public void notificar(String mensaje, Contacto contacto) {
        notificadorWhatsApp.notificarViaWhatsApp(mensaje, contacto);
    }
}
