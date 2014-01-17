package com.frengky.onlinetrading.datafeed;

import java.net.URI;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.util.zip.GZIPInputStream;
import java.lang.Exception;
import com.frengky.onlinetrading.datafeed.DatafeedClient;
import org.apache.log4j.Logger;

public class DatafeedFileClient extends DatafeedClient implements IDatafeedClient {
    protected BufferedInputStream _stream;
    protected static final int BUFFSIZE = 4096;
    protected byte[] _buffer = new byte[BUFFSIZE];
    protected static Logger log = Logger.getLogger(DatafeedFileClient.class);
    protected boolean _isConnected = false;
    
    public DatafeedFileClient() {
    }
    
    public DatafeedFileClient(IDatafeed datafeed) {
        _datafeed = datafeed;
    }
    
    public void connect() {
    	_isConnected = false;
        log.info("Opening " + _location.toString() + " ...");
        
        _filename = _location.getPath();
        
        String ext = "";
        int pos = _filename.lastIndexOf('.');
        if(pos > 0) {
            ext = _filename.substring(pos + 1);
        } 
        
        try {
            if(ext.equalsIgnoreCase("gz")) {
                _stream = new BufferedInputStream(new GZIPInputStream(new FileInputStream(_filename)));
            } else {
                _stream = new BufferedInputStream(new FileInputStream(_filename));
            }
            
            _isConnected = true;
            
            int bRead = 0;
            do {
                bRead = _stream.read(_buffer, 0, _buffer.length);
                _datafeed.read(_buffer, 0, bRead);
            } while(bRead > 0);
            
            _stream.close();
            log.info("Closing " + _location.toString() + " ...");
        }
        catch(Exception ex) {
        	_isConnected = false;
            log.error(ex.getMessage());
        }
    }
    
    public void connect(URI location) {
        if(!location.getScheme().equalsIgnoreCase("file")) {
            log.error("Location not supported, not a file scheme");
            return;
        }
        
        _location = location;
        connect();
    }
    
    public void connect(String fileName) {
        try {
            _location = new URI("file:///" + fileName);
            connect();
        }
        catch(Exception ex) {
            log.error(ex.getMessage());
        }
    }
    
    public boolean isConnected() {
    	return _isConnected;
    }
    
    public void disconnect() {
        try {
            _stream.close();
        	_isConnected = false;
        }
        catch(Exception ex) {
            log.error(ex.getMessage());
        }        
    }
}
