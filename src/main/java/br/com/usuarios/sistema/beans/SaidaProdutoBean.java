/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuario.filtros.FiltroSaidaProduto;
import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Fisica;
import br.com.usuarios.sistema.OrdemServico;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.sistema.ProdutoFaturaItem;
import br.com.usuarios.sistema.SaidaProduto;
import br.com.usuarios.sistema.Servico;
import br.com.usuarios.sistema.dao.ProdutoDao;
import br.com.usuarios.sistema.dao.SaidaProdutoDao;
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
public class SaidaProdutoBean implements Serializable {

    private SaidaProduto saidaProduto;
    private double valor;
    private String statusFiltro;
    private Produto produtoPesquisa;
    private String dataCriacao;

    private List<SaidaProduto> listaSaidaProdutoPesquisa;
    private boolean valorCheck;
    private boolean dataCheck;

    private double totalProduto;
    private double totalGeral;

    private Produto produtoItemPojo;

    private ProdutoFaturaItem produtoFaturaItemEditar;
    private FiltroSaidaProduto filtroSaidaProduto;

    public SaidaProdutoBean() {

        this.filtroSaidaProduto = new FiltroSaidaProduto();
        this.produtoFaturaItemEditar = new ProdutoFaturaItem();
        this.totalProduto = new Double(0);
        this.totalGeral = new Double(0);
        produtoPesquisa = null;
        saidaProduto = new SaidaProduto();
        listaSaidaProdutoPesquisa = new ArrayList();

    }

    public void destroy() {

    }

    public void save() {

        DAO dao = new DAO(OrdemServico.class);

        if (saidaProduto.getFisica().getId() == null) {
            Messages.error("Necessário selecionar um cliente!");
            return;
        }

        if (saidaProduto.getListaProdutoFaturaItem().isEmpty()) {
            Messages.error("Necessário selecionar ao menos um Produto!");
            return;
        }

        if (saidaProduto.getId() == null) {
            if (!dao.save(saidaProduto)) {
                Messages.error("Erro ao salvar!!");
                return;
            } else {
                Messages.info("Salvo com Sucesso!");
                saidaProduto = new SaidaProduto();
                produtoPesquisa = new Produto();
                return;
            }
        } else {
            if (!dao.atualiza(saidaProduto)) {
                Messages.error("Erro ao atualizar!!");
                return;
            } else {
                Messages.info("Atualizado com Sucesso!");
                saidaProduto = new SaidaProduto();
                produtoPesquisa = new Produto();
                return;
            }
        }

    }

    public void deletar() {

        DAO dao = new DAO(SaidaProduto.class);
        if (saidaProduto.getId() == null) {
            Messages.error("Nenhuma saída de produto selecionada para exclusão!");
        }
        if (!dao.delete(saidaProduto)) {
            Messages.error("Erro ao deletar saída de produto!");
        } else {
            Messages.info("saída de produto excluida com sucesso!!");
            saidaProduto = new SaidaProduto();
        }
    }

    public void removeProduto(ProdutoFaturaItem produtoFaturaItem) {
        saidaProduto.getListaProdutoFaturaItem().remove(produtoFaturaItem);
        produtoPesquisa = new Produto();

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
        saidaProduto.getListaProdutoFaturaItem().add(produtoFaturaItemX);
        produtoPesquisa = new Produto();

    }

    public void editarProduto(ProdutoFaturaItem produtoFaturaItem) {

        for (ProdutoFaturaItem itemListaProduto : saidaProduto.getListaProdutoFaturaItem()) {

            if (itemListaProduto.equals(produtoFaturaItem)) {
                itemListaProduto.setValor(produtoFaturaItem.getValor());
                itemListaProduto.setQuantidade(produtoFaturaItem.getQuantidade());
            }

        }

        //linha para arrumar bug do autocomplete que retorna ultimo valor armazenado em itemValue do compenente quando renderiza a pagina.
        produtoPesquisa = new Produto();
        produtoFaturaItemEditar = new ProdutoFaturaItem();
    }

    public String redireciona(SaidaProduto s) {
        Sessions.put("saidaProdutoPesquisa", s);
        return ChamadaPaginaBean.urlRetorno();
    }

    public void calculaTotalProduto() {
        totalProduto = new Double(0);
        if (!saidaProduto.getListaProdutoFaturaItem().isEmpty()) {
            for (ProdutoFaturaItem produtoFaturaItem : saidaProduto.getListaProdutoFaturaItem()) {
                totalProduto += (produtoFaturaItem.getValor() * produtoFaturaItem.getQuantidade());
            }
        }

        calculaTotalGeral();
    }

    public void loadListaSaidaServicoPesquisa() {
        listaSaidaProdutoPesquisa.clear();
        listaSaidaProdutoPesquisa = new SaidaProdutoDao().pesquisaSaidaProdutoFiltro(filtroSaidaProduto);
    }

    public void calculaTotalGeral() {
        totalGeral = new Double(0);
        totalGeral += totalProduto;
    }

    public List<Produto> pesquisaProdutosPorDescricao(String caixaPesquisa) {

        List<Produto> listaProdutosPesquisados = new ArrayList();
        if (caixaPesquisa.length() >= 1) {
            caixaPesquisa = caixaPesquisa.trim();
            listaProdutosPesquisados = new ProdutoDao().pesquisaProdutoDescricao(caixaPesquisa);
        }
        return listaProdutosPesquisados;

    }

    public Produto getProdutoPesquisa() {
        return produtoPesquisa;
    }

    public void setProdutoPesquisa(Produto produtoPesquisa) {
        this.produtoPesquisa = produtoPesquisa;
    }

    public String getStatusFiltro() {
        return statusFiltro;
    }

    public void setStatusFiltro(String statusFiltro) {
        this.statusFiltro = statusFiltro;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public SaidaProduto getSaidaProduto() {

        if (Sessions.exists("fisicaPesquisa")) {
            saidaProduto.setFisica((Fisica) Sessions.getObject("fisicaPesquisa", true));
        }

        if (Sessions.exists("saidaProdutoPesquisa")) {
            saidaProduto = (SaidaProduto) Sessions.getObject("saidaProdutoPesquisa", true);
        }

        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
    }

    public List<SaidaProduto> getListaSaidaProdutoPesquisa() {
        return listaSaidaProdutoPesquisa;
    }

    public void setListaSaidaProdutoPesquisa(List<SaidaProduto> listaSaidaProdutoPesquisa) {
        this.listaSaidaProdutoPesquisa = listaSaidaProdutoPesquisa;
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

    public ProdutoFaturaItem getProdutoFaturaItemEditar() {
        return produtoFaturaItemEditar;
    }

    public void setProdutoFaturaItemEditar(ProdutoFaturaItem produtoFaturaItemEditar) {
        this.produtoFaturaItemEditar = produtoFaturaItemEditar;
    }

    public FiltroSaidaProduto getFiltroSaidaProduto() {
        return filtroSaidaProduto;
    }

    public void setFiltroSaidaProduto(FiltroSaidaProduto filtroSaidaProduto) {
        this.filtroSaidaProduto = filtroSaidaProduto;
    }

    public Produto getProdutoItemPojo() {
        return produtoItemPojo;
    }

    public void setProdutoItemPojo(Produto produtoItemPojo) {
        this.produtoItemPojo = produtoItemPojo;
    }

}
