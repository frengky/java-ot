package com.frengky.onlinetrading.datafeed.app;

import java.net.URI;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

import com.frengky.onlinetrading.datafeed.*;
import com.frengky.onlinetrading.datafeed.idx.IDXDatafeed;
import com.frengky.onlinetrading.datafeed.idx.IDXDatafeedListener;
import com.frengky.onlinetrading.datafeed.idx.IDXDatafeedTradingStatusEvent;

import org.apache.log4j.Logger;

public class Client {
    private static Logger log = Logger.getLogger(Client.class);
    
    public Client() {
    }
        
    public static void main(String[] args) {
    	String location = null;
    	String user = null;
    	String password = null;
    	String option = null;
    	
    	boolean configExist = false;

    	String config = System.getProperty("config");
    	if(!config.isEmpty()) {
    		log.info("Using config from file: " + config);
    		try {
	    		Properties prop = new Properties();
	    		prop.load(new FileInputStream(config));
	    		location = prop.getProperty("datafeed.location");
	    		user = prop.getProperty("datafeed.user");
	    		password = prop.getProperty("datafeed.password");
	    		option = prop.getProperty("datafeed.option");
	    		
    			if(password.isEmpty()) {
    				log.warn("Using empty password?");
    				password = "";
    			}
    			configExist = true;
    		} catch(IOException ex) {
    			log.error(ex.getMessage());
    		}
    	} else {
    		location = System.getProperty("datafeed.location");
    		user = System.getProperty("datafeed.user");
    		password = System.getProperty("datafeed.password");
    		option = System.getProperty("datafeed.option");
    		
    		if(!location.isEmpty() && !user.isEmpty()) {
    			if(password.isEmpty()) {
    				log.warn("Using empty password?");
    				password = "";
    			}
    			log.info("Using config from parameters.");
    			configExist = true;
    		}
    	}
    	
        if(configExist) {
            try {            
                IDatafeedProvider provider = DatafeedProvider.forName("IDX");
                
                IDatafeedClient client = provider.getClient(new URI(location));
                client.setUsername(user);
                client.setPassword(password);
                client.setOption(option);
                
                if(option.isEmpty()) {
                	log.warn("Datafeed option is not supplied, using 1");
                	option = "1";
                }
                
                DatafeedDebugger debug = new DatafeedDebugger();
                // client.getDatafeed().addStreamListener(debug);
                ((IDXDatafeed)client.getDatafeed()).addStreamListener(debug);
                ((IDXDatafeed)client.getDatafeed()).addDatafeedListener(debug);
                
                client.connect();
            }
            catch(Exception ex) {
                log.error(ex.getMessage());
            }
        } else {
        	log.error("No configuration found!");
        }
    }
}

class DatafeedDebugger implements IDXDatafeedListener {
	private static Logger log = Logger.getLogger(DatafeedDebugger.class);
	
	public DatafeedDebugger() {
	}
	
	public void onDatafeedStream(DatafeedStreamEvent e) {
		try {
			System.out.write(e.getBytes());
		} catch(Exception ex) {
			log.error(ex.getMessage());
		}
    }
	
	public void onTradingStatusChanged(IDXDatafeedTradingStatusEvent e) {
		log.info("Trading status was changed");
		try {
			System.out.write(e.getTradingStatus());
		} catch(Exception ex) {
			log.error(ex.getMessage());
		}		
	}
}
