/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;

/**
 *
 * @author FITIA ARIVONY
 */
public class SwingSession {
    static HashMap<String,Object>attribut;

    public static HashMap<String, Object> getAttribut() {
        return attribut;
    }
    public static  void init(){
        System.out.println(SwingSession.getAttribut()+"-----");
        if(SwingSession.getAttribut()==null)SwingSession.setAttribut(new HashMap<String,Object>());
    }

    public static void setAttribut(HashMap<String, Object> attribut) {
        SwingSession.attribut = attribut;
    }
    public static Object getValue(String key){
        return SwingSession.getAttribut().get(key);
    }
    
    public static void addAttribut(String key,Object value){
        SwingSession.getAttribut().put(key, value);
    }
    
}
