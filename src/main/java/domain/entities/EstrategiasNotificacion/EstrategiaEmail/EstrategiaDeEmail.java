package domain.entities.EstrategiasNotificacion.EstrategiaEmail;

import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
import domain.entities.Contacto;

public class EstrategiaDeEmail implements EstrategiaDeNotificacion {

    private static final EmailAdapterInterface notificadorEmail = new EstrategiaMailtrapAdapter();

    @Override
    public void notificar(String asunto, String mensaje, Contacto contacto) {
        notificadorEmail.notificarViaEmail(asunto, mensaje, contacto);
    }
}
