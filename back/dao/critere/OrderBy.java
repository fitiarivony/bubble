/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.dao.critere;

import java.util.ArrayList;

/**
 *
 * @author FITIA ARIVONY
 */
public class OrderBy extends Minicriteri{
    String order;

    public String getOrder() {
        if(order==null)this.setOrder("asc");
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    
    @Override
    public String getName() {
       if(name==null)this.setName("  order by");
       return name;
    }
    public OrderBy(String attribut,String order){
        this.setAttribut(attribut);
        this.setOrder(order);
    }
     public OrderBy(String attribut){
        this.setAttribut(attribut);
    }
     public OrderBy(){
         
     }
  @Override
    public String writeSQL(ArrayList<Minicriteri> crit)throws Exception{
        String val="";
       
        if(crit.isEmpty())return val;
        else{
            
            val+=this.getName()+"  ";
            for(int i=0;i<crit.size();i++){
               
                if(i==crit.size()-1)val+=(crit.get(i)).getAttribut()+"  "+((OrderBy)crit.get(i)).getOrder();
                else val+=(crit.get(i)).getAttribut()+"  "+((OrderBy)crit.get(i)).getOrder()+",";
            }
        }
       
        return val;
    }
    
    
    
    
}
