/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Usuario;
import br.com.usuarios.sistema.dao.UsuarioDao;
import br.com.usuarios.utilitarios.Messages;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.mindrot.jbcrypt.BCrypt;

@ManagedBean
@RequestScoped
public class UsuarioBean {

    private Usuario usuario;
    private List<Usuario> listaUsuarios;

    public UsuarioBean() {
        usuario = new Usuario();
        listaUsuarios = new ArrayList();
        loadListaUsuario();
    }

    public void save() throws NoSuchAlgorithmException {

        DAO dao = new DAO(Usuario.class);
        
        Usuario user = new Usuario(null, usuario.getNome(), usuario.getLogin(), BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
        
        if (!dao.save(user)) {
            Messages.error("Erro ao salvar!");
        } else {
            Messages.info("Salvo com sucesso!!");
        }
    }

    public final void loadListaUsuario() {
        listaUsuarios.clear();
        listaUsuarios = new UsuarioDao().listaUsuarios();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

}
