package EstrategiasNotificacion.EstrategiaSms;

import entidades.Contacto;

public interface SmsAdapterInterface {

    void notificarViaSms(String mensaje, Contacto contacto);
}
