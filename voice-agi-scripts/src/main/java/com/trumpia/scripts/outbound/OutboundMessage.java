package com.trumpia.scripts.outbound;

import com.trumpia.scripts.outbound.messages.OutboundMent;
import com.trumpia.utils.VoiceUpdateStatusUtil;
import org.asteriskjava.fastagi.*;

import com.trumpia.framework.core.JFLogger;
import com.trumpia.framework.util.voice.agi.params.VoiceServletParams;

/**
 * Created by Logan K. on 13. 12. 19.오후 5:28
 */
public class OutboundMessage extends BaseAgiScript {

    @Override
    public void service(final AgiRequest agiRequest, final AgiChannel agiChannel) throws AgiException {

        JFLogger.logDebug(getClass(), "OutboundMessage service run");

        String sendTaskUidWithDbInfo = null;
        try {
            JFLogger.logDebug(getClass(), agiRequest);

            // http://www.voip-info.org/wiki/view/Asterisk+Detailed+Variable+List
            sendTaskUidWithDbInfo = agiChannel.getVariable(VoiceServletParams.Outbound.SENDTASK_UID);
            VoiceUpdateStatusUtil.updateReceivedStatusConnected(sendTaskUidWithDbInfo);

            final OutboundMent recordVoiceMessage = OutboundMent.instance(agiChannel.getVariable(VoiceServletParams.Outbound.GUIDE_VOICE_TYPE));
            recordVoiceMessage.setIntro(agiChannel.getVariable(VoiceServletParams.Outbound.INTRO_PATH));
            recordVoiceMessage.setMessage(agiChannel.getVariable(VoiceServletParams.Outbound.MESSAGE_PATH));
            JFLogger.logDebug(getClass(), recordVoiceMessage);

            recordVoiceMessage.play(agiRequest, agiChannel);

        } catch (final AgiHangupException e) {
            // The AgiHangupException is thrown if the channel is hung up while
            // processing the AgiRequest.
            JFLogger.logDebug(getClass(), "[" + sendTaskUidWithDbInfo + "] Hangup Cause > " + e.getCause());
            JFLogger.logDebug(getClass(), "[" + sendTaskUidWithDbInfo + "] Hangup Message > " + e.getMessage());
        } catch (final AgiNetworkException e) {
            // The AgiNetworkException usally wraps an IOException denoting a
            // network problem while talking to the Asterisk server.
            JFLogger.logDebug(getClass(), "[" + sendTaskUidWithDbInfo + "] AgiNetworkException Cause > " + e.getCause());
            JFLogger.logDebug(getClass(), "[" + sendTaskUidWithDbInfo + "] AgiNetworkException Message > " + e.getMessage());
        } catch (final AgiException e) {
            JFLogger.logError(getClass(), "[" + sendTaskUidWithDbInfo + "] AgiException > " + e.getMessage(), e);
        } catch (final Exception e) {
            JFLogger.logError(getClass(), "[" + sendTaskUidWithDbInfo + "] Exception > " + e.getMessage(), e);
        }

        // finally {
        // VoiceUpdateStatusUtil.updateReceivedStatusConnected(sendTaskUidWithDbInfo);
        // }
        hangup();
    }
}
