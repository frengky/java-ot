package com.frengky.onlinetrading.datafeed;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.core.session.IoSession;

public class BytesCodecFactory implements ProtocolCodecFactory {
    private ProtocolEncoder encoder;
    private ProtocolDecoder decoder;
    
    public BytesCodecFactory() {
        encoder = new BytesProtocolEncoder();
        decoder = new BytesProtocolDecoder();
    }
    
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }
    
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }
}
