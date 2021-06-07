package EstrategiasNotificacion.EstrategiaSms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import entidades.Contacto;

public class SmsTwilioAdapter implements SmsAdapterInterface {
    private static final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
    private static final String SERVICE_SID = System.getenv("SERVICE_SID");
    @Override
    public void notificarViaSms(String mensaje, Contacto contacto) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+54"+contacto.getNumeroDeTelefono()),
                SERVICE_SID,
                mensaje)
                .create();
        System.out.println(message);
    }
}
