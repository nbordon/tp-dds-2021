package EstrategiasNotificacion.EstrategiaSms;

import entidades.Contacto;

public interface SmsAdapterInterface {

    void notificarViaSms(String asunto, String mensaje, Contacto contacto);
}
