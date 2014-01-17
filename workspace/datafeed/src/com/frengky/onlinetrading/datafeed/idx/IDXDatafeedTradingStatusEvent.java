package com.frengky.onlinetrading.datafeed.idx;

import java.util.EventObject;

@SuppressWarnings("serial")
public class IDXDatafeedTradingStatusEvent extends EventObject {

	private byte _tradingStatus;
	
	public IDXDatafeedTradingStatusEvent(Object source, byte tradingStatus) {
		super(source);
		_tradingStatus = tradingStatus;
	}
	
	public byte getTradingStatus() {
		return _tradingStatus;
	}
}
