package domain.repositories.factories;

import config.Config;
import domain.entities.Usuario;
import domain.repositories.RepositorioUsuario;
import domain.repositories.daos.DAO;
import domain.repositories.daos.DAOHibernate;

public class FactoryRepositorioUsuario {
    private static RepositorioUsuario respositorioUsuario;

    static {
        respositorioUsuario = null;
    }

    public static RepositorioUsuario get(){
        if (respositorioUsuario == null) {
            //if (Config.useDataBase){
                DAO<Usuario> dao = new DAOHibernate<>(Usuario.class);
                respositorioUsuario = new RepositorioUsuario(dao);
            //} else {
                //repo = new RepositorioDeUsuarios(new DAOMemoria<>(Data.getData(Usuario.class)));
            //}
        }
        return respositorioUsuario;
    }
}
