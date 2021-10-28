package domain.entities.Mascotas;

import domain.entities.Organizacion.Organizacion;
import domain.entities.publicaciones.EstadoPublicacion;
import domain.entities.publicaciones.PublicacionMascotaEncontradaSinChapita;
import utils.localizador.LocalizadorDeOrganizacion;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "mascota_encontrada_sin_chapita")
public class MascotaEncontradaSinChapita extends MascotaEncontrada {
    @Transient
    private static final double RADIO_MINIMO = 5.0;

    public MascotaEncontradaSinChapita(){

    }
    public void notificar(String mensaje) {}
    @Override
    public void notificar() {
        LocalizadorDeOrganizacion localizadorDeOrganizacion = new LocalizadorDeOrganizacion();

        PublicacionMascotaEncontradaSinChapita publicacion = new PublicacionMascotaEncontradaSinChapita();
        publicacion.setMascotaEncontradaSinChapita(this);

        Organizacion organizacionDePublicacion;
        organizacionDePublicacion = localizadorDeOrganizacion.obtenerOrganizacionMasCercana(super.getUbicacion(),RADIO_MINIMO);
        publicacion.setOrganizacion(organizacionDePublicacion);
        publicacion.setEstado(EstadoPublicacion.PENDIENTE);
        organizacionDePublicacion.getContenedorPublicaciones().addPublicacionMascotaEncontradaSinChapita(publicacion);
    }
}