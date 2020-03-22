/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuario.filtros.FiltroSaidaServico;
import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Fisica;
import br.com.usuarios.sistema.OrdemServico;
import br.com.usuarios.sistema.OrdemStatus;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.sistema.ProdutoFaturaItem;
import br.com.usuarios.sistema.SaidaServico;
import br.com.usuarios.sistema.Servico;
import br.com.usuarios.sistema.ServicoFaturaItem;
import br.com.usuarios.sistema.dao.OrdemServicoDao;
import br.com.usuarios.sistema.dao.ProdutoDao;
import br.com.usuarios.sistema.dao.SaidaServicoDao;
import br.com.usuarios.sistema.dao.ServicoDao;
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
public class SaidaServicoBean implements Serializable {

    private SaidaServico saidaServico;
    private String statusFiltro;
    private Produto produtoPesquisa;
    private Servico servicoPesquisa;
    private List<SaidaServico> listaSaidaServicoPesquisa;
    private List<OrdemServico> listaOrdemServicoFeito;
    private double totalServico;
    private double totalProduto;
    private double totalGeral;

    private double valor;
    private String dataCriacao;
    private boolean valorCheck;
    private boolean dataCheck;

    private ProdutoFaturaItem produtoFaturaItemEditar;
    private ServicoFaturaItem servicoFaturaItemEditar;

    private FiltroSaidaServico filtroSaidaServico;

    public SaidaServicoBean() {

        filtroSaidaServico = new FiltroSaidaServico();

        totalServico = new Double(0);
        totalProduto = new Double(0);
        totalGeral = new Double(0);

        produtoFaturaItemEditar = new ProdutoFaturaItem();
        servicoFaturaItemEditar = new ServicoFaturaItem();

        listaOrdemServicoFeito = new ArrayList();
        saidaServico = new SaidaServico();
        produtoPesquisa = null;
        servicoPesquisa = null;
        listaSaidaServicoPesquisa = new ArrayList();
        loadListaOrdemServicoFeito();

    }

    public final void loadListaOrdemServicoFeito() {
        listaOrdemServicoFeito.clear();
        listaOrdemServicoFeito = new OrdemServicoDao().retornaOrdemServicoPorStatus(OrdemStatus.Feito);
    }

    public void importar(OrdemServico ordem) {
        saidaServico = new SaidaServico();
        saidaServico.setListaProdutoFaturaItem(ordem.getListaProdutoFaturaItem());
        saidaServico.setFisica(ordem.getFisica());
        saidaServico.setListaServicoFaturaItem(ordem.getListaServicoFaturaItem());
        calculaTotalServico();
        calculaTotalProduto();
    }

    public void carregaProduto(ProdutoFaturaItem produtoFaturaItem) {
        produtoFaturaItemEditar = produtoFaturaItem;
    }

    public void carregaServico(ServicoFaturaItem servicoFaturaItem) {
        servicoFaturaItemEditar = servicoFaturaItem;
    }

    public void editarProduto(ProdutoFaturaItem produtoFaturaItem) {
        for (ProdutoFaturaItem itemListaProduto : saidaServico.getListaProdutoFaturaItem()) {

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

    public void deletar() {

        DAO dao = new DAO(SaidaServico.class);
        if (saidaServico.getId() == null) {
            Messages.error("Nenhuma saída de serviços selecionada para exclusão!");
        }
        if (!dao.delete(saidaServico)) {
            Messages.error("Erro ao excluir saída de serviços!");
        } else {
            Messages.info("Saída de serviços excluida com sucesso!!");
            saidaServico = new SaidaServico();
        }
    }

    public void editarServico(ServicoFaturaItem servicoFaturaItem) {
        for (ServicoFaturaItem itemListaServico : saidaServico.getListaServicoFaturaItem()) {

            if (itemListaServico.equals(servicoFaturaItem)) {
                itemListaServico.setValor(servicoFaturaItem.getValor());
                itemListaServico.setQuantidade(servicoFaturaItem.getQuantidade());
            }

        }

        //linha para arrumar bug do autocomplete que retorna ultimo valor armazenado em itemValue do compenente quando renderiza a pagina.
        produtoPesquisa = new Produto();
        servicoPesquisa = new Servico();
        servicoFaturaItemEditar = new ServicoFaturaItem();

    }

    public void save() {

        DAO dao = new DAO(SaidaServico.class);

        if (saidaServico.getFisica().getId() == null) {
            Messages.error("Necessário selecionar um cliente!!");
            return;
        }

        if (saidaServico.getListaServicoFaturaItem().isEmpty()) {
            Messages.error("Necessário selecionar ao menos um servico!!");
            return;
        }

        if (saidaServico.getId() == null) {

            if (!dao.save(saidaServico)) {
                Messages.error("Erro ao salvar!!");
                return;
            } else {
                Messages.info("Salvo com Sucesso!");
                saidaServico = new SaidaServico();
                return;
            }
        } else {

            if (!dao.atualiza(saidaServico)) {
                Messages.error("Erro ao Atualizar!!");
                return;
            } else {
                Messages.info("Atualizado com Sucesso!");
                saidaServico = new SaidaServico();
                return;
            }

        }

    }

    public void destroy() {
        ChamadaPaginaBean.pagina("saidaServico");
    }

    public void calculaTotalServico() {
        totalServico = new Double(0);
        if (!saidaServico.getListaServicoFaturaItem().isEmpty()) {
            for (ServicoFaturaItem servicoFaturaItem : saidaServico.getListaServicoFaturaItem()) {
                totalServico += (servicoFaturaItem.getValor() * servicoFaturaItem.getQuantidade());
            }
        }
        calculaTotalGeral();
    }

    public void calculaTotalProduto() {
        totalProduto = new Double(0);
        if (!saidaServico.getListaProdutoFaturaItem().isEmpty()) {
            for (ProdutoFaturaItem produtoFaturaItem : saidaServico.getListaProdutoFaturaItem()) {
                totalProduto += (produtoFaturaItem.getValor() * produtoFaturaItem.getQuantidade());
            }
        }
        calculaTotalGeral();
    }

    public void calculaQuantidadeTotal() {
        int quantidadeTotal = 0;

        for (ProdutoFaturaItem itemFaturaProd : saidaServico.getListaProdutoFaturaItem()) {
            quantidadeTotal += itemFaturaProd.getQuantidade();
        }

        for (ServicoFaturaItem itemFaturaServ : saidaServico.getListaServicoFaturaItem()) {
            quantidadeTotal += itemFaturaServ.getQuantidade();
        }

        saidaServico.setQuantidadeTotal(quantidadeTotal);
    }

    public void calculaTotalGeral() {
        totalGeral = new Double(0);
        totalGeral = totalServico + totalProduto;
        saidaServico.setValorTotal(totalGeral);
        calculaQuantidadeTotal();
    }

    public void removeProduto(ProdutoFaturaItem produtoFaturaItem) {
        saidaServico.getListaProdutoFaturaItem().remove(produtoFaturaItem);
    }

    public void removeServico(ServicoFaturaItem servicoFaturaItem) {
        saidaServico.getListaServicoFaturaItem().remove(servicoFaturaItem);
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
        saidaServico.getListaServicosPesquisa().add(servicoFaturaItem);
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

        ProdutoFaturaItem produtoFaturaItemX = new ProdutoFaturaItem();
        produtoFaturaItemX.setProduto(produtoPesquisa);
        produtoFaturaItemX.setValor(produtoPesquisa.getPrecoVenda());
        saidaServico.getListaProdutoFaturaItem().add(produtoFaturaItemX);
        produtoPesquisa = new Produto();

    }

    public String redireciona(SaidaServico s) {
        Sessions.put("saidaServicoPesquisa", s);
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

    public void loadListaSaidaServicoPesquisa() {
        listaSaidaServicoPesquisa.clear();
        listaSaidaServicoPesquisa = new SaidaServicoDao().pesquisaSaidaServicoFiltro(filtroSaidaServico);
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

    public SaidaServico getSaidaServico() {

        if (Sessions.exists("fisicaPesquisa")) {
            saidaServico.setFisica((Fisica) Sessions.getObject("fisicaPesquisa", true));
        }

        if (Sessions.exists("saidaServicoPesquisa")) {
            saidaServico = (SaidaServico) Sessions.getObject("saidaServicoPesquisa", true);
        }

        return saidaServico;
    }

    public void setSaidaServico(SaidaServico saidaServico) {
        this.saidaServico = saidaServico;
    }

    public List<SaidaServico> getListaSaidaServicoPesquisa() {
        return listaSaidaServicoPesquisa;
    }

    public void setListaSaidaServicoPesquisa(List<SaidaServico> listaSaidaServicoPesquisa) {
        this.listaSaidaServicoPesquisa = listaSaidaServicoPesquisa;
    }

    public List<OrdemServico> getListaOrdemServicoFeito() {
        return listaOrdemServicoFeito;
    }

    public void setListaOrdemServicoFeito(List<OrdemServico> listaOrdemServicoFeito) {
        this.listaOrdemServicoFeito = listaOrdemServicoFeito;
    }

    public double getTotalServico() {
        return totalServico;
    }

    public void setTotalServico(double totalServico) {
        this.totalServico = totalServico;
    }

    public double getTotalProduto() {
        return totalProduto;
    }

    public void setTotalProduto(double totalProduto) {
        this.totalProduto = totalProduto;
    }

    public double getTotalGeral() {
        return totalGeral;
    }

    public void setTotalGeral(double totalGeral) {
        this.totalGeral = totalGeral;
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

    public FiltroSaidaServico getFiltroSaidaServico() {
        return filtroSaidaServico;
    }

    public void setFiltroSaidaServico(FiltroSaidaServico filtroSaidaServico) {
        this.filtroSaidaServico = filtroSaidaServico;
    }

}
