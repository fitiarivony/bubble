/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import exception.ConnexionException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author FITIA ARIVONY
 */
public class ConnectionConfiguration {
    String url;
    String mdp;
    String username;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public File getConf() throws  ConnexionException{
        
        try {
            File file=new File("conf.xml");
            if(file.exists())return file;
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            
            assert classLoader != null;
            Enumeration<URL> resources = classLoader.getResources("");
            List<File> dirs = new ArrayList<>();
            while (resources.hasMoreElements()) {
                
                URL resource = resources.nextElement();
                String nomfichier=resource.getFile();
                if(nomfichier.contains("WEB-INF")){     
                    String path=nomfichier.subSequence(0,nomfichier.indexOf("WEB-INF")).toString().replace("/build/web","");
                    path+="conf.xml";
                    return new File(path);
                      
                }
            }
            
            
            throw new ConnexionException("configuration not found");
        } catch (IOException ex) {
            throw new ConnexionException("configuration not found");
        }
        
        
    }
   public ConnectionConfiguration() throws ConnexionException{
        try {
            File file=this.getConf();
            
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            //an instance of  builer to parse the specified xml
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(file);
            doc.getDocumentElement().normalize();
            
            NodeList nodeList=doc.getElementsByTagName("connection");
            //nodeList is not iterable so we are using for loop
            for(int i=0;i<nodeList.getLength();i++){
                Node node=nodeList.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element eElement=(Element)node;
                    this.setMdp(eElement.getElementsByTagName("password").item(0).getTextContent());
                    this.setUrl(eElement.getElementsByTagName("url").item(0).getTextContent());
                    this.setUsername(eElement.getElementsByTagName("username").item(0).getTextContent());
                }
            }
        } catch (SAXException ex) {
            throw new ConnexionException("configuration not found");
        } catch (IOException ex) {
           throw new ConnexionException("configuration not found");
        } catch (ParserConfigurationException ex) {
            throw new ConnexionException("configuration not found");
        }
   }
}
