package com.frengky.onlinetrading.datafeed.idx;

import com.frengky.onlinetrading.datafeed.DatafeedTcpServer;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;

public class IDXDatafeedTcpServer extends DatafeedTcpServer {

    public IDXDatafeedTcpServer() {
        super();
    }
    
    private void doLogon(IoSession session, Object message) {
        String clientHost = getHostAddress(session);
        
        String[] logon;
        try {
            String logonStr = new String((byte[])message, "US-ASCII");
            log.debug(clientHost + " said " + logonStr.trim());
            
            logon = logonStr.split("\\|", 3);
            if(logon.length < 3 || 
                    logon[0].length() < 1 || 
                    logon[1].length() < 1 || 
                    logon[2].length() < 1)
                throw new Exception("wrong username or password");
        } catch(Exception ex) {
            log.error(clientHost + " login failed, " + ex.getMessage());
            session.close(true);
            return;
        }
        
        String user = logon[0];
        String passwd = logon[1];
        String option = logon[2].toLowerCase();
        
        log.info(clientHost + " login success, known as " + user);
        
        session.setAttribute("user", user);
        session.setAttribute("passwd", passwd);
        session.setAttribute("option", option);
        session.setAttribute("sequence", 1);
        
        String ok = "OK " + clientHost + "\r\n";
        session.write(ok.getBytes(Charset.forName("US-ASCII")));
    }  
    
    public void sessionOpened(IoSession session) {
        super.sessionOpened(session);
        
        String welcomeMsg = getBanner() + "\r\n";
        session.write(welcomeMsg.getBytes(Charset.forName("US-ASCII")));
    }
    
    public void messageReceived(IoSession session, Object message) {
        if((session.getAttribute("user") instanceof String) == false) {
            doLogon(session, message);
        }
    }
}
