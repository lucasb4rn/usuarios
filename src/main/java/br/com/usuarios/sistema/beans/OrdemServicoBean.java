/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.sistema.Fisica;
import br.com.usuarios.sistema.OrdemServico;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class OrdemServicoBean implements Serializable {

    private List<Produto> listaProdutos;
    private OrdemServico ordemServico;
    private Fisica fisica;
    private String status;

    public OrdemServicoBean() {
        ordemServico = new OrdemServico();
        listaProdutos = new ArrayList();
        fisica = new Fisica();
        status = "aFazer";
    }

    public void save() {
    }

    public List<Produto> getListaProdutos() {
        if (Sessions.exists("produtoPesquisa")) {
            listaProdutos.add((Produto) Sessions.getObject("produtoPesquisa", true));
            Sessions.remove("produtoPesquisa");
        }
        return listaProdutos;
    }

    public void setListaProdutos(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public Fisica getFisica() {
        if (Sessions.exists("fisicaPesquisa")) {
            fisica = (Fisica) Sessions.getObject("fisicaPesquisa", true);
        }
        return fisica;
    }

    public void setFisica(Fisica fisica) {
        this.fisica = fisica;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

}
