/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.sistema.Servico;
import br.com.usuarios.sistema.dao.ServicoDao;
import br.com.usuarios.utilitarios.ChamadaPaginaBean;
import br.com.usuarios.utilitarios.Messages;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ServicoBean implements Serializable {

    private Servico servico;
    private String caixaPesquisa;
    private List<Servico> listaServicoPesquisa;
    private String filter;

    public ServicoBean() {
        listaServicoPesquisa = new ArrayList();
        filter = "";
        servico = new Servico();
        caixaPesquisa = "";
    }

    public String destroy() {
        return "cadastro_servico?faces-redirect=true";
    }

    public void save() {

        DAO dao = new DAO(Produto.class);

        if (!dao.save(servico)) {
            Messages.error("Erro ao salvar!");
        } else {
            Messages.info("Salvo com sucesso!!");
            servico = new Servico();
        }

    }

    public void findServicoFiltro() {
        listaServicoPesquisa.clear();
        listaServicoPesquisa = new ServicoDao().pesquisaServicoFiltro(filter, caixaPesquisa);
    }

    public String redireciona(Servico s) {
        Sessions.put("servicoPesquisa", s);
        return ChamadaPaginaBean.urlRetorno();
    }

    public Servico getServico() {
        if (Sessions.exists("servicoPesquisa")) {
            servico = (Servico) Sessions.getObject("servicoPesquisa", true);
        }
        return servico;
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

    public List<Servico> getListaServicoPesquisa() {
        return listaServicoPesquisa;
    }

    public void setListaServicoPesquisa(List<Servico> listaServicoPesquisa) {
        this.listaServicoPesquisa = listaServicoPesquisa;
    }

}
