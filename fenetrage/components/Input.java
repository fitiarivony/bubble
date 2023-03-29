/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fenetrage.components;

import java.awt.Rectangle;
import java.lang.reflect.Field;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utils.Formatage;

/**
 *
 * @author FITIA ARIVONY
 */
public class Input extends Champ {
   
   
    public JTextField getText() {
        return (JTextField)this.getComponent();
    }

    public void setText(JTextField text) {
        this.component = text;
    }
    
    
    @Override
    public Object getValue()throws Exception {
     String text=getText().getText();
     
    Formatage format=new Formatage(text,getField().getType());
       return format.parse();
    }

    @Override
    public void setValue(Object o) throws Exception {
        getText().setText(o.toString());
    }

  
     public Input(Rectangle rec,Field field,JPanel panel){
         this.setParent(panel);
         this.init(rec.x,rec.y,rec.width,rec.height, field);
        this.setText(new JTextField());
         this.setLocation(rec.x,rec.y);
         this.reajust();
    }

    @Override
    public void setComponent() {
        this.component=new JTextField();
    }
    
     
     
}
