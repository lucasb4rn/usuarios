/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.sistema.dao.ProdutoDao;
import br.com.usuarios.utilitarios.ChamadaPaginaBean;
import br.com.usuarios.utilitarios.Messages;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CadastroProdutoBean implements Serializable {

    private Produto produto;
    private List<Produto> listaProdutoPesquisa;
    private String filter;
    private String caixaPesquisa;

    public CadastroProdutoBean() {
        listaProdutoPesquisa = new ArrayList();
        produto = new Produto();
        caixaPesquisa = "";
    }

    public String destroy() {
        Sessions.remove("produtoBean");
        return "cadastroProduto?faces-redirect=true";
    }

    public void save() {

        DAO dao = new DAO(Produto.class);

        //melhoria futura, criar validador
        if (produto.getDescricao().length() < 5) {
            Messages.error("Descrição precisa ter mais que 5 Caracteres!");
            return;
        }

        if (produto.getEAN().length() != 13) {
            Messages.error("EAN precisa ter 13 Caracteres GTIN 13");
            return;
        }

        if (produto.getPrecoCusto() < 0) {
            Messages.error("Preço de custo não pode ser negativo.");
            return;
        }

        if (produto.getPrecoVenda() <= 0) {
            Messages.error("Preço de venda não pode ser negativo");
            return;
        }

        if (produto.getId() != null) {

            if (!dao.atualiza(produto)) {
                Messages.error("Erro ao atualizar!");
                return;
            } else {
                Messages.info("Atualizado com sucesso!!");
                produto = new Produto();
            }

        } else {

            if (!dao.save(produto)) {
                Messages.error("Erro ao salvar!");
            } else {
                Messages.info("Salvo com sucesso!!");
                produto = new Produto();
            }
        }

    }

    public void deletar() {
        DAO dao = new DAO(Produto.class);
        if (produto.getId() != null) {
            if (!dao.delete(produto)) {
                Messages.error("Erro ao excluído produto!");
                return;
            } else {
                Messages.info("Produto excluído com sucesso!!");
                produto = new Produto();
                return;
            }
        } else {
            Messages.info("Necessário escolher um produto para excluir!");
        }
    }

    public void findProdutoFiltro() {
        listaProdutoPesquisa.clear();
        listaProdutoPesquisa = new ProdutoDao().pesquisaProdutoFiltro(filter, caixaPesquisa);
    }

    public String redireciona(Produto p) {
        Sessions.put("produtoPesquisa", p);
        return ChamadaPaginaBean.urlRetorno();
    }

    public List<Produto> getListaProdutoPesquisa() {
        return listaProdutoPesquisa;
    }

    public void setListaProdutoPesquisa(List<Produto> listaProdutoPesquisa) {
        this.listaProdutoPesquisa = listaProdutoPesquisa;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        if (Sessions.exists("produtoPesquisa")) {
            produto = (Produto) Sessions.getObject("produtoPesquisa", true);
        }
        return produto;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getCaixaPesquisa() {
        return caixaPesquisa;
    }

    public void setCaixaPesquisa(String caixaPesquisa) {
        this.caixaPesquisa = caixaPesquisa;
    }

}
