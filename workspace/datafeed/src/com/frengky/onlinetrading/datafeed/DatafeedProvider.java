package com.frengky.onlinetrading.datafeed;

import com.frengky.onlinetrading.datafeed.idx.IDXDatafeedProvider;

public class DatafeedProvider {
    
    public DatafeedProvider() {
    }
    
    public static IDatafeedProvider forName(String datafeed) {
        return new IDXDatafeedProvider();
    }
}
