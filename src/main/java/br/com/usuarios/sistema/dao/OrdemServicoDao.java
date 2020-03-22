/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.dao;

import br.com.usuario.filtros.FiltroOrdemServico;
import br.com.usuarios.DB.JPAUtil;
import br.com.usuarios.sistema.OrdemServico;
import br.com.usuarios.sistema.OrdemStatus;
import br.com.usuarios.utilitarios.Dates;
import static br.com.usuarios.utilitarios.Dates.data;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Lucas
 */
public class OrdemServicoDao {

    public List<OrdemServico> pesquisaOrdemServicoFiltro(FiltroOrdemServico filtroOrdemServico) {
        try {

            List listaWhere = new ArrayList();

            String queryString = "select * from sis_ordem_servico s join pes_fisica p on p.id = s.id_fisica";

            listaWhere.add(" s.status = '" + filtroOrdemServico.getStatusOrdem() + "'");
            
            if (filtroOrdemServico.isClienteCheck() && filtroOrdemServico.getClienteNome().length() > 0) {
                listaWhere.add(" p.ds_nome like '%" + filtroOrdemServico.getClienteNome() + "%'");
            }

            if (filtroOrdemServico.isDataCheck() && filtroOrdemServico.getDataCriacao().length() > 0) {
                listaWhere.add(" s.dt_cadastro = '" + Dates.converteStringToSqlDate(filtroOrdemServico.getDataCriacao()) + "'");
            }

            for (int i = 0; i < listaWhere.size(); i++) {
                if (i == 0) {
                    queryString += " WHERE " + listaWhere.get(i) + " \n";
                } else {
                    queryString += " AND " + listaWhere.get(i) + " \n";
                }
            }

            String orderBy = "order by p.ds_nome, s.dt_cadastro";
            
            Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString + orderBy, OrdemServico.class);
            return qry.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<OrdemServico> retornaOrdemServicoPorStatus(OrdemStatus ordemStatus) {

        String queryString = "select * from sis_ordem_servico where status = '" + ordemStatus + "'";

        try {
            Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString, OrdemServico.class);
            return qry.getResultList();

        } catch (Exception e) {
            return new ArrayList();
        }

    }

}
