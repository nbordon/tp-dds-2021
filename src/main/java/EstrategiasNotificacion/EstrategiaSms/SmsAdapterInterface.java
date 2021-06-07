package EstrategiasNotificacion.EstrategiaSms;

import entidades.Contacto;

public interface SmsAdapterInterface {
    public void notificarViaSms(String mensaje, Contacto contacto);
}
