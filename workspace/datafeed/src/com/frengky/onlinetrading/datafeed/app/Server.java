package com.frengky.onlinetrading.datafeed.app;

import org.apache.log4j.Logger;
import com.frengky.onlinetrading.datafeed.idx.IDXDatafeedTcpServer;

public class Server {
	private static Logger log = Logger.getLogger(Server.class);
	
	public Server() {
	}
	
	public static void main(String[] args) {
		log.info("Starting Server");
		
		try {
			IDXDatafeedTcpServer server = new IDXDatafeedTcpServer();
			server.listen();
		} catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
}
