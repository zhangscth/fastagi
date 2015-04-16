package com.trumpia.scripts.auth;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import com.trumpia.dialer.AuthDialer;
import com.trumpia.dialer.Dialer;
import com.trumpia.framework.core.JFLogger;

/**
 * Created by Logan K. on 14. 1. 10.오후 4:43
 */
public class AuthDialOut extends BaseAgiScript {

    @Override
    public void service(final AgiRequest request, final AgiChannel channel) throws AgiException {

        JFLogger.logDebug(getClass(), "AuthCallDialOut service run.");
        final Dialer authDialer = new AuthDialer(channel);
        authDialer.initialization();
        authDialer.dialout();
    }
}
