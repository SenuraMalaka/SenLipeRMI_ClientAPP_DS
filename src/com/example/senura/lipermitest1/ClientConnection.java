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
