package com.frengky.onlinetrading.datafeed.idx;

import java.util.ArrayList;
import java.util.Date;
import java.lang.Thread;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class IDXDatafeedSimulator extends IDXDatafeed {
    private Date _lastDateTime;
    private SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyyMMddkkmmss");
    
    protected void handleElements(ArrayList<byte[]> elements) {
        try {
            if(_lastDateTime == null) {
                _lastDateTime = getDate(elements.get(1), elements.get(2));
            } else {
                Date _current = getDate(elements.get(1), elements.get(2));
                long diff = _current.getTime()-_lastDateTime.getTime();
                
                _lastDateTime = _current;
                if(diff > 0)
                    Thread.sleep(diff);
            }
        } catch(Exception ex) {
            log.error(ex.getMessage());
        }
        super.handleElements(elements);
    }
    
    private Date getDate(byte[] date, byte[] time) throws ParseException {
        String datetime = new String(date);
        datetime += new String(time);
        
        return _dateFormat.parse(datetime);
    }
}
