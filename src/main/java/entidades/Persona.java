package entidades;

import entidades.Mascotas.EstadoMascota;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Organizacion;
import exception.MascotaNoPerteneceAlUsuarioException;

import java.util.List;
import java.util.Optional;

public class Persona extends Usuario {

    private InformacionPersonal informacionPersonal;
    private List<Mascota> mascotas;
    private Organizacion organizacion;

    public Persona(String email) {
        super(email);
    }

    public InformacionPersonal getInformacionPersonal() {
        return informacionPersonal;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public void registrarMascota(Mascota mascota) {
        mascotas.add(mascota);
    }

    public void actualizarEstadoMascota(Mascota mascota, EstadoMascota estado) throws Exception {
        Optional<Mascota> mascotaParaActualizar = mascotas.stream()
                .filter(mascotaEnLista -> mascotaEnLista.getNombre().equals(mascota.getNombre()))
                .findFirst();

        if (!mascotaParaActualizar.isPresent()) {
            throw new MascotaNoPerteneceAlUsuarioException();
        } else {
            mascotaParaActualizar.get().setEstado(estado);
        }
    }
}
