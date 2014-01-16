/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed.app;

import java.net.URI;
import java.util.Properties;
import java.io.FileInputStream;
import com.frengky.onlinetrading.datafeed.*;
import org.apache.log4j.Logger;


/**
 *
 * @author franky
 */
public class Client {
    private static Logger log = Logger.getLogger(Client.class);
    
    public Client() {
    }
    
    public static void main(String[] args) {
        Properties prop = new Properties();
        
        try {
            String config = System.getProperty("config");
            log.info("Using configuration " + config);
            prop.load(new FileInputStream(config));
            
            IDatafeedProvider provider = DatafeedProvider.forName("IDX");
            
            IDatafeedClient client = provider.getClient(
                    new URI(prop.getProperty("datafeed.location")));
            
            client.setUsername(prop.getProperty("datafeed.user"));
            client.setPassword(prop.getProperty("datafeed.password"));
            client.setOption("1");
            
            client.connect();
        }
        catch(Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
