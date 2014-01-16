/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed.idx;

import com.frengky.onlinetrading.datafeed.DatafeedFileClient;
import com.frengky.onlinetrading.datafeed.IDatafeed;
/**
 *
 * @author franky
 */
public class IDXDatafeedFileClient extends DatafeedFileClient {
    
    public IDXDatafeedFileClient() {
        _datafeed = new IDXDatafeed();
    }
    
    public IDXDatafeedFileClient(IDatafeed datafeed) {
        _datafeed = datafeed;
    }
}
