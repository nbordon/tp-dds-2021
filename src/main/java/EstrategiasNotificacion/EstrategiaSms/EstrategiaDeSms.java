package EstrategiasNotificacion.EstrategiaSms;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;

public class EstrategiaDeSms implements EstrategiaDeNotificacion {

    private static final SmsAdapterInterface notificadorSms = new SmsTwilioAdapter();

    @Override
    public void notificar(String mensaje, Contacto contacto) {
        notificadorSms.notificarViaSms(mensaje, contacto);
    }
}
