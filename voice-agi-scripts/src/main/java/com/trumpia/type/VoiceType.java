package com.trumpia.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Logan K. on 13. 12. 20.오후 3:17
 */
public enum VoiceType {
    Male("0"), Female("1");

    public final static Map<String, VoiceType> lookup = new HashMap<String, VoiceType>();
    static {
        for (final VoiceType voiceType : VoiceType.values()) {
            lookup.put(voiceType.voieType(), voiceType);
        }
    }
    private final String                       voiceType;

    private VoiceType(final String voiceType) {

        this.voiceType = voiceType;
    }

    public String voieType() {

        return voiceType;
    }

    public static VoiceType get(final String voiceType) {

        return lookup.get(voiceType);
    }
}
