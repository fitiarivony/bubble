/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fenetrage.components;

import back.objects.BddObject;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.Outils;
import utils.OutilsBdd;

/**
 *
 * @author FITIA ARIVONY
 */
public class Select extends Champ{
     

    public JComboBox<Object[]> getListe() {
        return (JComboBox<Object[]>)getComponent();
    }

    public void setListe(JComboBox<Object[]> liste) {
       this.component=liste;
    }

    @Override
    public Object getValue() throws Exception {
        return this.returnValue();
    }

    @Override
    public void setValue(Object o) {
       this.getListe().setSelectedItem(o);
    }
    
    public void setListe(Object[] o){
        setListe(new JComboBox(o));
    }
    
    public Select(Rectangle rec,Field field,JPanel panel) throws InvocationTargetException, InstantiationException, IllegalArgumentException, IllegalAccessException{
          this.setParent(panel);
        this.init(rec.x,rec.y,rec.width,rec.height, field);
        this.initListe(); 
        this.setLocation(rec.x,rec.y);
       this.reajust();
    }
    public Object returnValue() throws Exception{
        BddObject o=(BddObject)getListe().getSelectedItem();
        
        if(o.getPrimary_key()!=null){
            
            return OutilsBdd.get(o, o.getPrimary_key().getName());
        }
       return o;
    }
    
    
    public void initListe() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
        Class classe=getField().getDeclaringClass();
         Object obj=classe.newInstance();
        try{
            
       Method methode=classe.getMethod(getField().getName(),null);
        Object o=methode.invoke(obj,null);
        Object[]tab=(Object[])o;
        this.setListe(tab);
        }catch(Exception e){
                    Method[]fonction=classe.getMethods();
            for(int z=0;z<fonction.length;z++)
                    {
                        if(fonction[z].getName().equalsIgnoreCase(getField().getName()))
                        {
                            Object o=fonction[z].invoke(obj,null);
                            Object[]tab=(Object[])o;
                            this.setListe(tab);
                        }
                    } 
        }
       
    }

    @Override
    public void setComponent() {
       this.component=new JComboBox<Object[]>();
    }
}
