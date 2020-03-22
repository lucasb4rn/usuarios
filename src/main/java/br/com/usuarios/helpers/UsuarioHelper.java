/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.helpers;
import br.com.usuarios.sistema.Usuario;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



@ManagedBean
@ViewScoped
public class UsuarioHelper implements Serializable {

    public static Usuario SESSION() {
        return (Usuario) Sessions.getObject("usuarioLogado");
    }

    public Usuario getSession() {
        return (Usuario) Sessions.getObject("usuarioLogado");
    }

    public String getPrimeiroNome() {
        Usuario u = (Usuario) Sessions.getObject("usuarioLogado");
        if (u != null) {
            String nm[] = u.getNome().split(" ");
            if (nm.length > 0) {
                return nm[0];
            }
        }
        return "";
    }

}
