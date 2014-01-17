package com.frengky.onlinetrading.datafeed.idx;

import com.frengky.onlinetrading.datafeed.Datafeed;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class IDXDatafeed extends Datafeed {
    private ArrayList<byte[]> _elements = new ArrayList<byte[]>(50);
    private static final int BUFFSIZE = 4096;
    private ByteBuffer _buffer = ByteBuffer.allocateDirect(BUFFSIZE);
	
    public IDXDatafeed() {
    	super();
    }
    
    public String getVersion() {
        return "IDX Datafeed G4.0.3b";
    }
    
    public void read(byte[] buffer, int offset, int length) {
    	super.read(buffer, offset, length);
    	
        if(length < 1) {
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
                    processRecordElement(_elements);
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
    	super.reset();
    	_elements.clear();
    }
    
    protected void processRecordElement(ArrayList<byte[]> elements) {
        
        if(_elements.get(4)[0] == IDXDatafeedRecordType.TradingStatus) {

        	if(_elements.get(5)[0] == IDXDatafeedRecordTradingStatus.EndSendingRecord) {
            	log.debug("Trading status now is EndSendingRecord");
                _endOfFeed= true;
        	}
        	
        }
        
        // Proceed datafeed raw message here...
        /**
        String test = "HEAD=";
        test += new String(elements.get(0));
        test += " SEQ=";
        test += new String(elements.get(3));

        log.debug(test);
        **/
    }
}
