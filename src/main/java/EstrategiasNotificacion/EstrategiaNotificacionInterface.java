package EstrategiasNotificacion;

import entidades.Contacto;

public interface EstrategiaNotificacionInterface {
    public void notificar(String mensaje, Contacto contacto);
}
