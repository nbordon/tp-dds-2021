package entidades;

import entidades.Mascotas.EstadoMascota;
import entidades.Mascotas.Mascota;

import java.util.List;

public class Persona extends  Usuario{

    private InformacionPersonal informacionPersonal;
    private List<Mascota> mascotas;

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void agregarMascota(Mascota mascota){

        mascotas.add(mascota);
    }
    public InformacionPersonal getInformacionPersonal(){
        return informacionPersonal;
    }

    public void registrarMascota(Mascota mascota) {

        this.agregarMascota(mascota);

        }


    public void actualizarEstadoMascota(Mascota mascota, EstadoMascota estado){
        mascota.setEstado(estado);
    }


}
