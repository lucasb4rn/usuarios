/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.dao;

import br.com.usuarios.DB.JPAUtil;
import br.com.usuarios.sistema.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

public class UsuarioDao {

    public List<Usuario> listaUsuarios() {
        try {
            Query qry = new JPAUtil().getEntityManager().createNativeQuery("select * from seg_usuario", Usuario.class);
            return qry.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public Usuario buscaPorUsuario(String login) {
        try {
            String queryString = "select * from seg_usuario where login = '" + login +"'";
            Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString, Usuario.class);
            return (Usuario) qry.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
