/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.helpers;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Lucas
 */

@ManagedBean
@ViewScoped
public class UserAgentHelper implements Serializable {

    public static Boolean IS_MOBILE() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String userAgent = request.getHeader("user-agent");
        System.out.println(userAgent);

        return userAgent.toLowerCase().contains("android")
                || userAgent.toLowerCase().contains("iphone")
                || userAgent.toLowerCase().contains("ipad")
                || userAgent.toLowerCase().contains("ipod")
                || userAgent.toLowerCase().contains("blackberry")
                || userAgent.toLowerCase().contains("windows phone")
                || userAgent.toLowerCase().contains("mobile safari");

    }

    public Boolean getMobile() {
        return IS_MOBILE();
    }
}
