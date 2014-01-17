package com.frengky.onlinetrading.datafeed.idx;

import com.frengky.onlinetrading.datafeed.DatafeedStreamListener;

public interface IDXDatafeedListener extends DatafeedStreamListener {
	void onTradingStatusChanged(IDXDatafeedTradingStatusEvent e);
}
