package com.trumpia.scripts.outbound.messages;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;

import com.trumpia.dtmf.DTMF;
import com.trumpia.framework.core.JFLogger;
import com.trumpia.framework.util.voice.agi.params.VoiceServletParams;
import com.trumpia.scripts.VoiceMent;
import com.trumpia.type.VoiceType;
import com.trumpia.utils.VoiceUpdateStatusUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Logan K. on 13. 12. 19.오후 3:45
 */
public abstract class OutboundMent implements VoiceMent {

    public static final int       MAX_REPLAY_COUNT = 5;
    protected Map<String, String> mentMap          = new HashMap<String, String>(7);

    protected String              messageFrom;
    protected String              intro;
    protected String              press7;                                           // block
    protected String              message;
    protected String              press1;                                           // replay
    protected String              afterPress7;
    protected String              oneEightThree;
    protected String              loopMessage;

    public static OutboundMent instance(final String recordMessageType) {

        if (VoiceType.Male == VoiceType.get(recordMessageType))
            return new MaleMent();
        else
            return new FemaleMent();
    }

    @Override
    public void play(final AgiRequest agiRequest, final AgiChannel agiChannel) throws AgiException {

        final String sendTaskUid = agiChannel.getVariable(VoiceServletParams.Outbound.SENDTASK_UID);
        int replayCount = 0;

        agiChannel.streamFile(this.messageFrom);// messageFrom
        JFLogger.logDebug(getClass(), "play messageFrom > " + this.messageFrom);
        agiChannel.streamFile(this.intro);// intro
        JFLogger.logDebug(getClass(), "play intro > " + this.intro);
        agiChannel.streamFile(this.press7);// tcpa
        JFLogger.logDebug(getClass(), "play press7 > " + this.press7);

        this.loopMessage = this.message;
        while (true) {

            // http://www.voip-info.org/wiki/view/get+data
            final String dtmf = agiChannel.getData(this.loopMessage, WAIT_TIME_OUT_DTMF_3000, 1);// message
            JFLogger.logDebug(getClass(), "play message > " + this.loopMessage);
            final DTMF d = DTMF.get(dtmf);

            JFLogger.logDebug(getClass(), "[" + sendTaskUid + "] DTMF > " + d);

            if (DTMF.One == d) { // replay
                this.loopMessage = this.message;

                VoiceUpdateStatusUtil.updateReplay(sendTaskUid);
                JFLogger.logDebug(getClass(), "[" + sendTaskUid + "] updateReplay");

                replayCount++;
                JFLogger.logDebug(getClass(), "[" + sendTaskUid + "] Replay count > " + replayCount);
                if (replayCount >= MAX_REPLAY_COUNT) {
                    JFLogger.logDebug(getClass(), "[" + sendTaskUid + "] Over max replay count.");
                    break;
                }

                continue;
            } else if (DTMF.Seven == d) {// block
                agiChannel.streamFile(this.afterPress7);
                JFLogger.logDebug(getClass(), "play afterPress7 > " + this.afterPress7);
                VoiceUpdateStatusUtil.blockProcess(sendTaskUid);
                JFLogger.logDebug(getClass(), "[" + sendTaskUid + "] blockProcess");
                break;
            } else if (DTMF.UserHangup == d) {
                JFLogger.logDebug(getClass(), "[" + sendTaskUid + "] User hangup > " + dtmf);
                break;
            } else if (this.loopMessage.equals(this.oneEightThree) && d == null) {
                JFLogger.logDebug(getClass(), "[" + sendTaskUid + "] User didn't press any DTMF");
                break;
            }
            this.loopMessage = this.oneEightThree;
        }
    }

    public void setIntro(final String intro) {

        this.intro = USER_UPLOADED_FILE_PATH_SOFT_LINK + "/" + intro;
    }

    public void setMessage(final String message) {

        this.message = USER_UPLOADED_FILE_PATH_SOFT_LINK + "/" + message;
    }

    @Override
    public String toString() {

        return "RecordedVoiceMessage{" + "messageFrom='" + messageFrom + '\'' + ", intro='" + intro + '\'' + ", message='" + message + '\'' + ", press1='" + press1 + '\'' + ", press7='" + press7 + '\'' + ", afterPress7='" + afterPress7 + '\'' + '}';
    }
}
