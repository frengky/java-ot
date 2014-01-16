/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed.idx;

import com.frengky.onlinetrading.datafeed.Datafeed;
import java.util.ArrayList;

/**
 *
 * @author franky
 */
public class IDXDatafeed extends Datafeed {
    
    public IDXDatafeed() {
    }
    
    public String getVersion() {
        return "IDX Datafeed G4.0.3b";
    }
    
    protected void handleElements(ArrayList<byte[]> elements) {

        if(_elements.get(4)[0] == IDXDatafeedRecordType.TradingStatus && 
                _elements.get(5)[0] == IDXDatafeedTradingStatus.EndSendingRecord) {
            _endOfFeed= true;
        }        
        
        // Proceed message here...
        /**
        String test = "HEAD=";
        test += new String(elements.get(0));
        test += " SEQ=";
        test += new String(elements.get(3));

        log.debug(test);
        **/
    }
}
