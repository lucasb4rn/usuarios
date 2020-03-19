/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Fisica;
import br.com.usuarios.sistema.dao.FisicaDao;
import br.com.usuarios.utilitarios.ChamadaPaginaBean;
import br.com.usuarios.utilitarios.Dates;
import br.com.usuarios.utilitarios.Messages;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class FisicaBean implements Serializable {

    private Fisica fisica;
    private String selectedDtNascimento;
    private String filter;
    private String caixaPesquisa;
    private List<Fisica> listaFisicaPesquisa;

    public FisicaBean() {
        fisica = new Fisica();
        caixaPesquisa = "";
        filter = "";
        listaFisicaPesquisa = new ArrayList();
    }

    public String destroy() {
        Sessions.remove("fisicaBean");
        Sessions.remove("fisicaPesquisa");
        return "cadastro_fisica?faces-redirect=true";
    }

    public void save() {

        DAO dao = new DAO(Fisica.class);

        if (fisica.getId() != null) {
            if (!dao.atualiza(fisica)) {
                Messages.error("Erro ao Atualizar!");
            } else {
                Messages.info("Atualizado com sucesso!!");
            }
        } else {
            if (!dao.save(fisica)) {
                Messages.error("Erro ao salvar!");
            } else {
                Messages.info("Salvo com sucesso!!");
            }
        }
    }

    public void findPessoaFiltro() {
        listaFisicaPesquisa.clear();
        listaFisicaPesquisa = new FisicaDao().pesquisaPessoaFiltro(filter, caixaPesquisa);
    }

    public String redireciona(Fisica f) {
        Sessions.put("fisicaPesquisa", f);
        return ChamadaPaginaBean.urlRetorno();
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

    public String getSelectedDtNascimento() {
        return selectedDtNascimento;
    }

    public void setSelectedDtNascimento(String selectedDtNascimento) {
        fisica.setDataNascimento(Dates.converte(selectedDtNascimento));
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

    public List<Fisica> getListaFisicaPesquisa() {
        return listaFisicaPesquisa;
    }

    public void setListaFisicaPesquisa(List<Fisica> listaFisicaPesquisa) {
        this.listaFisicaPesquisa = listaFisicaPesquisa;
    }

}
