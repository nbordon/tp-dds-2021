package entidades;

import java.util.List;

public class Administrador extends  Usuario{

    private String nombre;
    private Organizacion organizacionPerteneciente;
    private List<UsuarioVoluntario> voluntariosDadosDeAlta;


public void aprobarVoluntario(UsuarioVoluntario voluntario){
if(organizacionPerteneciente.getUsuariosAprobados().contains(voluntario))
    voluntariosDadosDeAlta.add(voluntario)
    ;
}
    public void quitarVoluntario(UsuarioVoluntario voluntario){
voluntariosDadosDeAlta.remove(voluntario);
    }
}
