package com.trumpia.scripts.auth;

import com.trumpia.framework.core.JFLogger;
import com.trumpia.framework.util.voice.agi.params.VoiceServletParams;
import com.trumpia.scripts.auth.messages.AuthMent;
import org.asteriskjava.fastagi.*;

/**
 * Created by Logan K. on 14. 1. 11.오후 4:57
 */
public class AuthMessage extends BaseAgiScript {

    @Override
    public void service(final AgiRequest request, final AgiChannel channel) throws AgiException {

        JFLogger.logDebug(getClass(), "AuthCallMessage service run.");
        JFLogger.logDebug(getClass(), request);

        String phoneNumber = null;
        try {

            // http://www.voip-info.org/wiki/view/Asterisk+Detailed+Variable+List
            phoneNumber = channel.getVariable(VoiceServletParams.Outbound.SENDTASK_UID);

            final AuthMent authMent = new AuthMent();
            authMent.play(request, channel);

        } catch (final AgiHangupException e) {
            // The AgiHangupException is thrown if the channel is hung up while
            // processing the AgiRequest.
            JFLogger.logDebug(getClass(), "[" + phoneNumber + "] Hangup Cause > " + e.getCause());
            JFLogger.logDebug(getClass(), "[" + phoneNumber + "] Hangup Message > " + e.getMessage());
        } catch (final AgiNetworkException e) {
            // The AgiNetworkException usally wraps an IOException denoting a
            // network problem while talking to the Asterisk server.
            JFLogger.logDebug(getClass(), "[" + phoneNumber + "] AgiNetworkException Cause > " + e.getCause());
            JFLogger.logDebug(getClass(), "[" + phoneNumber + "] AgiNetworkException Message > " + e.getMessage());
        } catch (final AgiException e) {
            JFLogger.logError(getClass(), "[" + phoneNumber + "] AgiException > " + e.getMessage(), e);
        } catch (final Exception e) {
            JFLogger.logError(getClass(), "[" + phoneNumber + "] Exception > " + e.getMessage(), e);
        }

        hangup();
    }
}
