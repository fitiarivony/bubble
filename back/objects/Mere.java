/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.objects;

import exception.MappingException;
import java.sql.SQLException;
import utils.Connect;

/**
 *
 * @author FITIA ARIVONY
 */
public abstract  class Mere extends BddObject{
    Fille[]filles;
    Object id;
    
    public void setId(Object id){
        this.id=id;
    }
    public abstract  void setId();
    public  Object getId(){
        return this.id;
    }
    
    public Fille[] getFilles() {
        return filles;
    }
     public abstract void initFilles();

    public void setFilles(Fille[] fille) {
        this.filles = fille;
        for(int i=0;i<this.getFilles().length;i++){
            this.getFilles()[i].setMere(this);
        }
    }
    
    public void addFille(Fille fille){
        fille.setMere(this);
        Fille[]newfilles=new Fille[getFilles().length+1];
        for(int i=0;i<getFilles().length;i++){
            newfilles[i]=getFilles()[i];
        }
        newfilles[newfilles.length-1]=fille;
        setFilles(newfilles); 
    }

    
    public boolean makeinsert(Connect conn) throws SQLException, Exception {
        
         this.save( conn); //To change body of generated methods, choose Tools | Templates.
         try{
             String id=this.getLastId( conn).toString();
         this.setId(id);
         for(int i=0;i<getFilles().length;i++){
             this.getFilles()[i].setForeign(id);
             if(!this.getFilles()[i].save( conn))return false;
         }
         return true;  
         }catch(Exception e){   
             e.printStackTrace();
             return false;
         }       
    }
    
    
    
    @Override
    public boolean save(Connect conn)throws SQLException,Exception{
        try{
        
        conn.getConnection();
        conn.setuses(true);
        conn.setAutoCommit(false);
         if(makeinsert(conn)){
            conn.commit();
         
            return true;
        }
            conn.rollback();
            
            return false;
        }catch(Exception e){
            conn.rollback();
            
            throw e;
        }finally{
            conn.forceClose();
        }
 
    }

    @Override
    public boolean delete(Connect conn)  throws SQLException, MappingException,Exception{
        conn.setuses(true);
        conn.setAutoCommit(false);
       conn.getConnection();
        try{
              if(makedelete(conn) && super.delete(conn)){
            conn.commit();
            return true;
        }
        conn.rollback();
        return false;
        }finally{
            conn.forceClose();
        }
      
    }
    public boolean makedelete(Connect conn)throws Exception{
        this.initFilles();
        for(int i=0;i<this.getFilles().length;i++){
         
            if(!this.getFilles()[i].delete(conn))return false;
        }
        
        return true;
    }
    
    
}
