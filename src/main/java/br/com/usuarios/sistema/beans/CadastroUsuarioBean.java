/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Usuario;
import br.com.usuarios.sistema.dao.UsuarioDao;
import br.com.usuarios.utilitarios.ChamadaPaginaBean;
import br.com.usuarios.utilitarios.Messages;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.mindrot.jbcrypt.BCrypt;

@ManagedBean
@SessionScoped
public class CadastroUsuarioBean implements Serializable {

    private Usuario usuario;
    private List<Usuario> listaUsuarios;

    public CadastroUsuarioBean() {
        usuario = new Usuario();
        listaUsuarios = new ArrayList();
        loadListaUsuario();
    }

    public void destroy() {
        ChamadaPaginaBean.pagina("cadastroUsuario");
    }

    public void save() throws NoSuchAlgorithmException {

        DAO dao = new DAO(Usuario.class);

        if (usuario.getLogin().length() <= 0) {
            Messages.error("Campo login obrigatório!");
            return;
        }

        Usuario user = new Usuario(null, usuario.getNome(), usuario.getLogin(), BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));

        if (user.getId() == null) {

            if (new UsuarioDao().buscaPorUsuarioLogin(user.getLogin()) != null) {
                Messages.error("Usuário com este login já existe!");
                return;
            }

            if (!dao.save(user)) {
                Messages.error("Erro ao salvar!");
                return;
            } else {
                Messages.info("Salvo com sucesso!!");

            }

        } else {
            if (!dao.atualiza(user)) {
                Messages.error("Erro ao atualizar!");
                return;
            } else {
                Messages.info("Atualizado com sucesso!!");
            }

        }

        loadListaUsuario();
    }

    public void delete(Usuario usuario) {

        DAO dao = new DAO(Usuario.class);
        if (!dao.delete(usuario)) {
            Messages.error("Erro ao remover!");
        } else {
            Messages.info("Removido com sucesso!!");
        }
        loadListaUsuario();
    }

    public void editar(Usuario usuario) {
        usuario.setSenha("");
        this.usuario = usuario;
    }

    public void deletar(Usuario usuario) {

        if (!new DAO(Usuario.class).delete(usuario)) {
            Messages.error("Erro ao deletar!");
        } else {
            Messages.info("Deletado com sucesso!");
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
