package com.frengky.onlinetrading.datafeed;

import java.net.URI;
import java.net.InetSocketAddress;
import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class DatafeedTcpClient extends IoHandlerAdapter implements IDatafeedClient {
    protected String _username;
    protected String _password;
    protected String _option = "1";
    protected URI _location;
    protected IDatafeed _datafeed;
    protected IoConnector _connector;
    protected ConnectFuture _future;
    protected static Logger log = Logger.getLogger(DatafeedTcpClient.class);
    
    public DatafeedTcpClient() {
        _init();
    }
    
    public DatafeedTcpClient(IDatafeed datafeed) {
        _datafeed = datafeed;
        _init();
    }
    
    protected void _init() {
        _connector = new NioSocketConnector();
        _connector.getFilterChain().addLast("codec", 
                new ProtocolCodecFilter(new BytesCodecFactory()));
        
        _connector.getSessionConfig().setReadBufferSize(2048);
        _connector.getSessionConfig().setReaderIdleTime(30);
        _connector.setHandler(this);        
    }
    
    public URI getLocation() {
        return _location;
    }
    
    public void setLocation(URI location) {
        _location = location;
    }
    
    public IDatafeed getDatafeed() {
        return _datafeed;
    }

    public void setDatafeed(IDatafeed datafeed) {
        _datafeed = datafeed;
    }
    
    public String getUsername() {
        return _username;
    }
    
    public void setUsername(String username) {
        _username = username;
    }
    
    public String getPassword() {
        return _password;
    }
    
    public void setPassword(String password) {
        _password = password;
    }
    
    public String getOption() {
        return _option;
    }
    
    public void setOption(String option) {
        _option = option;
    }
    
    public void connect() {
        log.info("Connecting to " + _location.toString() + " ...");
                
        _future = _connector.connect(new InetSocketAddress(_location.getHost(), _location.getPort()));
        _future.awaitUninterruptibly();
        
        if(_future.isConnected()) {
        	log.info("Connected!");
        } else {
        	log.error("Connecting failed!");
        }
    }
    
    public void connect(URI location) {
        _location = location;
        connect();
    }
    
    public void connect(String host, int port) {
        try {
            _location = new URI("tcp://" + host + ":" + port);
            connect();
        } catch(Exception ex) {
            log.error(ex.getMessage());
        }
    }
    
    public void disconnect() {
        if(_future.isConnected())
            _future.getSession().close(true);
    }
    
    public void dispose() {
        _connector.dispose();
        _connector = null;
    }
    
    public void sessionOpened(IoSession session) {
    	log.debug("Session opened");
    }
    
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        if(_datafeed.endOfFeed()) {
            session.close(true);
            _connector.dispose();
        }
    }
    
    public void sessionClosed(IoSession session) {
    	log.debug("Session closed");
    }
    
    public void messageReceived(IoSession session, Object message) {
        byte[] bytesRead = (byte[])message;
        
        _datafeed.read(bytesRead, 0, bytesRead.length);
    }
    
    public void exceptionCaught(IoSession session, Throwable cause) {
        session.close(true);
        log.error("Exception caught: " + cause.getMessage());
    }
}
