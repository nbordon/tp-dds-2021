package domain.entities.EstrategiasNotificacion.EstrategiaWhatsApp;

import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
import domain.entities.Contacto;


public class EstrategiaDeWhatsApp implements EstrategiaDeNotificacion {

    private static final WhatsAppAdapterInterface notificadorWhatsApp = new WhastAppTwilioAdapter();

    @Override
    public void notificar(String asunto, String mensaje, Contacto contacto) {
        notificadorWhatsApp.notificarViaWhatsApp(asunto, mensaje, contacto);
    }
}
