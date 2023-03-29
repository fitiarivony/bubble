/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.dao.where;

import exception.DAOException;

/**
 *
 * @author FITIA ARIVONY
 */
public class Valeur {
    
    Object max;

    public Object getMax() {
        return max;
    }

    public void setMax(Object max) {
        this.max = max;
    }
    public Valeur(Object max){
        this.setMax(max);
    }

    @Override
    public String toString() {
        return "Valeur{" + "max=" + max + '}';
    }

   
  
    
}
