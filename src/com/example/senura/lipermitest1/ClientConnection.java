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
public class ClientConnection {
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
         CallHandler callHandler = new CallHandler();
        String remoteHost = "192.168.1.3";
        int portWasBinded = 58882;

        Client client = new Client(remoteHost, portWasBinded, callHandler);
        
        
        TestService remoteObject;
        remoteObject =
                (TestService) client.getGlobal(TestService.class);
        
        System.out.println(remoteObject.getResponse("Hey babe.."));
      
    }
    
    
    
    
    
    
    
}
