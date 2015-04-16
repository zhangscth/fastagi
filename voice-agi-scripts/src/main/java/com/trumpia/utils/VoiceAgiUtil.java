package com.trumpia.utils;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;

import com.trumpia.framework.core.JFLogger;
import com.trumpia.properties.AsteriskApplication;
import com.trumpia.status.ChannelStatus;

/**
 * Created by Logan K. on 13. 12. 27.오후 6:08
 */
public class VoiceAgiUtil {

    public static void channelStatus(final AgiChannel agiChannel, final String sendTaskUid) throws AgiException {

        final ChannelStatus channelStatus = ChannelStatus.get(agiChannel.getChannelStatus());
        JFLogger.logDebug(VoiceAgiUtil.class, "[" + sendTaskUid + "] agiChannel.getChannelStatus in VoiceMessageAgiScript > " + channelStatus);
        switch (channelStatus) {

            case Channel_Is_Down_And_Available:
            case Channel_Is_Down_But_Reserved:
            case Channel_Is_Off_Hook:
            case Digits_Have_Been_Dialed:
            case Remote_End_Is_Ringing:
            case Line_Is_Busy:
                JFLogger.logError(VoiceAgiUtil.class, "[" + sendTaskUid + "] [Not Exception] Unexpected ChannelStatus > " + channelStatus);
                break;

            case Line_Is_Ringing:
                JFLogger.logDebug(VoiceAgiUtil.class, "[" + sendTaskUid + "] Ringing");
                break;

            case Line_Is_Up:// user received agiChannel.getName()
                JFLogger.logDebug(VoiceAgiUtil.class, "[" + sendTaskUid + "] User received call.");
                JFLogger.logDebug(VoiceAgiUtil.class, "[" + sendTaskUid + "] Sip call id > " + AsteriskApplication.Sip.SIPCALLID.fullVariable(agiChannel));
                break;
        }
    }
}
