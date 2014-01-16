/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed;

import com.frengky.onlinetrading.datafeed.idx.IDXDatafeedProvider;

/**
 *
 * @author franky
 */
public class DatafeedProvider {
    
    public DatafeedProvider() {
    }
    
    public static IDatafeedProvider forName(String datafeed) {
        
        // only idx datafeed is supported at the moment..
        return new IDXDatafeedProvider();
    }
}
