package EstrategiasNotificacion.EstrategiaWhatsApp;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;

public class EstrategiaDeWhatsApp implements EstrategiaDeNotificacion {

    private static final WhatsAppAdapterInterface notificadorWhatsApp = new WhastAppTwilioAdapter();

    @Override
    public void notificar(String mensaje, Contacto contacto) {
        notificadorWhatsApp.notificarViaWhatsApp(mensaje, contacto);
    }
}
