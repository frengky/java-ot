/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed;

import com.frengky.onlinetrading.datafeed.IDatafeed;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/**
 *
 * @author franky
 */
public class Datafeed implements IDatafeed {
    protected int _sequenceNumber = 0;
    protected boolean _endOfFeed = false;
    protected ArrayList<byte[]> _elements = new ArrayList<byte[]>(50);
    protected static final int BUFFSIZE = 4096;
    protected ByteBuffer _buffer = ByteBuffer.allocateDirect(BUFFSIZE);
    protected static Logger log = Logger.getLogger(Datafeed.class);
    
    public Datafeed() {
    }
    
    public String getVersion() {
        return "Datafeed 1.0";
    }
    
    public int getSequenceNumber() {
        return _sequenceNumber;
    }
    
    protected void handleElements(ArrayList<byte[]> elements) {
    }
    
    public void read(byte[] buffer, int offset, int length) {
        if(length < 1) {
            _endOfFeed = true;
            return;
        }
        
        byte[] element;
        
        for(int i=offset; i<length; i++) {
            _buffer.put(buffer[i]);
            if(buffer[i] == 0x7C) { // Found pipe
                element = new byte[_buffer.position() - 1];
                _buffer.position(0);
                _buffer.get(element, 0, element.length);
                _buffer.position(0);
                
                _elements.add(element);
            }
            else if(buffer[i] == 0x0A) { // Found line break
                element = new byte[_buffer.position()];
                _buffer.position(0);
                _buffer.get(element, 0, element.length);
                _buffer.position(0);
                
                _elements.add(element);
                
                if(_elements.size() > 4) {
                    _sequenceNumber++;
                    handleElements(_elements);
                } else {
                    for(int x=0; x<_elements.size(); x++) {
                        log.info((new String(_elements.get(x))).trim());
                    }
                }
                _elements.clear();
            }
        }
    }
    
    public void reset() {
        _sequenceNumber = 0;
        _endOfFeed = false;
        _elements.clear();
    }
    
    public boolean endOfFeed() {
        return _endOfFeed;
    }    
}
