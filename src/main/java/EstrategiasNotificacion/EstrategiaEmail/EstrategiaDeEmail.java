package EstrategiasNotificacion.EstrategiaEmail;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import EstrategiasNotificacion.EstrategiaSms.SmsAdapterInterface;
import EstrategiasNotificacion.EstrategiaSms.SmsTwilioAdapter;
import entidades.Contacto;

public class EstrategiaDeEmail implements EstrategiaDeNotificacion {

    private static final EmailAdapterInterface notificadorEmail = new EstrategiaMailtrapAdapter();

    @Override
    public void notificar(String asunto, String mensaje, Contacto contacto) {
        notificadorEmail.notificarViaEmail(asunto, mensaje, contacto);
    }
}
