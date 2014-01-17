package com.frengky.onlinetrading.datafeed;

import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class BytesProtocolDecoder extends CumulativeProtocolDecoder {
    private byte[] data;
    
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        int remaining = in.remaining();
        
        data = new byte[remaining];
        in.get(data, 0, data.length);
        
        out.write(data);
        return true;
    }
}
