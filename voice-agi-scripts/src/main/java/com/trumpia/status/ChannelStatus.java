package com.trumpia.status;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Logan K. on 13. 12. 26.오후 3:42
 */

/**
 * Returns the status of the specified channel.
 * 
 * If no channel name is given the returns the status of the current channel.
 * 
 * Returns: failure: 200 result=-1 success: 200 result=<status>
 * 
 * <status> values: 0 Channel is down and available 1 Channel is down, but
 * reserved 2 Channel is off hook 3 Digits (or equivalent) have been dialed 4
 * Line is ringing 5 Remote end is ringing 6 Line is up 7 Line is busy
 */

// http://www.voip-info.org/wiki/view/Asterisk+variable+DIALSTATUS
public enum ChannelStatus {
    Channel_Is_Down_And_Available(0),

    Channel_Is_Down_But_Reserved(1),

    Channel_Is_Off_Hook(2),

    Digits_Have_Been_Dialed(3),

    Line_Is_Ringing(4),

    Remote_End_Is_Ringing(5),

    Line_Is_Up(6),

    Line_Is_Busy(7);

    private final int status;

    private ChannelStatus(final int status) {

        this.status = status;
    }

    public final static Map<String, ChannelStatus> lookup = new HashMap<String, ChannelStatus>();
    static {
        for (final ChannelStatus cs : ChannelStatus.values()) {
            lookup.put(cs.getStatusString(), cs);
        }
    }

    public static ChannelStatus get(final int status) {

        return lookup.get(String.valueOf(status));
    }

    public static ChannelStatus get(final String status) {

        return lookup.get(status);
    }

    public int getStatus() {

        return status;
    }

    public String getStatusString() {

        return String.valueOf(status);
    }
}
