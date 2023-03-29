/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.dao.where;

/**
 *
 * @author FITIA ARIVONY
 */
public enum GenCase {
    equal,eqless,less,eqgreat,great,interval,like,between;
    
    public static String getQuery(GenCase cas,String attribut){
        
         if(cas==null)return attribut+"= ?";
        switch(cas){
            case equal:
                return attribut+"=?";     
            case eqless:
                return attribut+"<=?";
            case less:
                return attribut+"<?";
            case eqgreat:
                return attribut+">=?";
            case great:
                return attribut+">?";
            case like:
                return attribut+" like ?";
            case between:
                return attribut+">? and "+attribut+" <?";
            case interval:
                return attribut+">=? and "+attribut+" <=?";
        }
        return attribut+"=?";
    }
   
    
}
