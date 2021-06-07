package EstrategiasNotificacion.EstrategiaSms;

import EstrategiasNotificacion.EstrategiaNotificacionInterface;
import entidades.Contacto;

public class EstrategiaSms implements EstrategiaNotificacionInterface {

    private static final SmsAdapterInterface notificadorSms = new SmsTwilioAdapter();
    @Override
    public void notificar(String mensaje , Contacto contacto) {
        notificadorSms.notificarViaSms(mensaje, contacto);
    }
}
