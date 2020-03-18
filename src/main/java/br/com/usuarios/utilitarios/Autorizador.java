/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.utilitarios;

import br.com.usuarios.sistema.Usuario;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class Autorizador implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        String nomePagina = context.getViewRoot().getViewId();

        if ("/login.xhtml".equals(nomePagina)) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(nomePagina);
            return;
        }

        Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

        if (usuarioLogado != null) {
            return;
        }

        NavigationHandler handler = context.getApplication().getNavigationHandler();
        handler.handleNavigation(context, null, "/login.xhtml?faces-redirect=true");
        context.renderResponse();;

    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
