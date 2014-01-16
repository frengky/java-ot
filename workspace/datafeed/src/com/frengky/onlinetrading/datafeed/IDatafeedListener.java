/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed;

import java.util.EventListener;

/**
 *
 * @author franky
 */
public interface IDatafeedListener extends EventListener {
	public void onDatafeedReceived(DatafeedReceivedEvent e);
}
