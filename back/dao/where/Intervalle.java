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
public class Intervalle extends Valeur{
    Object min;

    public Intervalle(Object max) {
        super(max);
    }
    public Intervalle(Object max,Object min) {
        super(max);
        
        this.setMin(min);
    }
    public Object getMin(){
        return min;
    }

    public void setMin(Object min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Intervalle{" + "min=" + min + ",max="+max+'}';
    }
    

  
}
