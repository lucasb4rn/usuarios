/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.dao;

import br.com.usuario.filtros.FiltroSaidaServico;
import br.com.usuarios.DB.JPAUtil;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.sistema.SaidaServico;
import br.com.usuarios.utilitarios.Dates;
import static br.com.usuarios.utilitarios.Dates.data;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Lucas
 */
public class SaidaServicoDao {

    public List<SaidaServico> pesquisaSaidaServicoFiltro(FiltroSaidaServico filtroSaidaServico) {
        try {

            List listaWhere = new ArrayList();

            String queryString = "select * from fin_saida_servico s join pes_fisica f on f.id = s.id_fisica ";

            if (filtroSaidaServico.isCheckCliente() && filtroSaidaServico.getClienteNome().length() > 0) {
                listaWhere.add("f.ds_nome like '%" + filtroSaidaServico.getClienteNome() + "%'");
            }

            if (filtroSaidaServico.isCheckDataCriacao() && filtroSaidaServico.getDataCadastro().length() > 0) {
                listaWhere.add("s.dt_cadastro = '" + Dates.converteStringToSqlDate(filtroSaidaServico.getDataCadastro()) + "'");
            }

            for (int i = 0; i < listaWhere.size(); i++) {
                if (i == 0) {
                    queryString += " WHERE " + listaWhere.get(i) + " \n";
                } else {
                    queryString += " AND " + listaWhere.get(i) + " \n";
                }
            }

            Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString, SaidaServico.class);
            return qry.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

}
