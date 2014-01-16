/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed;

/**
 *
 * @author franky
 */
public interface IDatafeed {
    
    String getVersion();
    int getSequenceNumber();
    void reset();
    boolean endOfFeed();
    
    void read(byte[] buffer, int offset, int length);
}
