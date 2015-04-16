package com.trumpia.scripts.auth.messages;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;

import com.trumpia.dtmf.DTMF;
import com.trumpia.framework.util.voice.agi.params.VoiceServletParams;
import com.trumpia.scripts.VoiceMent;

/**
 * Created by Logan K. on 14. 1. 10.오후 5:01
 */
public class AuthMent implements VoiceMent {

    private static final String AUTH_CALL_PATH = ASTERISK_DEFAULT_FILE_PATH + "/authcall";

    private static final String PLAY1_MESSAGE  = "/authcall_play_1";
    private static final String PLAY2_MESSAGE  = "/authcall_play_2";
    private static final String PLAY3_MESSAGE  = "/authcall_play_3";
    private static final String PLAY4_MESSAGE  = "/authcall_play_4";

    private String              mentPlay1      = AUTH_CALL_PATH + PLAY1_MESSAGE;
    private String              mentPlay2      = AUTH_CALL_PATH + PLAY2_MESSAGE;
    private String              mentPlay3      = AUTH_CALL_PATH + PLAY3_MESSAGE;
    private String              mentPlay4      = AUTH_CALL_PATH + PLAY4_MESSAGE;

    @Override
    public void play(final AgiRequest agiRequest, final AgiChannel agiChannel) throws AgiException {

        final String code = agiChannel.getVariable(VoiceServletParams.Auth.CODE);

        agiChannel.streamFile(mentPlay1);
        agiChannel.streamFile(mentPlay2);
        while (true) {
            agiChannel.streamFile(mentPlay3);
            agiChannel.sayDigits(code);
            final String dtmf = agiChannel.getData(mentPlay4, WAIT_TIME_OUT_DTMF_3000, 1);
            final DTMF d = DTMF.get(dtmf);
            if (DTMF.Asterisk == d) {
                continue;
            }
            break;
        }
    }

    @Override
    public String toString() {

        return "AuthMent{" + "mentPlay1='" + mentPlay1 + '\'' + ", mentPlay2='" + mentPlay2 + '\'' + ", mentPlay3='" + mentPlay3 + '\'' + ", mentPlay4='" + mentPlay4 + '\'' + '}';
    }
}
