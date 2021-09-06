package entidades.Mascotas;

import entidades.Organizacion.Organizacion;
import entidades.publicaciones.EstadoPublicacion;
import entidades.publicaciones.PublicacionMascotaEncontradaSinChapita;
import utils.localizador.LocalizadorDeOrganizacion;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "mascota_encontrada")
public class MascotaEncontradaSinChapita extends MascotaEncontrada {
    @Transient
    private static final double RADIO_MINIMO = 5.0;
    @Transient
    private LocalizadorDeOrganizacion localizadorDeOrganizacion;

    public MascotaEncontradaSinChapita(){
        this.localizadorDeOrganizacion = new LocalizadorDeOrganizacion();
    }

    @Override
    public void notificar() {
        PublicacionMascotaEncontradaSinChapita publicacion = new PublicacionMascotaEncontradaSinChapita();
        publicacion.setMascotaEncontradaSinChapita(this);

        Organizacion organizacionDePublicacion;
        organizacionDePublicacion = this.localizadorDeOrganizacion.obtenerOrganizacionMasCercana(super.getUbicacion(),RADIO_MINIMO);
        publicacion.setOrganizacion(organizacionDePublicacion);
        publicacion.setEstado(EstadoPublicacion.PENDIENTE);
        organizacionDePublicacion.getContenedorPublicaciones().addPublicacionMascotaEncontradaSinChapita(publicacion);
    }

    public void addOrganizacionALocalizador(Organizacion organizacion){
        this.localizadorDeOrganizacion.addOrganizacion(organizacion);
    }
}