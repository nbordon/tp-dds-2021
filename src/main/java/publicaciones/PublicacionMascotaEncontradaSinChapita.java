package publicaciones;

import entidades.Mascotas.MascotaEncontradaSinChapita;

public class PublicacionMascotaEncontradaSinChapita extends Publicacion {
    private MascotaEncontradaSinChapita mascotaEncontradaSinChapita;

    public PublicacionMascotaEncontradaSinChapita(){
        super.setEstado(EstadoPublicacion.PENDIENTE);
    }

    public MascotaEncontradaSinChapita getMascotaEncontradaSinChapita() {
        return mascotaEncontradaSinChapita;
    }

    public void setMascotaEncontradaSinChapita(MascotaEncontradaSinChapita mascotaEncontradaSinChapita) {
        this.mascotaEncontradaSinChapita = mascotaEncontradaSinChapita;
    }

    @Override
    public void notificar() {
        //TODO notificar cuando encontre a mi mascota
    }
}
