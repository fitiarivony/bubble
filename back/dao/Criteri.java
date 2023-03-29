/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.dao;

import back.dao.critere.GroupBy;
import back.dao.critere.Minicriteri;
import back.dao.critere.OrderBy;
import back.dao.where.Filtre;
import java.util.ArrayList;

/**
 *
 * @author FITIA ARIVONY
 */
public class Criteri {
     ArrayList<Minicriteri>critere;
     ArrayList<Filtre>restrictions;
     Integer offset;
     Integer limit;

    
    public ArrayList<Filtre> getRestrictions() {
        if(restrictions==null)this.setRestrictions(new ArrayList<Filtre>());
        return restrictions;
    }

    public void setRestrictions(ArrayList<Filtre> restrictions) {
        this.restrictions = restrictions;
    }

     
    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
     
    public ArrayList<Minicriteri> getCritere() {
        if(critere==null)this.setCritere(new ArrayList<Minicriteri>());
        return critere;
    }

    public void setCritere(ArrayList<Minicriteri> critere) {
        this.critere = critere;
    }
    public void tri(Minicriteri crit){
        this.getCritere().add(crit);
    }
     public void where(Filtre rest){
        this.getRestrictions().add(rest);
    }
    public String initCritere() throws Exception{
         String val="";
         if(this.getCritere().isEmpty())return "";
         else{
             
             ArrayList<Minicriteri>orderBy=new ArrayList<>();
              ArrayList<Minicriteri>groupBy=new ArrayList<>();
              if(this.getCritere().isEmpty())return val;
              else{
                  for(Minicriteri critere:this.getCritere()){
                      if(critere instanceof OrderBy) orderBy.add(critere);
                      if(critere instanceof GroupBy)groupBy.add(critere);
                  }
                  GroupBy group=new GroupBy();
                  OrderBy order=new OrderBy();
                   
                val+=group.writeSQL(groupBy);
                val+=order.writeSQL(orderBy);
                 
              }
         }
         val+=this.initOffset()+this.initLimit();
         
         return val;
     }
    public String initOffset(){
        if(this.getOffset()!=null)return "  offset "+this.getOffset();
        return "";
    }
     public String initLimit(){
        if(this.getLimit()!=null)return "  limit "+this.getLimit();
        return "";
    }
     
    
}
