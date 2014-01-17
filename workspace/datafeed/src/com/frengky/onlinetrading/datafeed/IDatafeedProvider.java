package com.frengky.onlinetrading.datafeed;

import java.net.URI;

public interface IDatafeedProvider {
    IDatafeed getDatafeed();
    IDatafeedClient getClient(URI location);
}
