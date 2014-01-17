package com.frengky.onlinetrading.datafeed;

public interface IDatafeed {
    
    String getVersion();
    int getSequenceNumber();
    
    void read(byte[] buffer, int offset, int length);
    void reset();
    boolean endOfFeed();
    void addStreamListener(DatafeedListener listener);
    void removeStreamListener(DatafeedListener listener);
}
