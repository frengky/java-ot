package com.frengky.onlinetrading.datafeed;

import java.net.URI;

public interface IDatafeedClient {
    
    URI getLocation();
    void setLocation(URI location);
    IDatafeed getDatafeed();
    void setDatafeed(IDatafeed datafeed);
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    String getOption();
    void setOption(String option);
    
    public void connect();
    public void connect(URI location);
    public void disconnect();
    public void dispose();
}
