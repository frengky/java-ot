package com.frengky.onlinetrading.datafeed.idx;

public class IDXDatafeedRecordTradingStatus {
    public static final byte BeginSendingRecord = 0x31;
    public static final byte Idle = 0x32;
    public static final byte BeginSessionOne = 0x33;
    public static final byte EndSessionOne = 0x34;
    public static final byte BeginSessionTwo = 0x35;
    public static final byte EndSessionTwo = 0x36;
    public static final byte EndSendingRecord = 0x37;
    public static final byte BeginPreOpening = 0x38;
    public static final byte EndPreOpening = 0x39;
    public static final byte BeginPreClosing = 0x61;
    public static final byte EndPreClosing = 0x62;
    public static final byte BeginPostClosing = 0x63;
    public static final byte EndPostClosing = 0x64;
    public static final byte TradingSuspension = 0x65;
    public static final byte TradingActivation = 0x66;
    public static final byte BoardSuspension = 0x67;
    public static final byte BoardActivation = 0x68;
    public static final byte InstrumentSuspension = 0x69;
    public static final byte InstrumentActivation = 0x6A;
    public static final byte MarketSuspension = 0x6B;
    public static final byte MarketActivation = 0x6C;
    public static final byte NotStarted = 0x01;
    // Index position in stream
    public static final int StreamPosition = 31;    
}
