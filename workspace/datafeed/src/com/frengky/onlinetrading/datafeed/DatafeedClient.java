/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed;

import java.net.URI;
/**
 *
 * @author franky
 */
public abstract class DatafeedClient {
    protected String _username;
    protected String _password;
    protected String _option = "1";
    protected String _filename;
    protected URI _location;
    protected IDatafeed _datafeed;
    
    public IDatafeed getDatafeed() {
        return _datafeed;
    }
    
    public void setDatafeed(IDatafeed datafeed) {
        _datafeed = datafeed;
    }    
    
    public URI getLocation() {
        return _location;
    }
    
    public void setLocation(URI location) {
        _location = location;
    }
    
    public String getUsername() {
        return _username;
    }
    
    public void setUsername(String username) {
        _username = username;
    }
    
    public String getPassword() {
        return _password;
    }
    
    public void setPassword(String password) {
        _password = password;
    }
    
    public String getOption() {
        return _option;
    }
    
    public void setOption(String option) {
        _option = option;
    }    
    
    public void dispose() {
    }
}
