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
public class Like extends Valeur{
    
    public Like(Object max) {
        super("%"+max+"%");
    }
    
}
