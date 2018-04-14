/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.senura.lipermitest1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author senura
 */
public class GETRequestHandlers {
    
    
    
    public static void sendGet(String urlToRead){
        
       
        
                try {
               urlToRead=getHTML(urlToRead);
           } catch (Exception ex) {
               Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
           }
             
            System.out.println(urlToRead);
    }
    
    
    
       public static boolean sendGetsToCalculateTimeElapsed(String urlToRead){
       
           System.out.println("ClientConnection.isgetCountCalculated >> "+ClientConnection.isgetCountCalculated);
           System.out.println("getcount >> "+ClientConnection.getCount);
           System.out.println("\n\n");
           
        if(!ClientConnection.isgetCountCalculated){
            
               try {
               urlToRead=getHTML(urlToRead);
           } catch (Exception ex) {
               Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
           }

           ClientConnection.getCount++;
           
           long timeElapsed=System.currentTimeMillis()-ClientConnection.startMilisecs;
           
           //measuring for 10secs
           if((timeElapsed/1000)>10 && !ClientConnection.isgetCountCalculated){
               ClientConnection.isgetCountCalculated=true;
               System.out.println("get count = "+ClientConnection.getCount+" gets per 10secs");
               
               ClientControllerCon.setControllerVar(ClientConnection.getCount, getIpAddressOfThis(),getHostNameOfThis());////////newnewnew
               
               //re init
               ClientConnection.getCount=0; ClientConnection.startMilisecs=0;
               
               ClientControllerCon.start();
               
               return true;//10 secs gone
           }
           
          
        }
        
        return false;
      }
       
       
       
       
       static String getHTML(String urlToRead) throws Exception {
      StringBuilder result = new StringBuilder();
      URL url = new URL(urlToRead);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
         result.append(line);
      }
      rd.close();
      return result.toString();
   }
       
      private static String getIpAddressOfThis(){
      
          String ipAdrs="unknown";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ipAdrs=inetAddress.getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(GETRequestHandlers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ipAdrs; 
      } 
      
      
       private static String getHostNameOfThis(){
      
          String hostName="unknown";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostName=inetAddress.getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(GETRequestHandlers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostName; 
      }
    
    
       
    
}
