/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Fisica;
import br.com.usuarios.sistema.dao.FisicaDao;
import br.com.usuarios.utilitarios.AnaliseString;
import br.com.usuarios.utilitarios.ChamadaPaginaBean;
import br.com.usuarios.utilitarios.Messages;
import br.com.usuarios.utilitarios.Sessions;
import br.com.usuarios.utilitarios.ValidaDocumentos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CadastroFisicaBean implements Serializable {

    private Fisica fisica;
    private String filter;
    private String caixaPesquisa;
    private List<Fisica> listaFisicaPesquisa;

    public CadastroFisicaBean() {
        fisica = new Fisica();
        caixaPesquisa = "";
        filter = "";
        listaFisicaPesquisa = new ArrayList();
    }

    public String destroy() {
        Sessions.remove("fisicaPesquisa");
        return ChamadaPaginaBean.pagina("cadastroFisica");
    }

    public void save() {

        DAO dao = new DAO(Fisica.class);

        if (fisica.getNome().length() < 1) {
            Messages.error("Nome precisa ser preenchido!!!");
            return;
        }

        if (fisica.getEmail().length() > 0) {
            if (!ValidaDocumentos.isEmailValido(fisica.getEmail())) {
                Messages.error("Email inválido!");
                return;
            }
        }

        if (!ValidaDocumentos.isValidoCPF(AnaliseString.extrairNumeros(fisica.getDocumento()))) {
            Messages.error("CPF inválido!");
            return;
        }

        if (fisica.getDocumento().length() == 0) {
            Messages.error("CPF obrigatório!!");
            return;
        }

        Fisica pesquisaPessoaPorCPF = new FisicaDao().pesquisaPessoaPorCPF(fisica.getDocumento());

        if (pesquisaPessoaPorCPF != null) {
            if (!pesquisaPessoaPorCPF.getId().equals(fisica.getId())) {
                Messages.error("Pessoa com esse CPF já existe!");
                return;
            }
        }

        if (fisica.getId() != null) {

            if (!dao.atualiza(fisica)) {
                Messages.error("Erro ao Atualizar!");
                return;
            } else {
                fisica = new Fisica();
                Messages.info("Atualizado com sucesso!!");
                return;
            }

        } else {

            if (!dao.save(fisica)) {
                Messages.error("Erro ao salvar!");
                return;
            } else {
                fisica = new Fisica();
                Messages.info("Salvo com sucesso!!");
                return;
            }
        }
    }

    public void deletar() {

        DAO dao = new DAO(Fisica.class);

        if (fisica.getId() != null) {
            if (!dao.delete(fisica)) {
                Messages.error("Erro ao excluído Cliente!");
                return;
            } else {
                Messages.info("Cliente excluído com sucesso!!");
                fisica = new Fisica();
                return;
            }
        }

        Messages.info("Selecionar um cliente para excluído!!");

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
