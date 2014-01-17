package com.frengky.onlinetrading.datafeed;

import java.util.EventListener;

public interface DatafeedListener extends EventListener {
	void onDatafeedStream(DatafeedStreamEvent e);
}
