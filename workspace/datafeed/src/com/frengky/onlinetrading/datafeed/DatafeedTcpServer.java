package com.frengky.onlinetrading.datafeed;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.util.Map;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class DatafeedTcpServer extends IoHandlerAdapter implements IDatafeedListener {
    
    protected String _host = "0.0.0.0";
    protected int _port = 9010;
    protected IoAcceptor _acceptor;
    protected static Logger log = Logger.getLogger(DatafeedTcpServer.class);
    
    public DatafeedTcpServer() {
        _init();
    }
    
    protected void _init() {
        _acceptor = new NioSocketAcceptor();
        _acceptor.getFilterChain().addLast("codec", 
                new ProtocolCodecFilter(new BytesCodecFactory()));
        
        _acceptor.setHandler(this);
        _acceptor.getSessionConfig().setReadBufferSize(2048);
        _acceptor.getSessionConfig().setBothIdleTime(30);
    }
    
    protected String getBanner() {
    	return "Welcome to datafeed server";
    }
    
    public void listen() {
        log.info("Starting server at " + _host + ", port " + _port + " ...");
        
        try {
            _acceptor.bind(new InetSocketAddress(_host, _port));
            log.info("Ready for connection");
        } catch(IOException ex) {
            log.error(ex.getMessage());
        }
    }
    
    public void listen(String host, int port) {
        _host = host;
        _port = port;
        listen();
    }
    
    public void onDatafeedReceived(DatafeedReceivedEvent e) {
    	broadcast(e.getBytes());
    }
    
    public void broadcast(Object message) {
    	if(_acceptor.getManagedSessionCount() < 1) {
    		return;
    	}
    	
    	Map<Long, IoSession> sessions = _acceptor.getManagedSessions();
    	for(Map.Entry<Long, IoSession> client : sessions.entrySet()) {
    		IoSession session = client.getValue();
    		
			if((session.getAttribute("user") instanceof String) == true) {
				String user = (String)session.getAttribute("user");
	    		try {
	    			WriteFuture write = session.write(message);
	    			write.awaitUninterruptibly();
	    		} catch(Exception ex) {
	    			log.error("Failed to contact " + user + ", " + ex.getMessage());
	    		}
			}
    	}
    }
    
    public void stop() {
        _acceptor.unbind();
        log.info("Stopped");
    }
    
    public void dispose() {
        _acceptor.dispose(true);
        _acceptor = null;
    }
    
    protected String getHostAddress(IoSession session) {
        InetAddress addr = ((InetSocketAddress)session.getRemoteAddress()).getAddress();
        return addr.getHostAddress();        
    }    
    
    public void sessionOpened(IoSession session) {
        log.info(getHostAddress(session) + " session opened");
    }
    
    public void messageReceived(IoSession session, Object message) {
    }
    
    public void sessionIdle(IoSession session, IdleStatus status) {
        log.info(getHostAddress(session) + " idle");
    }
    
    public void sessionClosed(IoSession session) {
        log.info(getHostAddress(session) + " session closed");        
    }    
    
    public void exceptionCaught(IoSession session, Throwable cause) {
        session.close(true);
        log.error(getHostAddress(session) + " exception caught, " + cause.getMessage());
    }
}
