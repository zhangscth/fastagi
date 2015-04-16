package com.trumpia.scripts;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;

/**
 * Created by Logan K. on 14. 1. 10.오후 5:11
 */
public interface VoiceMent {

    String                  ASTERISK_DEFAULT_FILE_PATH        = "/var/lib/asterisk/sounds/trumpia";
    String                  USER_UPLOADED_FILE_PATH_SOFT_LINK = ASTERISK_DEFAULT_FILE_PATH + "/voice";
    String                  USER_UPLOADED_REAL_FILE_PATH      = "/usr/local/trumpia/files/voice";

    public static final int WAIT_TIME_OUT_DTMF_1000           = 1000;
    public static final int WAIT_TIME_OUT_DTMF_2000           = 2000;
    public static final int WAIT_TIME_OUT_DTMF_3000           = 3000;
    public static final int WAIT_TIME_OUT_DTMF_5000           = 5000;

    void play(final AgiRequest agiRequest, final AgiChannel agiChannel) throws AgiException;
}
