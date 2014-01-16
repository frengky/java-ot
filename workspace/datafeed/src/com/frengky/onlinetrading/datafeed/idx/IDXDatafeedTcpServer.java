/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frengky.onlinetrading.datafeed.idx;

import com.frengky.onlinetrading.datafeed.DatafeedTcpServer;
import java.nio.charset.Charset;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author franky
 */
public class IDXDatafeedTcpServer extends DatafeedTcpServer {

    public IDXDatafeedTcpServer() {
        super();
    }
    
    private void doLogon(IoSession session, Object message) {
        String clientHost = getHostAddress(session);
        
        String[] logon;
        try {
            String logonStr = new String((byte[])message, "US-ASCII").trim();
            logon = logonStr.split("\\|", 3);
            if(logon.length < 3 || 
                    logon[0].length() < 1 || 
                    logon[1].length() < 1 || 
                    logon[2].length() < 1)
                throw new Exception("user, passwd and option expected");
        } catch(Exception ex) {
            log.warn(clientHost + ": Invalid logon format (" + ex.getMessage() + ")");
            session.close(true);
            return;
        }
        
        String user = logon[0];
        String passwd = logon[1];
        String option = logon[2].toLowerCase();
        
        log.info(clientHost + 
                ": Logon accepted [user: " + user + 
                ", passwd: " + passwd + 
                ", option: " + option + "]");
        
        session.setAttribute("user", user);
        session.setAttribute("passwd", passwd);
        session.setAttribute("option", option);
        session.setAttribute("sequence", 1);
        
        String ok = "OK " + clientHost + "\r\n";
        session.write(ok.getBytes(Charset.forName("US-ASCII")));
    }  
    
    public void sessionOpened(IoSession session) {
        super.sessionOpened(session);
        
        String welcomeMsg = "Welcome to datafeed server\r\n";
        session.write(welcomeMsg.getBytes(Charset.forName("US-ASCII")));
    }    
    
    public void messageReceived(IoSession session, Object message) {
        if((session.getAttribute("user") instanceof String) == false) {
            doLogon(session, message);
        }
    }
}
