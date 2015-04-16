package com.trumpia.utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.trumpia.framework.core.JFLogger;
import com.trumpia.framework.util.status.VoiceStatus;
import com.trumpia.framework.util.voice.agi.env.VoiceAgiEnvFactor;
import com.trumpia.framework.util.voice.agi.params.VoiceInternalWsParams;
import com.trumpia.framework.util.voice.url.VoiceUrlPattern;

/**
 * Created by Logan K. on 13. 12. 22.오후 6:25
 */
public class VoiceUpdateStatusUtil {

    public static void updateReplay(final String sendTaskUid) {

        final VoiceAgiEnvFactor voiceAgiEnvFactor = new VoiceAgiEnvFactor();
        String onlySendTaskUid = null;
        try {
            onlySendTaskUid = voiceAgiEnvFactor.whereto(sendTaskUid);
        } catch (final Exception e) {
            JFLogger.logError(VoiceUpdateStatusUtil.class, "Exception in updateReplay > " + e.getMessage(), e);
        }
        JFLogger.logDebug(VoiceUpdateStatusUtil.class, "[" + onlySendTaskUid + "] updateReplay");
        requestUpdateWithOnlySendTaskUid(onlySendTaskUid, voiceAgiEnvFactor.getInternalApiUrl().getRequestUrl() + VoiceUrlPattern.InternalWs.URL_REST_BIZ_VOICE_UPDATE_ROOT + VoiceUrlPattern.InternalWs.REPLAY);
    }

    public static void blockProcess(final String sendTaskUid) {

        final VoiceAgiEnvFactor voiceAgiEnvFactor = new VoiceAgiEnvFactor();
        String onlySendTaskUid = null;
        try {
            onlySendTaskUid = voiceAgiEnvFactor.whereto(sendTaskUid);
        } catch (final Exception e) {
            JFLogger.logError(VoiceUpdateStatusUtil.class, "Exception in blockProcess > " + e.getMessage(), e);
        }
        JFLogger.logDebug(VoiceUpdateStatusUtil.class, "[" + onlySendTaskUid + "] blockProcess");
        requestUpdateWithOnlySendTaskUid(onlySendTaskUid, voiceAgiEnvFactor.getInternalApiUrl().getRequestUrl() + VoiceUrlPattern.InternalWs.URL_REST_BIZ_VOICE_UPDATE_ROOT + VoiceUrlPattern.InternalWs.BLOCK);
    }

    private static void requestUpdateWithOnlySendTaskUid(final String onlySendTaskUid, final String requestURL) {

        JFLogger.logDebug(VoiceUpdateStatusUtil.class, "[" + onlySendTaskUid + "] requestUpdateWithOnlySendTaskUid url > " + requestURL);

        final MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add(VoiceInternalWsParams.SENDTASK_UID, onlySendTaskUid);

        requestInternalApi(requestURL, params, onlySendTaskUid);
    }

    public static void updateReceivedStatusTrying(final String sendtaskUidWithDbInfo) {

        updateReceivedStatus(sendtaskUidWithDbInfo, VoiceStatus.ReceivedStatus.Trying);
    }

    public static void updateReceivedStatusConnected(final String sendtaskUidWithDbInfo) {

        updateReceivedStatus(sendtaskUidWithDbInfo, VoiceStatus.ReceivedStatus.Connected);
    }

    private static void updateReceivedStatus(final String sendtaskUidWithDbInfo, final VoiceStatus.ReceivedStatus receivedStatus) {

        JFLogger.logDebug(VoiceUpdateStatusUtil.class, "SendTaskUid > " + sendtaskUidWithDbInfo + " / ReceivedStatus > " + receivedStatus);
        final VoiceAgiEnvFactor voiceAgiEnvFactor = new VoiceAgiEnvFactor();
        String onlySendTaskUid = null;
        try {
            onlySendTaskUid = voiceAgiEnvFactor.whereto(sendtaskUidWithDbInfo);
        } catch (final Exception e) {
            JFLogger.logError(VoiceUpdateStatusUtil.class, "Exception in updateReceivedStatus > " + e.getMessage(), e);
        }
        String requestURL = voiceAgiEnvFactor.getInternalApiUrl().getRequestUrl() + VoiceUrlPattern.InternalWs.URL_REST_BIZ_VOICE_UPDATE_ROOT;

        switch (receivedStatus) {

            case Waiting:
                break;
            case Trying:
                requestURL += VoiceUrlPattern.InternalWs.RECEIVEDSTATUS_TRYING;
                break;
            case Connected:
                requestURL += VoiceUrlPattern.InternalWs.RECEIVEDSTATUS_CONNECTED;
                break;
            case Refund:
                break;
        }
        JFLogger.logDebug(VoiceUpdateStatusUtil.class, "[" + onlySendTaskUid + "] updateReceivedStatus");
        requestUpdateWithOnlySendTaskUid(onlySendTaskUid, requestURL);
    }

    private static void requestInternalApi(final String requestURL, final MultivaluedMap<String, String> params, final String sendTaskUid) {

        final Client client = Client.create();
        try {
            final WebResource webResource = client.resource(requestURL);

            JFLogger.logDebug(VoiceUpdateStatusUtil.class, "[" + sendTaskUid + "] request data > " + params);
            final ClientResponse response = webResource.queryParams(params).type(MediaType.APPLICATION_JSON).put(ClientResponse.class);

            final ClientResponse.Status clientResponseStatus = response.getClientResponseStatus();
            JFLogger.logDebug(VoiceUpdateStatusUtil.class, "[" + sendTaskUid + "] response status < " + clientResponseStatus);
            switch (clientResponseStatus) {
                case BAD_REQUEST:
                case UNAUTHORIZED:
                case PAYMENT_REQUIRED:
                case FORBIDDEN:
                case NOT_FOUND:
                case METHOD_NOT_ALLOWED:
                case NOT_ACCEPTABLE:
                case PROXY_AUTHENTICATION_REQUIRED:
                case GONE:
                case LENGTH_REQUIRED:
                case PRECONDITION_FAILED:
                case REQUEST_ENTITY_TOO_LARGE:
                case REQUEST_URI_TOO_LONG:
                case UNSUPPORTED_MEDIA_TYPE:
                case REQUESTED_RANGE_NOT_SATIFIABLE:
                case EXPECTATION_FAILED:
                    JFLogger.logDebug(VoiceUpdateStatusUtil.class, "[" + sendTaskUid + "] invalid response status < " + clientResponseStatus);
                    break;
            }
        } catch (Exception e) {
            JFLogger.logError(VoiceUpdateStatusUtil.class, "Exception occurred in VoiceUpdateStatusUtil > " + e.getMessage(), e);
        } finally {
            client.destroy();
        }
    }
}
