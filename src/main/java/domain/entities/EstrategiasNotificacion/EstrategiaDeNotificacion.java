package domain.entities.EstrategiasNotificacion;

import domain.entities.Contacto;


public interface EstrategiaDeNotificacion {

    void notificar(String asunto, String mensaje, Contacto contacto);
}
