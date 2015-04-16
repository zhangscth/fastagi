package com.trumpia.dialer;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;

import com.trumpia.framework.core.JFLogger;
import com.trumpia.framework.util.voice.agi.account.Account;
import com.trumpia.framework.util.voice.agi.params.VoiceServletParams;

/**
 * Created by Logan K. on 14. 1. 13.오전 11:00
 */
public class AuthDialer extends Dialer {

    protected String toolData;

    public AuthDialer(final AgiChannel channel) {

        super(channel);
    }

    @Override
    public void initialization() throws AgiException {

        this.toolData = channel.getVariable(VoiceServletParams.Auth.PHONE_NUMBER).replaceAll("\\s", "");

        // exec caller id to display user phone
        this.callerNumber = channel.getVariable(VoiceServletParams.Auth.CALLER_NUMBER);
        this.channel.exec(APPLICATION_SIP_ADD_HEADER, X_ASTERISK_CALLERID + ":" + this.callerNumber);
        JFLogger.logDebug(getClass(), "[" + this.tracer() + "] exec(APPLICATION_SIP_ADD_HEADER, X_ASTERISK_CALLERID > " + this.callerNumber);

        this.account = Account.instance(channel.getVariable(VoiceServletParams.Auth.LOCAL_IP));
        channel.setCallerId(account.getAccount());
        JFLogger.logDebug(getClass(), "[" + this.tracer() + "] > " + this.account);

    }

    @Override
    public String destination() {

        return this.sipProvider.destination(this.toolData);
    }

    @Override
    public String tracer() {

        return this.callerNumber + " > " + this.toolData;
    }
}
