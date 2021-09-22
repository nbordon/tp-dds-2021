package entidades.EstrategiasNotificacion;

import entidades.Contacto;


public interface EstrategiaDeNotificacion {

    void notificar(String asunto, String mensaje, Contacto contacto);
}
