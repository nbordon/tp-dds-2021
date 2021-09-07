package entidades;

import entidades.Mascotas.EstadoMascota;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Organizacion;
import exception.MascotaNoPerteneceAlUsuarioException;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "persona")
public class Persona extends Usuario {
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "informacion_personal_id", referencedColumnName = "id")
    private InformacionPersonal informacionPersonal;
    @OneToMany(mappedBy = "duenio")
    private List<Mascota> mascotas ;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;

    public Persona(String email) {
        super(email);
        mascotas = new ArrayList<>();
    }

    public Persona() {

    }

    public InformacionPersonal getInformacionPersonal() {
        return informacionPersonal;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
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

    public void setInformacionPersonal(InformacionPersonal informacionPersonal) {
        this.informacionPersonal = informacionPersonal;
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
