/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Timestamp;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author FITIA ARIVONY
 */
public class Formatage {
    String text;
   Class classe;
    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

   
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public Formatage(String text,Class classe){
        setText(text);
        setClasse(classe);
    }
    public Date format() throws ParseException{
        try{
             SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
             return  format.parse(this.getText());
        }catch(Exception e){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
           return  format.parse(this.getText());
        }
        
    }
      public Timestamp formatTime() throws ParseException{
          System.out.println("ato");
        try{
             SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             Timestamp stamp=new Timestamp(format.parse(this.getText()).getTime());
             
             return stamp;
        }catch(Exception e){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           return  new Timestamp(format.parse(this.getText()).getTime());
        }
        
    }

    public Object parse() throws InstantiationException, IllegalAccessException, ParseException{
      
        if(getClasse().getSimpleName().equalsIgnoreCase("Integer")){
            return Integer.parseInt(getText());
        }
        if(getClasse().getSimpleName().equalsIgnoreCase("Float")){
            return Float.parseFloat(getText());
        }
        if(getClasse().getSimpleName().equalsIgnoreCase("Double")){
            return Double.parseDouble(getText());
        }
         if(getClasse().getSimpleName().equalsIgnoreCase("Boolean")){
             return Boolean.getBoolean(this.getText());
           
        }
         if(getClasse().getSimpleName().equalsIgnoreCase("Timestamp")){
            return this.formatTime();
        }
        
         Object objet=getClasse().newInstance();
         
        if(objet instanceof java.util.Date){
            
            return this.format();
        }
        if(objet instanceof java.sql.Date){
            return new java.util.Date(new java.util.Date(getText()).getTime());
        }
        return getText();
    }
   
  
}
