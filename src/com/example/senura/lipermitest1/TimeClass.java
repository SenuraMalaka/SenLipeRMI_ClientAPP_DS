/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.senura.lipermitest1;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author senura
 */
public class TimeClass {
    
    private static Calendar cal =null;
    
    
    public static String getTime(){
    cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }
    
}
