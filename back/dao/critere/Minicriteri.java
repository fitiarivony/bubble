/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.dao.critere;

import java.util.ArrayList;

/**
 *
 * @author FITIA ARIVONY
 */
public abstract class Minicriteri {
    String name;
    String attribut;
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribut() {
        return attribut;
    }

    public void setAttribut(String attribut) {
        this.attribut = attribut;
    }
    public abstract String writeSQL(ArrayList<Minicriteri> crit)throws Exception;
    
}
