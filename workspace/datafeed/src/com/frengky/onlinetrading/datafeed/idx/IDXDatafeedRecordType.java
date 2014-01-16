/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed.idx;

/**
 *
 * @author franky
 */
public class IDXDatafeedRecordType {
    public static final byte TradingStatus = 0x30;
    public static final byte Order = 0x31;
    public static final byte Trade = 0x32;
    public static final byte StockData = 0x33;
    public static final byte BrokerData = 0x34;
    public static final byte StockSummary = 0x35;
    public static final byte Indices = 0x36;
    public static final byte SuspendReleaseBroker = 0x37;
    public static final byte SuspendReleaseStock = 0x38;
    public static final byte News = 0x39;
    public static final byte Unknown = 0x01;
    // Index position in stream
    public static final int StreamPosition = 29;    
}

