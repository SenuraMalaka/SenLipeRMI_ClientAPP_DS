/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.senura.lipermitest1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author senura
 */
public class GETRequestHandlers {
    
    
    
    public static void sendGet(String urlToRead){
        
       
        
                try {
               urlToRead=ClientConnection.getHTML(urlToRead);
           } catch (Exception ex) {
               Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
           }
             
            System.out.println(urlToRead);
    }
    
    
    
       public static String sendGetsToCalculateTimeElapsed(String urlToRead){
       
        if(!ClientConnection.isgetCountCalculated){
            
               try {
               urlToRead=ClientConnection.getHTML(urlToRead);
           } catch (Exception ex) {
               Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
           }

           ClientConnection.getCount++;
           
           long timeElapsed=System.currentTimeMillis()-ClientConnection.startMilisecs;
           
           //measuring for 10secs
           if((timeElapsed/1000)>=10){
               ClientConnection.isgetCountCalculated=true;
               System.out.println("get count = "+ClientConnection.getCount+" gets per 10secs");
               
               //re init
               ClientConnection.getCount=0; ClientConnection.startMilisecs=0;
               
               return "10Secs gone";
           }
           
           return "still counting";
        }
        
        return "nothing";
      }
    
    
       
    
}
