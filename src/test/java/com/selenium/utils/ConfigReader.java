package com.selenium.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private  Properties properties;

   public ConfigReader(String propFile) {
        properties = new Properties();

        try {
            FileInputStream file =
                    new FileInputStream(
                        "src/test/resources/"+propFile+".properties");

            properties.load(file);
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    
   }
    public  String getProperty(String key) {
        return properties.getProperty(key);
    }
    
}