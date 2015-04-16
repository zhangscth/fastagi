package com.trumpia.dialer;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;

import com.trumpia.framework.core.JFLogger;
import com.trumpia.framework.util.voice.agi.account.Account;
import com.trumpia.properties.AsteriskApplication;
import com.trumpia.sipprovider.Appia;
import com.trumpia.sipprovider.Innodial;
import com.trumpia.sipprovider.SipProvider;
import com.trumpia.status.DialStatus;

/**
 * Created by Logan K. on 14. 1. 13.오전 9:18
 */
public abstract class Dialer {

    public static final String APPLICATION_SIP_ADD_HEADER = "SIPAddHeader";
    public static final String X_ASTERISK_CALLERID        = "X-Asterisk-callerid";

    protected SipProvider      sipProvider;
    protected AgiChannel       channel;
    protected Account          account;
    protected String           callerNumber;

    public Dialer(final AgiChannel channel) {

        this.channel = channel;
    }

    public abstract void initialization() throws AgiException;

    public abstract String destination();

    public abstract String tracer();

    public void dialout() throws AgiException {

        this.sipProvider = new Innodial();

        JFLogger.logDebug(getClass(), "[" + tracer() + "] before exec(Dial, " + destination() + ")");
        channel.exec(AsteriskApplication.Dial.Application, destination());
        JFLogger.logDebug(getClass(), "[" + tracer() + "] after exec(Dial, " + destination() + ")");

        final DialStatus dialStatus = DialStatus.get(AsteriskApplication.Dial.DIALSTATUS.fullVariable(this.channel));
        JFLogger.logDebug(getClass(), "[" + tracer() + "] DialStatus on Innodial > " + dialStatus);

        switch (dialStatus) {
            case ANSWER:
            case BUSY:
            case NOANSWER:
            case CANCEL:
            case DONTCALL:
                break;
            case TORTURE:
            case INVALIDARGS:
                JFLogger.logError(getClass(), "[" + tracer() + "][Unexpected DialStatus] >>> " + dialStatus);
                break;
            case CONGESTION:
            case CHANUNAVAIL:
                JFLogger.logError(getClass(), "[" + tracer() + "][Somehing Wrong] >>> " + dialStatus);
//                dialoutThroughAppia();
                break;
        }
    }

    public void dialoutThroughAppia() throws AgiException {

        this.sipProvider = new Appia();

        JFLogger.logDebug(getClass(), "[" + tracer() + "] Dial > " + destination());

        channel.setCallerId(this.callerNumber);

        channel.exec(AsteriskApplication.Dial.Application, destination());
        final DialStatus dialStatus = DialStatus.get(AsteriskApplication.Dial.DIALSTATUS.fullVariable(channel));
        JFLogger.logDebug(getClass(), "[" + tracer() + "] DialStatus on Appia > " + dialStatus);
        switch (dialStatus) {
            case ANSWER:
            case BUSY:
            case NOANSWER:
            case CANCEL:
            case DONTCALL:
                break;
            case TORTURE:
            case INVALIDARGS:
                JFLogger.logError(getClass(), "[" + tracer() + "][Unexpected DialStatus] >>> " + dialStatus);
                break;
            case CONGESTION:
            case CHANUNAVAIL:
                JFLogger.logDebug(getClass(), "[" + tracer() + "][Not Exception] Failed to make call > " + destination());
                break;
        }
    }
}
