package com.trumpia.status;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Logan K. on 13. 12. 27.오후 12:51
 */
public enum DialStatus {
    ANSWER,

    BUSY,

    NOANSWER,

    CANCEL,

    CONGESTION,

    CHANUNAVAIL,

    DONTCALL,

    TORTURE,

    INVALIDARGS;

    public final static Map<String, DialStatus> lookup = new HashMap<String, DialStatus>();
    static {
        for (final DialStatus ds : DialStatus.values()) {
            lookup.put(ds.name(), ds);
        }
    }

    public static DialStatus get(final String status) {

        return lookup.get(status);
    }
}
