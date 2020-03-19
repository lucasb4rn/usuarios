/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Fisica;
import br.com.usuarios.sistema.OrdemServico;
import br.com.usuarios.sistema.OrdemStatus;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.sistema.Servico;
import br.com.usuarios.sistema.dao.OrdemServicoDao;
import br.com.usuarios.sistema.dao.ProdutoDao;
import br.com.usuarios.sistema.dao.ServicoDao;
import br.com.usuarios.utilitarios.ChamadaPaginaBean;
import br.com.usuarios.utilitarios.Messages;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class OrdemServicoBean implements Serializable {

    private OrdemServico ordemServico;
    private String statusFiltro;
    private List<OrdemStatus> listaOrdemStatus;
    private Produto produtoPesquisa;
    private Servico servicoPesquisa;
    private List<OrdemServico> listaOrdemServicoPesquisa;

    private double valor;
    private String dataCriacao;
    private boolean valorCheck;
    private boolean dataCheck;

    public OrdemServicoBean() {

        listaOrdemStatus = new ArrayList();
        produtoPesquisa = null;
        servicoPesquisa = null;
        ordemServico = new OrdemServico();
        listaOrdemServicoPesquisa = new ArrayList();
        statusFiltro = "Todos";

    }

    public void reset() {
    }

    public void save() {

        DAO dao = new DAO(OrdemServico.class);

        if (!dao.save(ordemServico)) {
            Messages.error("Erro ao salvar!!");
            return;
        } else {
            Messages.info("Salvo com Sucesso!");
            ordemServico = new OrdemServico();
            produtoPesquisa = new Produto();
            return;
        }

    }

    public void destroy() {

    }

    public List<OrdemStatus> getListaOrdemStatus() {
        listaOrdemStatus = Arrays.asList(OrdemStatus.values());
        return listaOrdemStatus;
    }

    public void removeProduto(Produto produto) {
        ordemServico.getListaProdutos().remove(produto);
    }

    public void removeServico(Servico servico) {
        ordemServico.getListaServicos().remove(servico);
    }

    public void addGridServico() {

        if (servicoPesquisa == null) {
            Messages.error("Escolher um serviço!!");
            return;
        }

        if (servicoPesquisa.getId() == null) {
            Messages.error("Escolher um serviço!");
            return;
        }

        ordemServico.getListaServicosPesquisa().add(servicoPesquisa);
        servicoPesquisa = new Servico();

    }

    public void addGrid() {

        if (produtoPesquisa == null) {
            Messages.error("Escolher um produto!!");
            return;
        }

        if (produtoPesquisa.getId() == null) {
            Messages.error("Escolher um produto!!");
            return;
        }

        ordemServico.getListaProdutos().add(produtoPesquisa);
        produtoPesquisa = new Produto();

    }

    public String redireciona(OrdemServico o) {
        Sessions.put("ordemServicoPesquisa", o);
        return ChamadaPaginaBean.urlRetorno();
    }

    public List<Produto> pesquisaProdutosPorDescricao(String caixaPesquisa) {

        List<Produto> listaProdutosPesquisados = new ArrayList();
        if (caixaPesquisa.length() >= 1) {
            caixaPesquisa = caixaPesquisa.trim();
            listaProdutosPesquisados = new ProdutoDao().pesquisaProdutoDescricao(caixaPesquisa);
        }
        return listaProdutosPesquisados;

    }

    public List<Servico> pesquisaServicoPorDescricao(String caixaPesquisa) {

        List<Servico> listaServicosPesquisados = new ArrayList();
        if (caixaPesquisa.length() >= 1) {
            caixaPesquisa = caixaPesquisa.trim();
            listaServicosPesquisados = new ServicoDao().pesquisaServicoDescricao(caixaPesquisa);
        }
        return listaServicosPesquisados;

    }

    public void loadListaOrdemServicoPesquisa() {
        listaOrdemServicoPesquisa.clear();
        listaOrdemServicoPesquisa = new OrdemServicoDao().pesquisaOrdemServicoFiltro(statusFiltro, dataCheck, dataCriacao, valorCheck, valor);
    }

    public OrdemServico getOrdemServico() {

        if (Sessions.exists("fisicaPesquisa")) {
            ordemServico.setFisica((Fisica) Sessions.getObject("fisicaPesquisa", true));
        }

        if (Sessions.exists("ordemServicoPesquisa")) {
            ordemServico = (OrdemServico) Sessions.getObject("ordemServicoPesquisa", true);
        }

        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public Produto getProdutoPesquisa() {
        return produtoPesquisa;
    }

    public void setProdutoPesquisa(Produto produtoPesquisa) {
        this.produtoPesquisa = produtoPesquisa;
    }

    public List<OrdemServico> getListaOrdemServicoPesquisa() {
        return listaOrdemServicoPesquisa;
    }

    public void setListaOrdemServicoPesquisa(List<OrdemServico> listaOrdemServicoPesquisa) {
        this.listaOrdemServicoPesquisa = listaOrdemServicoPesquisa;
    }

    public String getStatusFiltro() {
        return statusFiltro;
    }

    public void setStatusFiltro(String statusFiltro) {
        this.statusFiltro = statusFiltro;
    }

    public boolean isValorCheck() {
        return valorCheck;
    }

    public void setValorCheck(boolean valorCheck) {
        this.valorCheck = valorCheck;
    }

    public boolean isDataCheck() {
        return dataCheck;
    }

    public void setDataCheck(boolean dataCheck) {
        this.dataCheck = dataCheck;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Servico getServicoPesquisa() {
        return servicoPesquisa;
    }

    public void setServicoPesquisa(Servico servicoPesquisa) {
        this.servicoPesquisa = servicoPesquisa;
    }

}
