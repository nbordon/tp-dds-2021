package entidades.EstrategiasNotificacion.EstrategiaSms;

import entidades.EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;

public class EstrategiaDeSms implements EstrategiaDeNotificacion {

    private static final SmsAdapterInterface notificadorSms = new SmsTwilioAdapter();

    @Override
    public void notificar(String asunto, String mensaje, Contacto contacto) {
        notificadorSms.notificarViaSms(asunto, mensaje, contacto);
    }
}