package entidades.QR;

import com.google.zxing.WriterException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class QR {
    static public void main(String[] args) {
        try {
            Ventana ventana = new Ventana();

            ventana.setVisible(true);

        } catch (WriterException ex) {
            Logger.getLogger(QR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

