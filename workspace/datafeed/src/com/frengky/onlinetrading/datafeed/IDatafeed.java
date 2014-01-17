package com.frengky.onlinetrading.datafeed;

public interface IDatafeed {
    
    String getVersion();
    int getSequenceNumber();
    void reset();
    boolean endOfFeed();
    
    void read(byte[] buffer, int offset, int length);
    void addDatafeedListener(IDatafeedListener listener);
    void removeDatafeedListener(IDatafeedListener listener);
}
