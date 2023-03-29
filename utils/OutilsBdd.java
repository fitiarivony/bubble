/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import back.dao.Criteri;
import back.dao.where.Intervalle;
import back.dao.where.Filtre;
import back.dao.where.Valeur;
import back.annotations.Table;
import back.annotations.Attribut;
import back.annotations.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import exception.DAOException;
import exception.MappingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author FITIA ARIVONY
 */
public class OutilsBdd {
    
    Criteri crit;

    @JsonIgnore
    public Criteri getCrit() {
        if(crit==null)this.setCrit(new Criteri());
        return crit;
    }

    public void setCrit(Criteri crit) {
        this.crit = crit;
    }

   @JsonIgnore
    public   Table getTable()throws  MappingException{
       
             Table annotation=(Table) this.getClass().getAnnotation(Table.class);
             if(annotation!=null){
                  return annotation; 
             }
            throw new MappingException("It is not a table");  
    }
    public static Attribut getPrimary(Field o){
     
           Attribut annotation=(Attribut)o.getAnnotation(Attribut.class);  
            if(annotation!=null){
                 if( annotation.primary_key()){
                    
             return annotation; 
            }
            }
           
           return null;   
    }
     public static Attribut getAttribut(Field o){
     
           Attribut annotation=(Attribut)o.getAnnotation(Attribut.class);  
            if(annotation!=null){
               
                
             return annotation; 
            
            }
           
           return null;   
    }
    @JsonIgnore
    public Field getPrimary_key(){
         Field[]fields=this.getClass().getDeclaredFields();
          for(int i=0;i<fields.length;i++){
            
                 if(getPrimary(fields[i])!=null)return fields[i];
                    
          }
          return null;
    }
    
    public static Attribut getNonPrimary(Field o) throws MappingException{
     
            Attribut annotation=(Attribut)o.getAnnotation(Attribut.class);  
         
            if(annotation!=null){
                 if(!annotation.primary_key()){
                    
             return annotation; 
            }
            }
           
           return null;         
    }
    
   @JsonIgnore
    public Field[] getTraitable() throws MappingException, Exception{
        Class cl=this.getClass();
        Field[]fields=cl.getDeclaredFields();
        ArrayList<Field>inserer=new ArrayList<>();
        for(int i=0;i<fields.length;i++){
            if(OutilsBdd.getNonPrimary(fields[i])!=null && this.get(fields[i])!=null){
                inserer.add(fields[i]);
            }
        }
        return inserer.toArray(new Field[inserer.size()]);
    }
      public Field[] allAttributes() throws MappingException, Exception{
        Class cl=this.getClass();
        Field[]fields=cl.getDeclaredFields();
        ArrayList<Field>inserer=new ArrayList<>();
        for(int i=0;i<fields.length;i++){
            if(OutilsBdd.getAttribut(fields[i])!=null){
                inserer.add(fields[i]);
            }
        }
        return inserer.toArray(new Field[inserer.size()]);
    }
    
   
     public  ArrayList<Object> valeurs(Field[] inserer) throws Exception{
        ArrayList<Object> valeur=new ArrayList<>();
        for(int i=0;i<inserer.length;i++){
              //System.out.println((get(this,inserer[i].getName())));
             if(get(this,inserer[i].getName()) instanceof Date){
                 if(get(this,inserer[i].getName()) instanceof Timestamp){       
                Timestamp daty=(Timestamp)get(this,inserer[i].getName());
              
                valeur.add(new java.sql.Timestamp(daty.getTime()));
            }else{
                      Date daty=(Date)get(this,inserer[i].getName());
                valeur.add(new java.sql.Date(daty.getTime()));
                 }
            }else{
                valeur.add((get(this,inserer[i].getName())));
            }
        }
        
        return valeur;
    }
     
     
    
    
    public  Object valeurs_key(Object o) throws Exception{
            if(get(o,this.getPrimary_key().getName()) instanceof Date){
               
                Date daty=(Date)get(o,this.getPrimary_key().getName());
               return daty;
            }else{
               
                return ((get(o,this.getPrimary_key().getName())));
            }
    }
    
  
    
    public static Object get(Object o,String attribut) throws Exception{
        
        Class cl=o.getClass();
        Object obj=new Object();
        String fonction=attribut.substring(0, 1).toUpperCase()+attribut.substring(1);
        try {
            Method meth=cl.getMethod("get"+fonction,null);
            obj=meth.invoke(o,null);
          return obj;
        } catch (NoSuchMethodException ex) {
          Method[]methods=cl.getMethods();
                for(int j=0;j<methods.length;j++){
                   System.out.println(methods[j].getName().toLowerCase()+"-----"+"get"+attribut.toLowerCase());
                    if(methods[j].getName().toLowerCase().equalsIgnoreCase("get"+attribut.toLowerCase())){
                        
                        obj=methods[j].invoke(o,null);
                        return obj;
                         
                        
                    }
                }
        } 
        System.out.println(attribut);
        throw new Exception("Tsy mitovy attribut getters");
    }
    
    
      public  Object get(Field attribut) throws Exception{
        attribut.setAccessible(true);
        return attribut.get(this);
      }
      public void set(Field attribut,Object valeur) throws IllegalArgumentException, IllegalAccessException{
          attribut.setAccessible(true);
          attribut.set(this, valeur);
      }
    public static void setValeur(Object o,String attribut ,Object valeur) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception{
        Class cl=o.getClass();
        
         String fonction=attribut.substring(0, 1).toUpperCase()+attribut.substring(1);
        try {
    //  System.out.println(fonction+"----"+"  "+valeur+" "+OutilsBdd.getField(o, attribut).getType());        
            Method meth=cl.getMethod("set"+fonction,OutilsBdd.getField(o, attribut).getType());
            
     
            meth.invoke(o,valeur);
            
        } catch (NoSuchMethodException ex) {
            
          Method[]methods=cl.getMethods();
                for(int j=0;j<methods.length;j++){
                    if(methods[j].getName().toLowerCase().equalsIgnoreCase("set"+attribut.toLowerCase())){
                   
                        methods[j].invoke(o,valeur);
                        break;
                    }
                }
        } 
      //  System.out.println(o);
    }
     public static String getWhere(Integer i,String sql){
        if(i==0){
            sql+=" where ";
            return sql;
        }
        sql+=" and ";
        return sql;
    }
     public static Field getField(Object o,String attribut){
         Field[]attributs=o.getClass().getDeclaredFields();
         for(int i=0;i<attributs.length;i++){
             if(attributs[i].getName().equalsIgnoreCase(attribut))return attributs[i];
         }
         return null;
     }
     public Filtre initValue(Filtre restr,Field attribut) throws DAOException, Exception{
            if(restr.getValue()==null)restr.setValue(new Valeur(this.get(attribut)));
           
            if(restr.getValue() instanceof Intervalle){
                ((Intervalle)restr.getValue()).setMax(this.get(attribut));
            }
            return restr;
     }
    
     public Filtre[] storeRestrictions() throws Exception{
           Field[] fields=this.getTraitable();
           Filtre[]restrictions=new Filtre[fields.length];
           int k=0;
          if(this.getCrit().getRestrictions().isEmpty()){
            for(int i=0;i<fields.length;i++){
               restrictions[i]=new Filtre(null,OutilsBdd.getNonPrimary(fields[i]).name(),new Valeur(this.get(fields[i])));
            }  
            return restrictions;
          }

             for(int i=0;i<fields.length;i++){
            
          for(int j=0;j<this.getCrit().getRestrictions().size();j++){
              if(OutilsBdd.getNonPrimary(fields[i]).name()
                      .equalsIgnoreCase(
                      this.getCrit().getRestrictions().get(j).getAttribut()
                      )){
                  restrictions[k]=this.initValue(this.getCrit().getRestrictions().get(j),fields[i]) ;
                  k++;
                  break;
              }
              if(j==this.getCrit().getRestrictions().size()-1){
                  restrictions[k]=new Filtre(null,OutilsBdd.getNonPrimary(fields[i]).name(),new Valeur(this.get(fields[i])));
                  k++;
              }
          }
             }
             for(Filtre rest:restrictions)System.out.println(rest);
       return restrictions;
     }
     
    
  
    
}
