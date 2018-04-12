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
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;
import org.w3c.dom.css.Counter;

/**
 *
 * @author senura
 */
public class ClientConnection {
    
    private static TestService remoteObject=null;
    private static boolean isServerCameOnline=false;
    
    //to calculate the gets per sec
    static long startMilisecs=0;
    static int getCount=0;
    static boolean isgetCountCalculated=false;
    
    
    public static void main(String[] args) {   
        setTimerToCheckServerAvailability();
    }
    
    
    private static void setClient(){
        
        CallHandler callHandler = new CallHandler();
        String remoteHost = "192.168.1.4";
        int portWasBinded = 58882;
        
        Client client=null;
       
        try {
            client = new Client(remoteHost, portWasBinded, callHandler);
            isServerCameOnline=true;
        } catch (IOException ex) {
            System.out.println("server is not online");
        }
        
        
        if(client!=null){
        remoteObject =
                (TestService) client.getGlobal(TestService.class);
        
        System.out.println(remoteObject.getResponse("Hey babe.."));
        
            setTimerToCheckURLAvailability();

        }
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
      
      
//      public static String sendGet(String urlToRead, boolean countTime){
//        
//       
//        if(!countTime)
//        {
//                try {
//               urlToRead=ClientConnection.getHTML(urlToRead);
//           } catch (Exception ex) {
//               Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
//           }
//             
//            System.out.println(urlToRead);
//        }
//        else if(!isgetCountCalculated){
//            
//               try {
//               urlToRead=ClientConnection.getHTML(urlToRead);
//           } catch (Exception ex) {
//               Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
//           }
//
//           getCount++;
//           
//           long timeElapsed=System.currentTimeMillis()-startMilisecs;
//           
//           //measuring for 10secs
//           if((timeElapsed/1000)>=10){
//               isgetCountCalculated=true;
//               System.out.println("get count = "+getCount+" gets per 10secs");
//               
//               //re init
//               getCount=0; startMilisecs=0;
//               
//               return "10Secs gone";
//           }
//           
//           return "still counting";
//        }
//        
//        return "nothing";
//      }
      
      
      private static void checkURLAvailable(){
      
          try{
          
          if(remoteObject.isHavingHostURL()) {
              
            if(!isgetCountCalculated)countNumOfConPerSec();///////
              
            String sampleUrl=remoteObject.getURL();
            System.out.println(sampleUrl);
            GETRequestHandlers.sendGet(sampleUrl);
        }
        else
            System.out.println("Not having a URL");
          }catch(java.lang.reflect.UndeclaredThrowableException ex){
              System.out.println("Server gone Offline");
              setClient();
          }
          
      }
      
      
      
      //Timers
      private static void setTimerToCheckURLAvailability(){
          Timer timer = new Timer();
          TimerTask myTask = new TimerTask() {
              @Override
              public void run() {
                  checkURLAvailable();
                 
              }
          };

          timer.schedule(myTask, 2000, 2000);
      }
      
      
      private static void setTimerToCheckServerAvailability(){
          Timer timer = new Timer();
          TimerTask myTask = new TimerTask() {
              @Override
              public void run() {
                  setClient();
                  if(isServerCameOnline) timer.cancel();
              }
          };

          timer.schedule(myTask, 2000, 2000);
      }
      
      
      private static void setTimerToCountGets(String sampleUrl){
          Timer timer = new Timer();
          TimerTask myTask = new TimerTask() {
              @Override
              public void run() {
                  
                  if(GETRequestHandlers.sendGetsToCalculateTimeElapsed(sampleUrl).equals("10Secs gone")){
                     
                  timer.cancel();
                  }
              }
          };

          timer.schedule(myTask, 2000, 2000);
      }
      
      
      private static void countNumOfConPerSec(){
         
          if(startMilisecs==0)
          startMilisecs= System.currentTimeMillis();
         
         if(remoteObject.isHavingHostURL()) {
            String sampleUrl=remoteObject.getURL();
            
            
         setTimerToCountGets(sampleUrl);
         }
         
      }
      
      
      
      
    
    
    
    
}
