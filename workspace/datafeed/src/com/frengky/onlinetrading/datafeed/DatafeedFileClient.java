/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed;

import java.net.URI;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.util.zip.GZIPInputStream;
import java.lang.Exception;
import com.frengky.onlinetrading.datafeed.DatafeedClient;
import org.apache.log4j.Logger;

/**
 *
 * @author franky
 */
public class DatafeedFileClient extends DatafeedClient implements IDatafeedClient {
    protected BufferedInputStream _stream;
    protected static final int BUFFSIZE = 4096;
    protected byte[] _buffer = new byte[BUFFSIZE];
    protected static Logger log = Logger.getLogger(DatafeedFileClient.class);
    
    public DatafeedFileClient() {
    }
    
    public DatafeedFileClient(IDatafeed datafeed) {
        _datafeed = datafeed;
    }
    
    public void connect() {
        log.info("Connecting to " + _location.toString() + " ...");
        
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
            
            int bRead = 0;
            do {
                bRead = _stream.read(_buffer, 0, _buffer.length);
                _datafeed.read(_buffer, 0, bRead);
            } while(bRead > 0);
            
            log.info("End of feed reached ("+ _datafeed.getSequenceNumber() +")");
            _stream.close();
        }
        catch(Exception ex) {
            log.error(ex.getMessage());
        }
    }
    
    public void connect(URI location) {
        if(!location.getScheme().equalsIgnoreCase("file")) {
            log.error("Location scheme is not file");
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
    
    public void disconnect() {
        try {
            _stream.close();
        }
        catch(Exception ex) {
            log.error(ex.getMessage());
        }        
    }
}
