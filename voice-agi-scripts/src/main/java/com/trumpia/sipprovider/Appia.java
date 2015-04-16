package com.trumpia.sipprovider;

/**
 * Created by Logan K. on 14. 1. 13.오전 9:30
 */
public class Appia extends SipProvider {

    @Override
    public String trunk() {

        return "SIP";
    }

    @Override
    public String proxyDomain() {

        return "appia";
    }

    @Override
    public String dialOptions() {

        return ""; // ex) ",60"
    }
}
