package EstrategiasNotificacion.EstrategiaSms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import entidades.Contacto;

public class SmsTwilioAdapter implements SmsAdapterInterface {

    private static final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
    private static final String SERVICE_SID = System.getenv("SERVICE_SID");
    private static final String PREFIJO_ARGENTINA = "+54";

    @Override
    public void notificarViaSms(String asunto, String mensaje, Contacto contacto) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                new com.twilio.type.PhoneNumber(PREFIJO_ARGENTINA + contacto.getNumeroDeTelefono()),
                SERVICE_SID,
                mensaje)
                .create();
    }
}
