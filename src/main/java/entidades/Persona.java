package entidades;

import java.util.List;

public class Persona {

    private InformacionPersonal informacionPersonal;
    private List<Mascota> mascotas;


    public void agregarMascota(Mascota mascota){

        mascotas.add(mascota);
    }
//TODO generar QR

    public void registrarMascota(Mascota mascota) {

        this.agregarMascota(mascota);

        }


    public void actualizarEstadoMascota(Mascota mascota,EstadoMascota estado){
        mascota.setEstado(estado);
    }


}
