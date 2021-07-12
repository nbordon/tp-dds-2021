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

    public void notificarMascotaEnconrtadaSinChapita() {
        //TODO notificar cuando encontre a mi mascota
    }
}
