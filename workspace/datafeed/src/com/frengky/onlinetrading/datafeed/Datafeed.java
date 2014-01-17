package com.frengky.onlinetrading.datafeed;

import com.frengky.onlinetrading.datafeed.IDatafeed;

import java.util.Arrays;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Datafeed implements IDatafeed {
    private int _size = 0;
    protected int _sequenceNumber = 0;
    protected boolean _endOfFeed = false;
    protected static Logger log = Logger.getLogger(Datafeed.class);
    
    private ArrayList<IDatafeedListener> _datafeedListeners = new ArrayList<IDatafeedListener>();
    
    public Datafeed() {
    	log.info(getVersion());
    }
    
    public String getVersion() {
        return "No datafeed version implemented";
    }
    
    public int getSequenceNumber() {
        return _sequenceNumber;
    }
    
    public void read(byte[] buffer, int offset, int length) {
        if(length < 1) {
            _endOfFeed = true;
            log.debug("No remaining bytes to read, assuming end of feed at " + getSequenceNumber() + " (" + _size + " bytes so far)");
            return;
        }
        
    	_size += length;
        processDatafeedReceivedEvent(
        		new DatafeedReceivedEvent(this, Arrays.copyOf(buffer, length)));
    }
    
    public void reset() {
        _sequenceNumber = 0;
        _endOfFeed = false;
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
