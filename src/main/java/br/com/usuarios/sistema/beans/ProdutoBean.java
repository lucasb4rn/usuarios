/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Fisica;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.utilitarios.Messages;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ProdutoBean {

    private Produto produto;

    public ProdutoBean() {
        produto = new Produto();
    }

    public void save() {

        DAO dao = new DAO(Produto.class);

        if (!dao.save(produto)) {
            Messages.error("Erro ao salvar!");
        } else {
            Messages.info("Salvo com sucesso!!");
        }

    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}
