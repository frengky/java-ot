package com.frengky.onlinetrading.datafeed;

import com.frengky.onlinetrading.datafeed.IDatafeed;

import java.util.Arrays;
import java.util.ArrayList;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

public class Datafeed implements IDatafeed {
    protected int _sequenceNumber = 0;
    protected int _size = 0;
    protected boolean _endOfFeed = false;
    protected ArrayList<byte[]> _elements = new ArrayList<byte[]>(50);
    protected static final int BUFFSIZE = 4096;
    protected ByteBuffer _buffer = ByteBuffer.allocateDirect(BUFFSIZE);
    protected static Logger log = Logger.getLogger(Datafeed.class);
    
    private ArrayList<IDatafeedListener> _datafeedListeners = new ArrayList<IDatafeedListener>();
    
    public Datafeed() {
    	log.info(getVersion());
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
    	_size += length;
    	
        if(length < 1) {
            _endOfFeed = true;
            log.debug("No remaining bytes to read, assuming end of feed at " + getSequenceNumber() + " (" + _size + " bytes so far)");
            return;
        }
        
        processDatafeedReceivedEvent(
        		new DatafeedReceivedEvent(this, Arrays.copyOf(buffer, length)));
        
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
                }
                /**
                else {
                    for(int x=0; x<_elements.size(); x++) {
                        log.info("XXX " + (new String(_elements.get(x))).trim());
                    }
                }
                **/
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
    
    public synchronized void addDatafeedListener(IDatafeedListener listener) {
    	if(!_datafeedListeners.contains(listener)) {
    		_datafeedListeners.add(listener);
    	}
    }
    
    public synchronized void removeDatafeedListener(IDatafeedListener listener) {
    	if(_datafeedListeners.contains(listener)) {
    		_datafeedListeners.remove(listener);
    	}
    }
    
    @SuppressWarnings("unchecked")
	private void processDatafeedReceivedEvent(DatafeedReceivedEvent datafeedReceivedEvent) {
    	ArrayList<IDatafeedListener> tmp;
    	
    	synchronized(this) {
    		if(_datafeedListeners.size() == 0) {
    			return;
    		}
    		tmp = (ArrayList<IDatafeedListener>) _datafeedListeners.clone();
    	}
    	
    	for(IDatafeedListener listener : tmp) {
    		listener.onDatafeedReceived(datafeedReceivedEvent);
    	}
    }
}
