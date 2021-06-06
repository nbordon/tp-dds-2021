package entidades;


import com.google.zxing.WriterException;

import javax.swing.*;
import java.awt.image.BufferedImage;


public class Ventana extends JFrame {

    public Ventana() throws WriterException {
        GeneraQR generaQR = new GeneraQR();
        BufferedImage imagen = generaQR.crearQR("Mascota encontrada", 300, 300);
        ImageIcon icono = new ImageIcon(imagen);
        JLabel etiqueta = new JLabel("");

        etiqueta.setIcon(icono);

        this.setIconImage(imagen);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Chapita mascota");
        this.getContentPane().add(etiqueta);
        this.pack();
    }    }

