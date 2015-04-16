package com.trumpia.sipprovider;

/**
 * Created by Logan K. on 14. 1. 13.오전 9:30
 */
public class Innodial extends SipProvider {

    @Override
    public String trunk() {

        return "SIP";
    }

    @Override
    public String proxyDomain() {

        return "innodial";
    }

    @Override
    public String dialOptions() {

        return ""; // ex) ",60"
    }
}
