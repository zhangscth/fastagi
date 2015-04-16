import com.trumpia.framework.util.voice.agi.params.VoiceServletParams;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by Logan K. on 13. 12. 23.오후 4:35
 */
public class TestMain {

    public static void main(String[] args) {

        System.out.println("1 4159441104");
        String toolData = StringUtils.replace("1 4159441104", "\\s", "");
        System.out.println(toolData);
        toolData = toolData.replaceAll("\\s", "");
        System.out.println(toolData);

        // try {
        // System.out.println(InetAddress.getLocalHost().getHostName());
        // System.out.println(InetAddress.getLocalHost().getHostAddress());
        // } catch (UnknownHostException e) {
        // e.printStackTrace();
        // }
        // System.out.println(getLocalServerIp());
        // System.out.println(runIfConfigCommand("ifconfig"));
        // System.out.println(ParseMacAddress(runIfConfigCommand("ifconfig")));
    }

    private static String getLocalServerIp() {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    System.out.println(inetAddress);
                    System.out.println(inetAddress.isLoopbackAddress());
                    System.out.println(inetAddress.isLinkLocalAddress());
                    System.out.println(inetAddress.isSiteLocalAddress());
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                    System.out.println("--------------------------------------");
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

    private final static String runIfConfigCommand(String command) {

        String outputText = null;
        try {
            Process p = Runtime.getRuntime().exec(command);
            InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
            StringBuffer buffer = new StringBuffer();
            for (;;) {
                int c = stdoutStream.read();
                if (c == -1)
                    break;
                buffer.append((char) c);
            }
            outputText = buffer.toString();
            stdoutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputText;
    }

    public static String ParseMacAddress(String text) {

        String result = null;
        String[] list = text.split("\\p{XDigit}{2}(-\\p{XDigit}{2}){5}");
        int index = 0;
        for (String str : list) {
            if (str.length() < text.length()) {
                index = str.length();
                result = text.substring(index, index + 17);
                if (!result.equals("00-00-00-00-00-00")) {
                    break;
                }
                text = text.substring(index + 17);

            }
        }
        return result;
    }
}
