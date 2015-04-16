package com.trumpia.scripts.outbound.messages;

/**
 * Created by Logan K. on 13. 12. 19.오후 4:02
 */
public class FemaleMent extends OutboundMent {

    private static final String FEMALE_PATH          = ASTERISK_DEFAULT_FILE_PATH + "/female";

    private static final String FEMALE_MESSAGE_FROM  = "/Message_From_1_F";
    private static final String FEMALE_PRESS_1       = "/1-800_3_F_1";
    private static final String FEMALE_PRESS_7       = "/Calls_Sender_2_F";
    private static final String FEMALE_AFTER_PRESS_7 = "/after_press7";
    private static final String FEMALE_1_800_3       = "/1-800_3_F_1";

    public FemaleMent() {

        this.messageFrom = FEMALE_PATH + FEMALE_MESSAGE_FROM;
        this.press1 = FEMALE_PATH + FEMALE_PRESS_1;
        this.press7 = FEMALE_PATH + FEMALE_PRESS_7;
        this.afterPress7 = FEMALE_PATH + FEMALE_AFTER_PRESS_7;
        this.oneEightThree = FEMALE_PATH + FEMALE_1_800_3;
    }
}
