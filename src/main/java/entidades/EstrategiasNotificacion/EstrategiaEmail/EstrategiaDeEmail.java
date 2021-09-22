package entidades.EstrategiasNotificacion.EstrategiaEmail;

import entidades.EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;

public class EstrategiaDeEmail implements EstrategiaDeNotificacion {

    private static final EmailAdapterInterface notificadorEmail = new EstrategiaMailtrapAdapter();

    @Override
    public void notificar(String asunto, String mensaje, Contacto contacto) {
        notificadorEmail.notificarViaEmail(asunto, mensaje, contacto);
    }
}
