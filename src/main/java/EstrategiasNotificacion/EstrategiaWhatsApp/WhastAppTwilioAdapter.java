package EstrategiasNotificacion.EstrategiaWhatsApp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import entidades.Contacto;

public class WhastAppTwilioAdapter implements WhatsAppAdapterInterface {

    private static final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
    private static final String SENDER_PHONE_NUMBER = System.getenv("TWILIO_WHATSAPP_PHONE_NUMBER");
    private static final String PREFIJO_WHATSAPP = "whatsapp:";
    private static final String PREFIJO_WHASTAPP_ARGENTINA = "+549";

    @Override
    public void notificarViaWhatsApp(String mensaje, Contacto contacto) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                new com.twilio.type.PhoneNumber(PREFIJO_WHATSAPP + PREFIJO_WHASTAPP_ARGENTINA + contacto.getNumeroDeTelefono()),
                new com.twilio.type.PhoneNumber(PREFIJO_WHATSAPP + SENDER_PHONE_NUMBER),
                mensaje)
                .create();
    }
}
