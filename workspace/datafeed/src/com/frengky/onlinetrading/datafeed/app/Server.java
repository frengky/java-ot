package com.frengky.onlinetrading.datafeed.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.frengky.onlinetrading.datafeed.DatafeedProvider;
import com.frengky.onlinetrading.datafeed.IDatafeedClient;
import com.frengky.onlinetrading.datafeed.IDatafeedProvider;
import com.frengky.onlinetrading.datafeed.idx.IDXDatafeedTcpServer;

public class Server {
	private static Logger log = Logger.getLogger(Server.class);
	
	public Server() {
	}
	
	public static void main(String[] args) {
		String listen = null;
		String port = null;
		
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
	    		listen = prop.getProperty("datafeed.listen");
	    		port = prop.getProperty("datafeed.port");
	    		
	    		location = prop.getProperty("datafeed.location");
	    		user = prop.getProperty("datafeed.user");
	    		password = prop.getProperty("datafeed.password");
	    		option = prop.getProperty("datafeed.option");
	    		
	    		if(!listen.isEmpty() && !port.isEmpty() && !location.isEmpty() && !user.isEmpty()) {
	    			if(password.isEmpty()) {
	    				log.warn("Using empty password?");
	    				password = "";
	    			}
	    			configExist = true;
	    		}
    		} catch(IOException ex) {
    			log.error(ex.getMessage());
    		}
    	} else {
    		listen = System.getProperty("datafeed.listen");
    		port = System.getProperty("datafeed.port");
    		    		
    		location = System.getProperty("datafeed.location");
    		user = System.getProperty("datafeed.user");
    		password = System.getProperty("datafeed.password");
    		option = System.getProperty("datafeed.option");
    		
    		if(!listen.isEmpty() && !port.isEmpty() && !location.isEmpty() && !user.isEmpty()) {
    			if(password.isEmpty()) {
    				log.warn("Using empty password?");
    				password = "";
    			}
    			log.info("Using config from parameters.");
    			configExist = true;
    		}
    	}
    	
    	if(!configExist) {
    		log.error("Configuration incomplete, aborting.");
    		return;
    	}
    	
		IDXDatafeedTcpServer server = new IDXDatafeedTcpServer();		
		try {			
			server.listen(listen, Integer.parseInt(port));
		} catch(Exception ex) {
			log.error(ex.getMessage());
		}
		
		URI clientUri;
		try {
			clientUri = new URI(location);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			server.stop();
			return;
		}
		
		IDatafeedProvider provider = DatafeedProvider.forName("IDX");
		IDatafeedClient client = provider.getClient(clientUri);
		client.setUsername(user);
		client.setPassword(password);
		client.setOption(option);
		
		client.getDatafeed().addDatafeedListener(server);
		
		log.debug("Connecting in 10 seconds...");

		try {
			Thread.sleep(10000);
			client.connect();
		} catch(Exception ex) {
			log.error(ex.getMessage());
			client.getDatafeed().removeDatafeedListener(server);
			server.stop();
			return;			
		}
	}
}
