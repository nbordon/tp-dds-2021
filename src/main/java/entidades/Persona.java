package entidades;

import entidades.Mascotas.EstadoMascota;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Organizacion;

import java.util.List;

public class Persona extends  Usuario{

    private InformacionPersonal informacionPersonal;
    private List<Mascota> mascotas;
    private Organizacion organizacion;

    public void agregarMascota(Mascota mascota){

        mascotas.add(mascota);
    }
    public InformacionPersonal getInformacionPersonal(){
        return informacionPersonal;
    }

    public void registrarMascota(Mascota mascota) {

        this.agregarMascota(mascota);

        }

    public void setInformacionPersonal(InformacionPersonal informacionPersonal) {
        this.informacionPersonal = informacionPersonal;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }


    public void actualizarEstadoMascota(Mascota mascota, EstadoMascota estado){
        mascota.setEstado(estado);
    }


}
