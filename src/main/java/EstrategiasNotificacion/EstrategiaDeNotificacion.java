package EstrategiasNotificacion;

import entidades.Contacto;

public interface EstrategiaDeNotificacion {

    void notificar(String mensaje, Contacto contacto);
}
