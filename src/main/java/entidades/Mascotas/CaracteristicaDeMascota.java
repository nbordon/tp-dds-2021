package entidades.Mascotas;

public class CaracteristicaDeMascota {
    private String descripcion;
    private String valor;

    public CaracteristicaDeMascota() {
    }

    public CaracteristicaDeMascota(String descripcion, String valor) {
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
