package br.com.usuario.converter;

import br.com.usuarios.utilitarios.Currency;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean
public class ToPercent implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return (String) value; // Or (value != null) ? value.toString().toUpperCase() : null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            if (value.isEmpty()) {
                value = "0";
            }
            String somenteLetras = value.replaceAll("[^a-zA-Z]", "");
            if (somenteLetras.length() > 0) {
                return null;
            }
            value = value.replace("-", "");
            if (value.isEmpty()) {
                return null;
            }
            Double d = Currency.substituiVirgulaDouble(Currency.converteR$(value, 4));
            if (d <= 0 || d > 100) {
                d = new Double(0);
            }
            return "" + d;
        }
        return null;
    }

}
