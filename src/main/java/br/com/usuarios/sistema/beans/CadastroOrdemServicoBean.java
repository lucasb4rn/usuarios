/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuario.filtros.FiltroOrdemServico;
import br.com.usuarios.DB.DAO;
import br.com.usuarios.helpers.UsuarioHelper;
import br.com.usuarios.sistema.Fisica;
import br.com.usuarios.sistema.OrdemServico;
import br.com.usuarios.sistema.OrdemServicoLog;
import br.com.usuarios.sistema.OrdemStatus;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.sistema.ProdutoFaturaItem;
import br.com.usuarios.sistema.Servico;
import br.com.usuarios.sistema.ServicoFaturaItem;
import br.com.usuarios.sistema.dao.OrdemServicoDao;
import br.com.usuarios.sistema.dao.ProdutoDao;
import br.com.usuarios.sistema.dao.ServicoDao;
import br.com.usuarios.utilitarios.ChamadaPaginaBean;
import br.com.usuarios.utilitarios.Currency;
import br.com.usuarios.utilitarios.Dates;
import br.com.usuarios.utilitarios.Messages;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CadastroOrdemServicoBean implements Serializable {

    private OrdemServico ordemServico;
    private List<OrdemStatus> listaOrdemStatus;
    private Produto produtoPesquisa;
    private Servico servicoPesquisa;
    private List<OrdemServico> listaOrdemServicoPesquisa;
    private Double totalServico;
    private Double totalProduto;
    private Double totalGeral;

    private Produto ItemProdutoPojo;

    private FiltroOrdemServico filtroOrdemServico;
    private ProdutoFaturaItem produtoFaturaItemEditar;
    private ServicoFaturaItem servicoFaturaItemEditar;

    public CadastroOrdemServicoBean() {

        this.ItemProdutoPojo = new Produto();
        filtroOrdemServico = new FiltroOrdemServico();
        totalServico = new Double(0);
        totalProduto = new Double(0);
        totalGeral = new Double(0);
        produtoFaturaItemEditar = new ProdutoFaturaItem();
        servicoFaturaItemEditar = new ServicoFaturaItem();
        listaOrdemStatus = new ArrayList();
        produtoPesquisa = new Produto();
        servicoPesquisa = new Servico();
        ordemServico = new OrdemServico();
        listaOrdemServicoPesquisa = new ArrayList();

    }

    public void clear(Produto produto) {
        produto = null;
    }

    public void save() {

        DAO dao = new DAO(OrdemServico.class);

        if (ordemServico.getFisica().getId() == null) {
            Messages.error("Necessário escolher um cliente!!");
            return;
        }

        if (ordemServico.getListaServicoFaturaItem().isEmpty()) {
            Messages.error("Para atualizar uma ordem de serviço é necessário adicionar pelo menos um serviço!!");
            return;
        }

        if (ordemServico.getId() != null) {

            OrdemServico servicoAntes = (OrdemServico) dao.buscaPorId(ordemServico.getId());

            if (!dao.atualiza(ordemServico)) {
                Messages.error("erro ao atualizar!!");
                return;

            } else {

                //salva Log
                if (!servicoAntes.getStatus().equals(ordemServico.getStatus())) {
                    String alteracao = "Antes: " + servicoAntes.getStatus() + " -> " + ordemServico.getStatus();
                    OrdemServicoLog logServico = new OrdemServicoLog(null, ordemServico, Dates.dataHoje(), UsuarioHelper.SESSION(), alteracao);
                    DAO daoLog = new DAO(OrdemServicoLog.class);
                    daoLog.save(logServico);

                }

                Messages.info("Atualizado com Sucesso!");

                ordemServico = new OrdemServico();
                produtoPesquisa = new Produto();
                servicoPesquisa = new Servico();
                return;
            }

        } else {

            if (ordemServico.getListaServicoFaturaItem().isEmpty()) {
                Messages.info("Para incluir uma ordem de serviço é necessário adicionar pelo menos um serviço!!");
                return;
            }

            if (!dao.save(ordemServico)) {
                Messages.error("Erro ao salvar!!");
                return;

            } else {
                Messages.info("Salvo com Sucesso!");
                ordemServico = new OrdemServico();
                produtoPesquisa = new Produto();
                servicoPesquisa = new Servico();
                return;
            }

        }

    }

    public void destroy() {
        ChamadaPaginaBean.pagina("cadastroOrdemServico");
    }

    public void deletar() {

        DAO dao = new DAO(OrdemServico.class);

        if (ordemServico.getId() == null) {
            Messages.error("Nenhum ordem selecionada para exclusão!");
        }

        if (!dao.delete(ordemServico)) {
            Messages.error("Erro ao deletar Ordem de Servico!");
        } else {
            Messages.info("Ordem de Serviço deletado com sucesso!!");
            ordemServico = new OrdemServico();
        }

    }

    public List<OrdemStatus> getListaOrdemStatus() {
        listaOrdemStatus = Arrays.asList(OrdemStatus.values());
        return listaOrdemStatus;
    }

    public void removeProduto(ProdutoFaturaItem produtoFaturaItem) {
        ordemServico.getListaProdutoFaturaItem().remove(produtoFaturaItem);
    }

    public void removeServico(ServicoFaturaItem servicoFaturaItem) {
        ordemServico.getListaServicoFaturaItem().remove(servicoFaturaItem);
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

        ServicoFaturaItem servicoFaturaItem = new ServicoFaturaItem();
        servicoFaturaItem.setServico(servicoPesquisa);
        servicoFaturaItem.setValor(servicoPesquisa.getValor());
        ordemServico.getListaServicosPesquisa().add(servicoFaturaItem);
        servicoPesquisa = new Servico();

    }

    public void calculaTotalServico() {
        totalServico = new Double(0);
        if (!ordemServico.getListaServicoFaturaItem().isEmpty()) {
            for (ServicoFaturaItem servicoFaturaItem : ordemServico.getListaServicoFaturaItem()) {
                totalServico += (servicoFaturaItem.getValor() * servicoFaturaItem.getQuantidade());
            }
        }
        calculaTotalGeral();
    }

    public void calculaTotalProduto() {
        totalProduto = new Double(0);
        if (!ordemServico.getListaProdutoFaturaItem().isEmpty()) {
            for (ProdutoFaturaItem faturaProdutoItem : ordemServico.getListaProdutoFaturaItem()) {
                totalProduto += faturaProdutoItem.getValor() * faturaProdutoItem.getQuantidade();
            }
        }

        calculaTotalGeral();
    }

    public void calculaTotalGeral() {
        totalGeral = new Double(0);
        totalGeral = totalServico + totalProduto;
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

        ProdutoFaturaItem produtoFaturaItemX = new ProdutoFaturaItem();
        produtoFaturaItemX.setProduto(produtoPesquisa);
        produtoFaturaItemX.setValor(produtoPesquisa.getPrecoVenda());

        ordemServico.getListaProdutoFaturaItem().add(produtoFaturaItemX);
        produtoPesquisa = new Produto();
        produtoFaturaItemEditar = new ProdutoFaturaItem();

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

    public void editarProduto(ProdutoFaturaItem produtoFaturaItem) {

        if (produtoFaturaItem.getValor() <= 0) {
            Messages.error("Valor não pode ser negativo ou menor que zero, verifique!");
            return;
        }

        if (produtoFaturaItem.getQuantidade() <= 0) {
            Messages.error("Quantidade não pode ser negativa ou menor que zero, verifique!");
            return;
        }

        for (ProdutoFaturaItem itemListaProduto : ordemServico.getListaProdutoFaturaItem()) {

            if (itemListaProduto.equals(produtoFaturaItem)) {
                itemListaProduto.setValor(produtoFaturaItem.getValor());
                itemListaProduto.setQuantidade(produtoFaturaItem.getQuantidade());
            }

        }

        //linha para arrumar bug do autocomplete que retorna ultimo valor armazenado em itemValue do compenente quando renderiza a pagina. 
        produtoPesquisa = new Produto();
        servicoPesquisa = new Servico();
        produtoFaturaItemEditar = new ProdutoFaturaItem();
    }

    public void editarServico(ServicoFaturaItem servicoFaturaItem) {

        if (servicoFaturaItem.getValor() <= 0) {
            Messages.error("Valor não pode ser negativo ou menor que zero, verifique!");
            return;
        }

        if (servicoFaturaItem.getQuantidade() <= 0) {
            Messages.error("Quantidade não pode ser negativa ou menor que zero, verifique!");
            return;
        }

        for (ServicoFaturaItem itemListaServico : ordemServico.getListaServicoFaturaItem()) {

            if (itemListaServico.equals(servicoFaturaItem)) {
                itemListaServico.setValor(servicoFaturaItem.getValor());
                itemListaServico.setQuantidade(servicoFaturaItem.getQuantidade());
            }
        }

        //linha para arrumar bug do autocomplete que retorna ultimo valor armazenado em itemValue do compenente quando renderiza a pagina. 
        servicoPesquisa = new Servico();
        produtoPesquisa = new Produto();
        produtoFaturaItemEditar = new ProdutoFaturaItem();
    }

    public void loadListaOrdemServicoPesquisa() {
        listaOrdemServicoPesquisa.clear();
        listaOrdemServicoPesquisa = new OrdemServicoDao().pesquisaOrdemServicoFiltro(filtroOrdemServico);
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

    public Produto getProdutoPesquisa() {
        return produtoPesquisa;
    }

    public void setProdutoPesquisa(Produto produtoPesquisa) {
        this.produtoPesquisa = produtoPesquisa;
    }

    public Servico getServicoPesquisa() {
        return servicoPesquisa;
    }

    public void setServicoPesquisa(Servico servicoPesquisa) {
        this.servicoPesquisa = servicoPesquisa;
    }

    public List<OrdemServico> getListaOrdemServicoPesquisa() {
        return listaOrdemServicoPesquisa;
    }

    public void setListaOrdemServicoPesquisa(List<OrdemServico> listaOrdemServicoPesquisa) {
        this.listaOrdemServicoPesquisa = listaOrdemServicoPesquisa;
    }

    public Double getTotalServico() {
        return totalServico;
    }

    public void setTotalServico(Double totalServico) {
        this.totalServico = totalServico;
    }

    public Double getTotalProduto() {
        return totalProduto;
    }

    public void setTotalProduto(Double totalProduto) {
        this.totalProduto = totalProduto;
    }

    public Double getTotalGeral() {
        return Currency.converteDoubleR$Double(totalGeral);
    }

    public void setTotalGeral(Double totalGeral) {
        this.totalGeral = totalGeral;
    }

    public ProdutoFaturaItem getProdutoFaturaItemEditar() {
        return produtoFaturaItemEditar;
    }

    public void setProdutoFaturaItemEditar(ProdutoFaturaItem produtoFaturaItemEditar) {
        this.produtoFaturaItemEditar = produtoFaturaItemEditar;
    }

    public ServicoFaturaItem getServicoFaturaItemEditar() {
        return servicoFaturaItemEditar;
    }

    public void setServicoFaturaItemEditar(ServicoFaturaItem servicoFaturaItemEditar) {
        this.servicoFaturaItemEditar = servicoFaturaItemEditar;
    }

    public FiltroOrdemServico getFiltroOrdemServico() {
        return filtroOrdemServico;
    }

    public void setFiltroOrdemServico(FiltroOrdemServico filtroOrdemServico) {
        this.filtroOrdemServico = filtroOrdemServico;
    }

    public Produto getItemProdutoPojo() {
        return ItemProdutoPojo;
    }

    public void setItemProdutoPojo(Produto ItemProdutoPojo) {
        this.ItemProdutoPojo = ItemProdutoPojo;
    }

}
