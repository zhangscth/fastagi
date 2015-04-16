package com.trumpia.scripts.outbound;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import com.trumpia.dialer.Dialer;
import com.trumpia.dialer.OutboundDialer;
import com.trumpia.framework.core.JFLogger;

/**
 * Created by Logan K. on 13. 12. 19.오후 5:15
 */
public class OutboundDialOut extends BaseAgiScript {

    @Override
    public void service(final AgiRequest request, final AgiChannel channel) throws AgiException {

        JFLogger.logDebug(getClass(), "------------------------------------------------------------- OutboundDialOut service run.");
        final Dialer dialer = new OutboundDialer(channel);
        dialer.initialization();
        dialer.dialout();
    }
}
