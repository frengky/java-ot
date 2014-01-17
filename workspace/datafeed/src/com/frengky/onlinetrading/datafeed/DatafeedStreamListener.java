package com.frengky.onlinetrading.datafeed;

import java.util.EventListener;

public interface DatafeedStreamListener extends EventListener {
	void onDatafeedStream(DatafeedStreamEvent e);
}
