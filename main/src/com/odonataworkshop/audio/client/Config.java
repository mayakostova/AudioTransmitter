package com.odonataworkshop.audio.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by maya on 27.1.2015 г..
 */
public class Config{
    public static final String HOST_PROPERTY_NAME = "audio.transmitter.host";
    public static final String PORT_PROPERTY_NAME = "audio.transmitter.port";

    private static Properties properties;
    private static String file;

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties aProperties) {
        properties = aProperties;
    }

    public static void setFile(String aFile) {
        file = aFile;
    }

    public static String getProperty(String message){
        return properties.getProperty(message);
    }

    public static void setProperty(String message, String value){
        properties.setProperty(message,value);
    }

    public static void saveProperties() {
        try {
            properties.store(new FileOutputStream(file),"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
