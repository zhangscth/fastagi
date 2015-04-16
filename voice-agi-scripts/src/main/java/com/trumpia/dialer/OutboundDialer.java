package com.trumpia.dialer;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;

import com.trumpia.framework.core.JFLogger;
import com.trumpia.framework.util.voice.agi.account.Account;
import com.trumpia.framework.util.voice.agi.params.VoiceServletParams;

/**
 * Created by Logan K. on 14. 1. 13.오전 9:29
 */
public class OutboundDialer extends Dialer {

    public static final String X_ASTERISK_SENDTASKUID = "X-Asterisk-sendtaskuid";
    protected String           sendTaskUidWithDbInfo  = null;
    protected String           countryCode;
    protected String           toolData;

    public OutboundDialer(final AgiChannel channel) {

        super(channel);
    }

    @Override
    public void initialization() throws AgiException {

        this.countryCode = channel.getVariable(VoiceServletParams.Outbound.COUNTRY_CODE);
        this.toolData = channel.getVariable(VoiceServletParams.Outbound.TOOL_DATA);

        // exec caller id to display user phone
        this.callerNumber = channel.getVariable(VoiceServletParams.Outbound.CALLER_NUMBER);
        this.channel.exec(APPLICATION_SIP_ADD_HEADER, X_ASTERISK_CALLERID + ":" + callerNumber);

        // exec sendtask uid
        this.sendTaskUidWithDbInfo = this.channel.getVariable(VoiceServletParams.Outbound.SENDTASK_UID);
        this.channel.exec(APPLICATION_SIP_ADD_HEADER, X_ASTERISK_SENDTASKUID + ":" + sendTaskUidWithDbInfo);

        JFLogger.logDebug(getClass(), "[" + this.tracer() + "] exec(APPLICATION_SIP_ADD_HEADER, X_ASTERISK_CALLERID > " + callerNumber);
        JFLogger.logDebug(getClass(), "[" + this.tracer() + "] exec(APPLICATION_SIP_ADD_HEADER, X_ASTERISK_SENDTASKUID > " + sendTaskUidWithDbInfo);

        this.account = Account.instance(channel.getVariable(VoiceServletParams.Outbound.LOCAL_IP));
        this.channel.setCallerId(account.getAccount());
        JFLogger.logDebug(getClass(), "[" + this.tracer() + "] " + this.account);
    }

    @Override
    public String destination() {

        return this.sipProvider.destination(this.countryCode + this.toolData);
    }

    @Override
    public String tracer() {

        return this.callerNumber + " > " + this.toolData;
    }
}
