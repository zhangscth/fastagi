package com.trumpia.server;

import org.asteriskjava.fastagi.AgiServer;
import org.asteriskjava.fastagi.AgiServerThread;
import org.asteriskjava.fastagi.DefaultAgiServer;

import com.trumpia.framework.core.JFLogger;

/**
 * Created by Logan K. on 13. 12. 19.오후 5:17
 */
public class VoiceAgiServer {

    static boolean                 status;

    private static AgiServerThread agiServerThread;

    static {

        // https://github.com/srt/asterisk-java/blob/master/src/main/java/org/asteriskjava/fastagi/DefaultAgiServer.java
        final AgiServer agiServer = new DefaultAgiServer();
        agiServerThread = new AgiServerThread();
        status = false;
        agiServerThread.setAgiServer(agiServer);
    }

    private VoiceAgiServer() {

    }

    public static synchronized void startup() {

        JFLogger.logDebug(VoiceAgiServer.class, "VoiceAgiServer startup.");
        if (status)
            return;

        agiServerThread.setDaemon(false); // should be false;
        agiServerThread.startup();
        status = true;
    }

    public static synchronized void shutdown() {

        if (!status)
            return;

        agiServerThread.shutdown();
        status = false;
    }

    public static boolean getStatus() {

        return status;
    }

    public static void main(String[] args) {

        VoiceAgiServer.startup();
    }
}
