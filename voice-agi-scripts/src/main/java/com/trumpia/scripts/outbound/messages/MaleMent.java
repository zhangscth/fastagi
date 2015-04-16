package com.trumpia.scripts.outbound.messages;

/**
 * Created by Logan K. on 13. 12. 19.오후 4:02
 */
public class MaleMent extends OutboundMent {

    private static final String MALE_PATH          = ASTERISK_DEFAULT_FILE_PATH + "/male";

    private static final String MALE_MESSAGE_FROM  = "/Message_From_1";
    private static final String MALE_PRESS_1       = "/1-800_3";
    private static final String MALE_PRESS_7       = "/Calls_Sender_2";
    private static final String MALE_AFTER_PRESS_7 = "/after_press7";
    private static final String MALE_1_800_3       = "/1-800_3";

    public MaleMent() {

        this.messageFrom = MALE_PATH + MALE_MESSAGE_FROM;
        this.press1 = MALE_PATH + MALE_PRESS_1;
        this.press7 = MALE_PATH + MALE_PRESS_7;
        this.afterPress7 = MALE_PATH + MALE_AFTER_PRESS_7;
        this.oneEightThree = MALE_PATH + MALE_1_800_3;
    }
}
