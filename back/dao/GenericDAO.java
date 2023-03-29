/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.dao;

import back.dao.critere.Minicriteri;
import back.dao.where.Intervalle;
import back.dao.where.Filtre;
import back.annotations.Attribut;
import back.objects.BddObject;
import exception.DAOException;
import exception.MappingException;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import utils.Connect;
import utils.Outils;
import utils.OutilsBdd;

/**
 *
 * @author FITIA ARIVONY
 */
public class GenericDAO extends OutilsBdd{
    
    public  boolean save(Connect conn)throws ClassNotFoundException,SQLException, Exception{
        PreparedStatement stat=null;
//        System.out.println(o);
        try{
          conn.getConnection();
            String sql="INSERT INTO "+this.getTable().name()+"(";
            Field[]fields=this.getTraitable();
            for(int i=0;i<fields.length;i++){
                if(i==fields.length-1)sql+=OutilsBdd.getNonPrimary(fields[i]).name()+")";
                else sql+=OutilsBdd.getNonPrimary(fields[i]).name()+",";
                
            }
         
        sql+=" values (";
       
        ArrayList<Object>valeurs=this.valeurs(fields);
        for(int i=0;i<valeurs.size();i++){
            if(i==fields.length-1)sql+="? )";
            else sql+="? ,";
        }
        stat=conn.prepareStatement(sql);
          for(int i=0,j=1;i<valeurs.size();i++,j++){
              
            //  System.out.println(valeurs.get(i).toString()+"-------").?
              stat.setObject(j,valeurs.get(i));
        }
      System.out.println(stat);
         stat.executeUpdate();
        }catch(Exception e){
            // System.out.println("tonga ato");
            e.printStackTrace();
           return false;
        }finally{
           
            if(stat!=null){
                stat.close();
            }
             conn.close();
        }
         return true;
          
    }
     public  boolean update(Connect conn) throws SQLException, Exception{
     
        PreparedStatement stat=null;
         String sql="UPDATE "+this.getTable().name()+" set ";
        try{
             conn.getConnection();
             Field[] attributs=this.getTraitable();
             for(int i=0;i<attributs.length;i++){
               //  System.out.println(attributs[i]);
                 sql+=OutilsBdd.getNonPrimary(attributs[i]).name()+" = ";
                 if(i!=attributs.length-1){
                   sql+="? ,";
                }else {
                     sql+="? ";
                 }
             }
             sql+="   where  ";
          
                sql+=OutilsBdd.getPrimary(this.getPrimary_key()).name()+"=";
                sql+=" ? ";
             stat=conn.prepareStatement(sql);
           ArrayList<Object>valeurs=this.valeurs(attributs);
            int j;
            int i;
           for(i=0,j=1;i<valeurs.size();i++,j++){
              
              stat.setObject(j,valeurs.get(i));
        }
              stat.setObject(j,this.get(this.getPrimary_key()));
       
          System.out.println(stat);
           stat.executeUpdate();
             
               
        }catch(Exception e){
            e.printStackTrace();
           return false;
        }finally{
            if(stat!=null)stat.close();
            
            if(conn!=null)conn.close();
            
        }
        return true;
    }
     public  boolean delete(Connect conn) throws SQLException, MappingException,Exception{
        
         PreparedStatement stat=null;
         String sql="DELETE FROM "+this.getTable().name();
         try{
             sql+="  where ";
            sql+=OutilsBdd.getPrimary(this.getPrimary_key()).name()+"=";
                     sql+=" ? ";
           //  System.out.println(sql);
          
           conn.getConnection();
              stat=conn.prepareStatement(sql);      
              stat.setObject(1,this.get(this.getPrimary_key()));
           System.out.println(stat);
            stat.executeUpdate();
         }catch(Exception e){
             e.printStackTrace();
             return false;
         }finally{
             if(stat!=null){
                 stat.close();
             }
             if(conn!=null){
                 conn.close();
             }
         }
         return true;
     }
     public  ArrayList<Object> selectAll(Connect conn) throws SQLException, MappingException, DAOException, Exception{
         String sql="SELECT * from "+this.getTable().name();
        
         PreparedStatement stat=null;
         ResultSet res=null;
       
         try{
             conn.getConnection();
             sql+=this.getCrit().initCritere();
             
             System.out.println("Avant traitement:"+sql);
             stat=conn.prepareStatement(sql);
             res=stat.executeQuery();
             System.out.println("Apres traitement:"+stat);
             
            return this.extractResult(res);             
         }finally{
             if(res!=null){
                 res.close();
             }
             if(stat!=null){
                 stat.close();
             }
             this.setCrit(null);
         }
       
     }
     
     
      public  ArrayList<Object> getById(Connect conn) throws SQLException, MappingException, Exception{
         String sql="SELECT * from "+this.getTable().name();
         
         PreparedStatement stat=null;
         ResultSet res=null;
         try{
             sql+="  where ";
             sql+=OutilsBdd.getPrimary(this.getPrimary_key()).name()+"=";
                     sql+=" ? ";
            //System.out.println(sql);
          sql+=this.getCrit().initCritere();
             conn.getConnection();
               System.out.println("Avant traitement:"+sql);
              stat=conn.prepareStatement(sql);      
              stat.setObject(1,this.get(this.getPrimary_key()));
           System.out.println("Apres traitement:"+stat);
             res=stat.executeQuery();
            return this.extractResult(res);   
         }finally{
             if(res!=null){
                 res.close();
             }
             if(stat!=null){
                 stat.close();
             }
             if(conn!=null){
                 conn.close();
             }
             this.setCrit(null);
         }
        
     }
      
    
        
       
   public  Object getLastId(Connect conn) throws SQLException, Exception{
       String sql="select "+OutilsBdd.getPrimary(this.getPrimary_key()).name()+ "   dernier from "
               +OutilsBdd.getPrimary(this.getPrimary_key()).name()
               +" order by substring("
               +OutilsBdd.getPrimary(this.getPrimary_key()).name()
               +"  from 4)::integer desc limit 1";
       PreparedStatement stat=null;
       ResultSet res=null;
       try{
           conn.getConnection();
           stat=conn.prepareStatement(sql);
           System.out.println(stat);
           res=stat.executeQuery();
           while(res.next())return res.getObject("dernier");
       }catch(Exception e){
           throw e;
       }finally{
           if(res!=null)res.close();
           if(stat!=null)stat.close();
       }
       throw new Exception("No last id");
   }
   
   
   
    public  Object[] getByIds(Connect conn) throws Exception{
       
        
       // Field[] fields=this.getTraitable();
        
         
       String sql="SELECT * FROM "+this.getTable().name();
       Filtre[] rest=this.storeRestrictions();
        System.out.println(rest.length);
       for(int i=0;i<rest.length;i++){
           sql=OutilsBdd.getWhere(i, sql);
         
           sql+=rest[i].genereCase();
       }
       PreparedStatement stat=null;
         ResultSet res=null;
       try{
           conn.getConnection();
           sql+=this.getCrit().initCritere();
             System.out.println("Avant traitement:"+sql);
           stat=conn.prepareStatement(sql);
           
          for(int i=0,j=1;i<rest.length;i++,j++){
               stat.setObject(j,rest[i].getValue().getMax());
              // System.out.println(rest[i].getValue());
               if(rest[i].getValue() instanceof Intervalle){
                    j++;
                   stat.setObject(j,((Intervalle)rest[i].getValue()).getMin());
               }
           }
           System.out.println("Apres traitement:"+stat);
         res=stat.executeQuery();
    
         return this.extractResult(res).toArray();
       }
       finally{
             
        if(res!=null)res.close();
        if(stat!=null)stat.close();
        if(conn!=null)conn.close();
        this.setCrit(null);
    }
       
        
    }
   public  ArrayList<Object> extractResult(ResultSet res)throws Exception{
        Class cl=this.getClass();
        ArrayList<Object>liste=new ArrayList<Object>();
        Field[]colonne=this.allAttributes();
         while(res.next()){     
                 Object valiny=new Object();
                valiny=cl.newInstance();
            for(int i=0;i<colonne.length;i++){
                if(res.getObject((OutilsBdd.getAttribut(colonne[i]).name()))!=null){
                   
                   OutilsBdd.setValeur(valiny,colonne[i].getName(),res.getObject((OutilsBdd.getAttribut(colonne[i]).name())));
               }
                
            }
          //  System.out.println(valiny);
            liste.add(valiny);
        }
       
         return liste;
    }
   public  Object[] executeQuery(String sql,Connect conn,String attribut)throws Exception{
       PreparedStatement stat=null;
       ResultSet res=null;
       ArrayList<Object>objets=new ArrayList<Object>();
       try{
         conn.getConnection();
          sql+=this.getCrit().initCritere();
          
            System.out.println("Avant traitement:"+sql);
           stat=conn.prepareStatement(sql);
          
           System.out.println("Apres traitement:"+stat);
           res=stat.executeQuery();
           while(res.next()){
               objets.add(res.getObject(attribut));
           }
           return objets.toArray();
       }finally{
           if(res!=null)res.close();
           if(stat!=null)stat.close();
           if(conn!=null)conn.close();
           this.setCrit(null);
       }
   }
   public  ArrayList<Object> executeQuery(String sql,Connect conn)throws Exception{
       PreparedStatement stat=null;
       ResultSet res=null;
       
      try{
          
           conn.getConnection();
           sql+=this.getCrit().initCritere();
             System.out.println("Avant traitement:"+sql);
           stat=conn.prepareStatement(sql);
           res=stat.executeQuery();
            System.out.println("Apres traitement:"+stat);
           return this.extractResult(res);
       
      }finally{
          if(res!=null)res.close();
          if(stat!=null)stat.close();
          if(conn!=null)conn.close();
          this.setCrit(null);
      }
   }
    
     public boolean insertArray(BddObject[] intervalles,Connect conn) throws SQLException{
             
         conn.setuses(true);
         conn.setAutoCommit(false);
         for(BddObject inter:intervalles){
             try {
                 System.out.println(inter+"----");
                 if(!inter.save(conn)){
                   
                     conn.rollback();
                     return false;
                 }
             } catch (Exception ex) {
                 ex.printStackTrace();
                conn.rollback();
                return false;
             }
         }
         conn.commit();
         return true;
    }
     
   
   
    
   

   
}
