/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.dao.where;

import back.dao.where.Valeur;
import exception.DAOException;

/**
 *
 * @author FITIA ARIVONY
 */
public class Filtre {
    GenCase cas;
    String attribut;
    Valeur value;

    public Valeur getValue() throws DAOException {
        if(value==null)throw new DAOException("La valeur à comparer est nulle");
        return value;
    }

    public void setValue(Valeur value) {
        this.value = value;
    }
    
    
    public GenCase getCas() {
        return cas;
    }

    public void setCas(GenCase cas) {
        this.cas = cas;
    }

    public String getAttribut() {
        return attribut;
    }

    public void setAttribut(String attribut) {
        this.attribut = attribut;
    }
    
    public String genereCase() throws DAOException{
       return GenCase.getQuery(this.getCas(), this.getAttribut());
    }
    public Filtre(){}
    public Filtre(GenCase cas,String attribut,Valeur valeur){
        this.setAttribut(attribut);
        this.setCas(cas);
        this.setValue(valeur);
    }

    @Override
    public String toString() {
        return "Restriction{" + "cas=" + cas + ", attribut=" + attribut + ", value=" + value + '}';
    }
    
}
