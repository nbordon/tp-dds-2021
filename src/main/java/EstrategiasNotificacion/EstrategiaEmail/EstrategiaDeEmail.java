package EstrategiasNotificacion.EstrategiaEmail;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;

public class EstrategiaDeEmail implements EstrategiaDeNotificacion {

    @Override
    public void notificar(String mensaje, Contacto contacto) {
        //TODO enviar el email
    }
}
