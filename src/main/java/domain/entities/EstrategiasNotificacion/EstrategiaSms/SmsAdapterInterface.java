package domain.entities.EstrategiasNotificacion.EstrategiaSms;

import domain.entities.Contacto;

public interface SmsAdapterInterface {

    void notificarViaSms(String asunto, String mensaje, Contacto contacto);
}
