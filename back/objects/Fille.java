/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.objects;

import fenetrage.components.Champ;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import utils.Outils;
import utils.OutilsBdd;

/**
 *
 * @author FITIA ARIVONY
 */
public abstract class Fille extends BddObject{
    Mere mere;
    Object id;
    
    public void setId(Object id){
        this.id=id;
    }
    public abstract  void setId();
    public abstract  void setForeign(Object obj);
    public  Object getId(){
        return this.id;
    }
    public Mere getMere() {
        return mere;
    }

    public void setMere(Mere mere) {
        this.mere = mere;
    }
     

    
     
    
}
