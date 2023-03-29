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
public class GroupBy extends Minicriteri{

    @Override
    public String getName() {
        if(name==null)this.setName("group by");
        return name;
    }

    @Override
    public String writeSQL(ArrayList<Minicriteri> crit) throws Exception {
         String val="";
        if(crit.isEmpty())return val;
        else{
            System.out.println("hereeee");
            val+=this.getName()+"  ";
            for(int i=0;i<crit.size();i++){
                if(i==crit.size()-1)val+=(crit.get(i)).getAttribut()+"  ";
                else val+=(crit.get(i)).getAttribut()+",";
            }
        }
        return val;
    }

    
    
}
