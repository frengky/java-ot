package com.frengky.onlinetrading.datafeed.idx;

import com.frengky.onlinetrading.datafeed.IDatafeed;
import com.frengky.onlinetrading.datafeed.IDatafeedClient;
import com.frengky.onlinetrading.datafeed.IDatafeedProvider;
import java.net.URI;

public class IDXDatafeedProvider implements IDatafeedProvider {
    
    public IDXDatafeedProvider() {
    }
    
    public IDatafeed getDatafeed() {
        return new IDXDatafeed();
    }
    
    public IDatafeedClient getClient(URI location) {
        String scheme = location.getScheme();
        IDatafeedClient client;
        
        if(scheme.equalsIgnoreCase("file")) {
            client = new IDXDatafeedFileClient();
            client.setLocation(location);
            return client;            
        } else {
            client = new IDXDatafeedTcpClient();
            client.setLocation(location);
            return client;        
        }
    }    
}
