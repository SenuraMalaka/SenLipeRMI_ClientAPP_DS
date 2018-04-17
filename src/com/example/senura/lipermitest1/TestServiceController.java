/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.senura.lipermitest1;

/**
 *
 * @author senura
 */
public interface TestServiceController {
    public String getResponse(String data);
    public void setDDOSCount(int count, String ipAddress, String hostName);
    public String getClientInfo();
    public int getNumofDDOSToBeExcecuted(String ipAddress);
    public void setProposedDDOSCount(int count);
    public boolean isProposedDDOSCountSet();
    public void sendGetDone(int count, String ipAddress, String hostName);
    public String getRewardsStatusText();
   
}
