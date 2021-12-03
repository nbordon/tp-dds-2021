package domain.repositories;

import domain.entities.Usuario;
import domain.repositories.daos.DAO;
import exception.VerificadorException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioUsuario extends Repositorio<Usuario>{

    public RepositorioUsuario(DAO<Usuario> dao) {
        super(dao);
    }

    public Boolean existe(String nombreUsuario) throws VerificadorException {
        return buscarUsuario(nombreUsuario) != null;
    }

    public Usuario buscarUsuario(String nombreUsuario) throws VerificadorException {
        return this.dao.buscar(condicionUsuarioYPassword(nombreUsuario));
    }

    private BusquedaCondicional condicionUsuarioYPassword(String nombreUsuario) throws VerificadorException {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Usuario> usuarioQuery = criteriaBuilder.createQuery(Usuario.class);

        Root<Usuario> condicionRaiz = usuarioQuery.from(Usuario.class);

        Predicate condicionNombreUsuario = criteriaBuilder.equal(condicionRaiz.get("nombreUsuario"),nombreUsuario);

        Predicate condicionExisteUsuario = criteriaBuilder.and(condicionNombreUsuario);

        usuarioQuery.where(condicionExisteUsuario);

        return new BusquedaCondicional(null, usuarioQuery);
    }
}