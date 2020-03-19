/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.utilitarios;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class ChamadaPaginaBean {

    public synchronized static String urlRetorno() {
        if (Sessions.exists("urlRetorno")) {
            String urlRetorno = Sessions.getString("urlRetorno");
            return urlRetorno + "?faces-redirect=true";
        }
        return "";
    }

    public synchronized static String pagina(String pagina) {
        HttpServletRequest paginaRequerida = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Sessions.put("urlRetorno", converteURL(paginaRequerida.getRequestURI()));
        return pagina + "?faces-redirect=true";
    }

    public static String converteURL(String urlDest) {
        return urlDest.substring(urlDest.lastIndexOf("/") + 1, urlDest.lastIndexOf("."));
    }

}
