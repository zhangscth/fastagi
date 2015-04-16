package com.trumpia.dtmf;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Logan K. on 13. 12. 26.오후 4:36
 */
public enum DTMF {
    UserHangup("-1"),

    Zero("0"),

    One("1"),

    Two("2"),

    Three("3"),

    Four("4"),

    Five("5"),

    Six("6"),

    Seven("7"),

    Eight("8"),

    Nine("9"),

    Sharp("#"),

    Asterisk("*");

    private final String                  dtmf;
    public final static Map<String, DTMF> lookup = new HashMap<String, DTMF>();

    DTMF(final String dtmf) {

        this.dtmf = dtmf;
    }

    public String getDtmf() {

        return dtmf;
    }

    static {
        for (final DTMF d : DTMF.values()) {
            lookup.put(d.getDtmf(), d);
        }
    }

    public static DTMF get(String dtmf) {

        return lookup.get(dtmf);
    }
}
