package domain.entities.Mascotas;

public enum TipoMascota {
    GATO{
        String getName(){
            return this.name();
        }
    }
    ,
    PERRO{
        String getName(){
            return this.name();
        }
    }
}
