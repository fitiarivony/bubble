/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bubble;

import back.annotations.InitToken;
import back.annotations.Token;

/**
 *
 * @author FITIA ARIVONY
 */
public class Utilisateur {
    String nom;
    String prenom;
   
    String identifiant;
    
    String mdp;
    @Token
    String token;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    @InitToken(hashing="sha1")
    public String initToken(){
        return this.getIdentifiant()+this.getMdp();
    }
    
}
