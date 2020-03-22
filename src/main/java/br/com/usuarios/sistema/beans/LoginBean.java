/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.sistema.Usuario;
import br.com.usuarios.sistema.dao.UsuarioDao;
import br.com.usuarios.utilitarios.Messages;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private Usuario usuario;

    public LoginBean() {
        usuario = new Usuario();
    }

    public String efetuarLogin() {

        Usuario usuarioLogado;

        usuarioLogado = new UsuarioDao().buscaPorUsuarioLogin(usuario.getLogin());

        if (usuarioLogado == null) {
            Messages.error("Usuário não existe!");
            return null;
        }

        boolean checkpw = BCrypt.checkpw(usuario.getSenha(), usuarioLogado.getSenha());

        if (checkpw == false) {
            Messages.error("Senha incorreta.");
            return null;
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("usuarioLogado", usuarioLogado);
        return "home?faces-redirect=true";

    }

    public String logout() {
        Sessions.remove("usuarioLogado");
        return "login.xhtml?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
