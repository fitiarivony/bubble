/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import exception.HashingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author FITIA ARIVONY
 */
public class Hashing {
    
     public static String  sha1(String message)throws Exception{
    MessageDigest digest = MessageDigest.getInstance("SHA-1");
     digest.reset();
     digest.update(message.getBytes("utf8"));
 return (String.format("%040x", new BigInteger(1, digest.digest())));
    }
    public static String md5(String input)  
{  
try   
{  
//static getInstance() method is called with hashing MD5  
MessageDigest md = MessageDigest.getInstance("MD5");  
//calculating message digest of an input that return array of byte  
byte[] messageDigest = md.digest(input.getBytes());  
//converting byte array into signum representation  
BigInteger no = new BigInteger(1, messageDigest);  
//converting message digest into hex value  
String hashtext = no.toString(16);  
while (hashtext.length() < 32)   
{  
hashtext = "0" + hashtext;  
}  
return hashtext;  
}  
//for specifying wrong message digest algorithms  
catch (NoSuchAlgorithmException e)   
{  
throw new RuntimeException(e);  
}  
}
    public static String getHashing(String meth,String value) throws Exception{
        if(meth.equalsIgnoreCase("md5"))return md5(value);
        if(meth.equalsIgnoreCase("sha1"))return sha1(value);
        throw new HashingException("No form of "+meth+" hashing");
    }
}
