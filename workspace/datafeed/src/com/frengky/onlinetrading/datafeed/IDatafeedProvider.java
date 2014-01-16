/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed;

import java.net.URI;
/**
 *
 * @author franky
 */
public interface IDatafeedProvider {
    IDatafeed getDatafeed();
    IDatafeedClient getClient(URI location);
}
