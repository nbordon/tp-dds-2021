package domain.entities.Mascotas;

public enum EstadoMascota {
    NO_PERDIDA{
        String getName(){
            return this.name();
        }
    }
    , PERDIDA{
        String getName(){
            return this.name();
        }
    }
    , ENCONTRADA{
        String getName(){
            return this.name();
        }
    }
}
