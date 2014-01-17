package com.frengky.onlinetrading.datafeed;

import java.util.EventObject;

@SuppressWarnings("serial")
public class DatafeedStreamEvent extends EventObject {
	
	private byte[] _data;
	
	public DatafeedStreamEvent(Object source, byte[] data) {
		super(source);
		_data = data;
	}
	
	public byte[] getBytes() {
		return _data;
	}
	
	public String getString() {
		return new String(_data);
	}
}
