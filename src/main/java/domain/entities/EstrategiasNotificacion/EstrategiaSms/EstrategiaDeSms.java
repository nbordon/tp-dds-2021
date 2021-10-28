package domain.entities.EstrategiasNotificacion.EstrategiaSms;

import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
import domain.entities.Contacto;

public class EstrategiaDeSms implements EstrategiaDeNotificacion {

    private static final SmsAdapterInterface notificadorSms = new SmsTwilioAdapter();

    @Override
    public void notificar(String asunto, String mensaje, Contacto contacto) {
        notificadorSms.notificarViaSms(asunto, mensaje, contacto);
    }
}
