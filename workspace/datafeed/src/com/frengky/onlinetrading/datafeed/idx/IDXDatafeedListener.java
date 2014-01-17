package com.frengky.onlinetrading.datafeed.idx;

import com.frengky.onlinetrading.datafeed.DatafeedListener;

public interface IDXDatafeedListener extends DatafeedListener {
	void onTradingStatusChanged(IDXDatafeedTradingStatusEvent e);
}
