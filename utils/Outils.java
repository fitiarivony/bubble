package utils;
import back.objects.BddObject;
import java.lang.*;
import java.util.Vector;
import java.awt.Point;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
public class Outils{
    public static int[] returnIntArray(Vector intArray)
    {
        int[]array=new int[intArray.size()];
        for(int i=0;i<intArray.size();i++)
        {
            array[i]=(int)intArray.elementAt(i);
        }
        return array;
    }
   public static String[] returnStringArray(Vector stringArray)
   {
       String[]table=new String[stringArray.size()];
       for(int i=0;i<stringArray.size();i++)
       {
           table[i]=(String)stringArray.elementAt(i);
       }
       return table;
   }
   
    public static String[] ObjectToStringArray(Object[] stringArray)
   {
       String[]table=new String[stringArray.length];
       for(int i=0;i<stringArray.length;i++)
       {
           table[i]=(String)stringArray[i];
       }
       return table;
   }
   
   public static Field[] returnFieldArray(ArrayList<Field> fieldArray)
   {
       Field[]table=new Field[fieldArray.size()];
       for(int i=0;i<fieldArray.size();i++)
       {
           table[i]=fieldArray.get(i);
       }
       return table;
   }
   
      public static BddObject[] returnBddArray(ArrayList<Object> BddArray)
   {
       BddObject[]table=new BddObject[BddArray.size()];
       for(int i=0;i<BddArray.size();i++)
       {
           table[i]=(BddObject)BddArray.get(i);
       }
       return table;
   }
      
     
           
              public static Method[] returnMethodArray(Object[] BddArray)
   {
       Method[]table=new Method[BddArray.length];
       for(int i=0;i<BddArray.length;i++)
       {
           table[i]=(Method)BddArray[i];
       }
       return table;
   }
                public static Double heure_diff(Date fin,Date debut){
            long diffTime=Math.abs(fin.getTime()-debut.getTime());
            long diff=TimeUnit.MILLISECONDS.convert(diffTime, TimeUnit.MILLISECONDS);
             double divided=1000d*3600d;
           
            return diff/divided;
     }
   
    
     

   
}