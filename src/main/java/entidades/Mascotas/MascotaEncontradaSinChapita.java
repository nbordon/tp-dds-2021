package entidades.Mascotas;

import entidades.Organizacion.Organizacion;
import publicaciones.PublicacionMascotaEncontradaSinChapita;
import utils.localizador.LocalizadorDeOrganizacion;

public class MascotaEncontradaSinChapita extends MascotaEncontrada {
    private static final double RADIO_MINIMO = 5.0;
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
    }

    public void addOrganizacionALocalizador(Organizacion organizacion){
        this.localizadorDeOrganizacion.addOrganizacion(organizacion);
    }
}