package domain.entities.Mascotas;

import domain.entities.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "foto")
@Getter
@Setter
public class Foto extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "contenido_b64", columnDefinition = "NVARCHAR(1024)")
    private String contenidoBase64;

    public Foto(){}
}
