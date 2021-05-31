package entidades;

import java.time.LocalDate;
import java.util.List;

public class InformacionPersonal {

    private String nombre;
    private String apellido;
    private TipoDeDocumento tipoDoc;
    private int nroDocumento;
    private LocalDate fechaNacimiento;
    private String email;
    private List <EstrategiaDeNotificacion>formaComunicacion;
    private List<Contacto> contactos;


    public InformacionPersonal(String nombre,String apellido,TipoDeDocumento tipoDoc,int nroDocumento,LocalDate fechaNacimiento,String email,List <EstrategiaDeNotificacion>formaComunicacion,List<Contacto> contactos){
    this.nombre= nombre;
    this.apellido=apellido;
    this.tipoDoc=tipoDoc;
    this.nroDocumento= nroDocumento;
    this.fechaNacimiento=fechaNacimiento;
    this.email=email;
    this.formaComunicacion=formaComunicacion;
    this.contactos=contactos;

    }

}
