/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.senura.lipermitest1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

/**
 *
 * @author senura
 */
public class ClientConnection {
    public static void main(String[] args) throws IOException {
        
        
        
         CallHandler callHandler = new CallHandler();
        String remoteHost = "192.168.1.4";
        int portWasBinded = 58882;

        Client client = new Client(remoteHost, portWasBinded, callHandler);
        
        
        TestService remoteObject;
        remoteObject =
                (TestService) client.getGlobal(TestService.class);
        
        System.out.println(remoteObject.getResponse("Hey babe.."));
        
        if(remoteObject.isHavingHostURL()) {
            String sampleUrl=remoteObject.getURL();
            System.out.println(sampleUrl);
            ClientConnection.sendGet(sampleUrl);
        }
        else
            System.out.println("Not having a URL");
      
    }
    
    
    
    
      private static String getHTML(String urlToRead) throws Exception {
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
      
      
      public static void sendGet(String urlToRead){
        
        try {
            urlToRead=ClientConnection.getHTML(urlToRead);
        } catch (Exception ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(urlToRead);
      }
    
    
    
    
}
