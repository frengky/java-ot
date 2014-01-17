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
    
    protected ArrayList<DatafeedStreamListener> _streamListeners = new ArrayList<DatafeedStreamListener>();
    
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
        processStreamEvent(new DatafeedStreamEvent(this, Arrays.copyOf(buffer, length)));
    }
    
    public void reset() {
        _sequenceNumber = 0;
        _endOfFeed = false;
    }
    
    public boolean endOfFeed() {
        return _endOfFeed;
    }
    
    public synchronized void addStreamListener(DatafeedStreamListener listener) {
    	if(!_streamListeners.contains(listener)) {
    		_streamListeners.add(listener);
    	}
    }
    
    public synchronized void removeStreamListener(DatafeedStreamListener listener) {
    	if(_streamListeners.contains(listener)) {
    		_streamListeners.remove(listener);
    	}
    }
    
    @SuppressWarnings("unchecked")
	protected void processStreamEvent(DatafeedStreamEvent streamEvent) {
    	ArrayList<DatafeedStreamListener> tmp;
    	
    	synchronized(this) {
    		if(_streamListeners.size() == 0) {
    			return;
    		}
    		tmp = (ArrayList<DatafeedStreamListener>) _streamListeners.clone();
    	}
    	for(DatafeedStreamListener listener : tmp) {
    		listener.onDatafeedStream(streamEvent);
    	}
    }
}
