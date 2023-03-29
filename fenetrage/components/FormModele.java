/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fenetrage.components;

import back.objects.BddObject;
import back.annotations.Attribut;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.JPanel;
import utils.Outils;
import utils.OutilsBdd;

/**
 *
 * @author FITIA ARIVONY
 */
public abstract class FormModele extends JPanel{
    BddObject obj;
    Champ[] champs;
    
    public Champ[] getChamps() {
        return champs;
    }

    public void setChamps(Champ[] champs) {
        this.champs = champs;
    }
    
    public BddObject getObj() {
        return obj;
    }

    public void setObj(BddObject obj) {
        this.obj = obj;
    }
    public void init(){
        Champ[]champ=new Champ[0];
        this.setChamps(champ);
    }
    public Champ[] refill(int ajouter){
        Champ[]champs=new Champ[getChamps().length+ajouter];
        for(int i=0;i<getChamps().length;i++){
            champs[i]=getChamps()[i];
        }
        return champs;
    }
    
    
     public abstract FormModele genere(BddObject obj)throws Exception;
     
      public void addComponents(BddObject obj,Point location,Field[]attribut) throws InvocationTargetException, InstantiationException, IllegalArgumentException, IllegalAccessException,Exception{
         //localisation
        
        Rectangle rec=new Rectangle(0,100,100,50);
        
        //Recuperation info des classes
        Class classe=obj.getClass();
       
        int j=this.getChamps().length;
        
        Champ[]champs=this.refill(attribut.length);
        Method[]fonction=classe.getMethods();
        //insertion des attributs
        int localx=location.x;
        int localy=location.y;
        
        for(int k=0;k<attribut.length;k++)
        {
                if(this.in(obj,attribut[k]))
                {           
                             Select sel=new Select(new Rectangle(localx,localy,rec.width,rec.height),attribut[k],this);
                                sel.add(this);
//                              localx=sel.getLabel().getLocation().x+sel.getLabel().getWidth()*2+50;
//                                localy=sel.getLabel().getLocation().y;
                                 champs[j]=sel;
                            j++;
                }
                   else
                    {
                            Input input=new Input(new Rectangle(localx,localy,rec.width,rec.height),attribut[k],this);          
                            input.add(this);
                         
//                            localx=input.getLabel().getLocation().x+input.getLabel().getWidth()*2+50;
//                            localy=input.getLabel().getLocation().y;
                            champs[j]=input;
                            j++;
                        }
            
        }
       this.setChamps(champs);
    }
      
      
      public boolean  in(BddObject obj,Field attribut)
    {
        try{           
        Class classe=obj.getClass();
        Method fonction;
        fonction = classe.getMethod(attribut.getName(),  null);
       
        return true;   
        }catch(Exception e){
           //  System.out.println(attribut.getName());
            return false;
        }
    }
    
     
   
}
