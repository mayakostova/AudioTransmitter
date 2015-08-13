/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odonataworkshop.util;

/**
 *
 * @author maya
 */
public enum MessageType {
    INFO_MESSAGE("Info","info.png"),
    ERROR_MESSAGE("Error","error.png"),
    WARNING_MESSAGE("Warning","warning.png");
    
    
    private String mTitle;
    private String mImage;
    
    private MessageType(String aTitle, String aImage){
        mTitle = aTitle;
        mImage = aImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImage() {
        return mImage;
    }
    
}
