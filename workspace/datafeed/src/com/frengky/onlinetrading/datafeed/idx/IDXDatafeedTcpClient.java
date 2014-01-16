/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed.idx;

import com.frengky.onlinetrading.datafeed.DatafeedTcpClient;
import com.frengky.onlinetrading.datafeed.IDatafeed;
import java.nio.charset.Charset;
import org.apache.mina.core.session.IoSession;
/**
 *
 * @author franky
 */
public class IDXDatafeedTcpClient extends DatafeedTcpClient {
    
    public IDXDatafeedTcpClient() {
        _datafeed = new IDXDatafeed();
    }
    
    public IDXDatafeedTcpClient(IDatafeed datafeed) {
        _datafeed = datafeed;
        _init();
    }
    
    public void sessionOpened(IoSession session) {
        String logon = _username + "|" + _password + "|" + _option + "\r\n";
        log.debug("Sending logon [" + logon.trim() + "]");
        session.write(logon.getBytes(Charset.forName("US-ASCII")));
    }
}
