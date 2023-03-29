/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fenetrage.components;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author FITIA ARIVONY
 */
public abstract class Champ{
    JLabel label;
    JComponent component;
    Field field;
    JPanel parent;
    
    public abstract void setComponent();
    public abstract Object getValue()throws Exception;
    public abstract void setValue(Object o) throws Exception;

    public JPanel getParent() {
        return parent;
    }

    public void setParent(JPanel parent) {
        this.parent = parent;
    }
    public void add(JPanel parent){
        setParent(parent);
        getParent().add(getLabel());
        getParent().add(getComponent());
    }
    
    public  void setLocation(int x,int y){
          this.getLabel().setLocation(x, y);
        this.getComponent().setBounds(x+this.getLabel().getWidth(), y,this.getLabel().getWidth(),this.getLabel().getHeight());
        
    }
    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
  public JComponent getComponent(){
      return this.component;
  }
    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }
    public String getNomLabel(){
        return label.getText();
    }
    public void setNomLabel(String text){
        this.label.setText(text);
    }
    
    public void init(int x,int y,int width,int height,Field field){
        this.setLabel(new JLabel());
        this.setField(field);
          this.getLabel().setBounds(x, y,width,height);
           this.getLabel().setText(getField().getName());
          
    }
    public void reajust(){
        if(getComponent().getX()+getComponent().getWidth()>=getParent().getWidth()){
            
            getLabel().setLocation(0, getLabel().getHeight()+getLabel().getY());
            getComponent().setLocation(getLabel().getWidth(),getLabel().getY());
            
        }
    }
     
    
    public static Champ[] toArray(ArrayList<Champ> champs)
   {
       Champ[]table=new Champ[champs.size()];
       for(int i=0;i<champs.size();i++)
       {
           table[i]=champs.get(i);
       }
       return table;
   }
     
     
    
   
}
