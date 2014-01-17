package com.frengky.onlinetrading.datafeed;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.core.session.IoSession;

public class BytesProtocolEncoder implements ProtocolEncoder {
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        byte[] data = (byte[]) message;
        
        IoBuffer buffer = IoBuffer.wrap(data);
        out.write(buffer);
    }
    
    public void dispose(IoSession session) throws Exception {
    }
}
