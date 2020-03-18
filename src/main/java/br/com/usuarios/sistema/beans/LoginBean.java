/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.sistema.Usuario;
import br.com.usuarios.sistema.dao.UsuarioDao;
import br.com.usuarios.utilitarios.Messages;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

    private Usuario usuario;

    public LoginBean() {
        usuario = new Usuario();
    }

    public String efetuarLogin() {

        Usuario usuarioLogado;

        usuarioLogado = new UsuarioDao().buscaPorUsuario(usuario.getLogin());

        if (usuarioLogado == null) {
            Messages.error("Usuário não existe!");
            return "login";
        }

        boolean checkpw = BCrypt.checkpw(usuario.getSenha(), usuarioLogado.getSenha());

        if (checkpw == false) {
            Messages.error("Senha incorreta");
            return "login";
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("usuarioLogado", usuarioLogado);
        return "home.xhtml?faces-redirect=true";

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
