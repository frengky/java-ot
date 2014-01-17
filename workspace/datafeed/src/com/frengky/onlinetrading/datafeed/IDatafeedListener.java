package com.frengky.onlinetrading.datafeed;

import java.util.EventListener;

public interface IDatafeedListener extends EventListener {
	public void onDatafeedReceived(DatafeedReceivedEvent e);
}
