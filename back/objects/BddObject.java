/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.objects;

import back.annotations.Expiration;
import back.annotations.InitToken;
import back.annotations.Mdp;
import back.annotations.Token;
import back.dao.GenericDAO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import exception.LoginException;
import helper.Hashing;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import utils.Connect;


/**
 *
 * @author FITIA ARIVONY
 */
public class BddObject extends GenericDAO{        
    public void login(Connect conn) throws Exception{
        try{
        conn.setuses(true);
        this.hashingMdp();
        Object[] o=this.getByIds(conn);
        if(o.length==0)throw new LoginException("Identifiant ou mot de passe errone");
        BddObject obj=(BddObject)o[0];
        this.set(this.getPrimary_key(),obj.get(obj.getPrimary_key()));
        setInfo(conn);
        }finally{
            conn.forceClose();
        }
       
    }
    public void hashingMdp() throws Exception{
        Field f=this.getMdpAnnotation();
        if(f!=null){
            
             Mdp hashage=f.getAnnotation(Mdp.class);
        String mdp=(String)this.get(f);
        this.set(this.getMdpAnnotation(),Hashing.getHashing(hashage.hashing(), mdp));
        }
    }
    @JsonIgnore
    public Field getMdpAnnotation(){
          for(Field f:this.getClass().getDeclaredFields()){
              
            Mdp tok=f.getAnnotation(Mdp.class);
          
            
            if(tok!=null)return f;
        }
        return null;
    }
    
    public void initExpiration() throws IllegalArgumentException, IllegalAccessException{
        Timestamp tmp=new Timestamp(System.currentTimeMillis());
        tmp.setMinutes(tmp.getMinutes()+15);
        this.set(this.getExpirationField(),tmp);
    }
    
    public void setInfo(Connect conn) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception{
        Method meth=this.getInitToken();
        if(meth!=null){
            InitToken hashage=meth.getAnnotation(InitToken.class);
            String token=(String)meth.invoke(this,null);
            token=Hashing.getHashing(hashage.hashing(), token);
            Field f=this.getTokenAnnotation();
            this.set(f, token);
            this.initExpiration();
            System.out.println(this); 
            this.update(conn);
        }
    }
   @JsonIgnore
    public Field getExpirationField(){
        for(Field f:this.getClass().getDeclaredFields()){
            Expiration tok=f.getAnnotation(Expiration.class);
            if(tok!=null)return f;
        }
        return null;
    }
    @JsonIgnore
    public Method getInitToken(){
        for(Method o:this.getClass().getMethods()){
            InitToken token=o.getAnnotation(InitToken.class);
            if(token!=null)return o;
        }
        return null;
    }
    @JsonIgnore
    public Field getTokenAnnotation(){
        for(Field f:this.getClass().getDeclaredFields()){
            Token tok=f.getAnnotation(Token.class);
            if(tok!=null)return f;
        }
        return null;
    }
        
}
