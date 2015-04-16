package com.trumpia.sipprovider;

/**
 * Created by Logan K. on 14. 1. 13.오전 9:32
 */
public abstract class SipProvider {

    public SipProvider() {

    }

    public String destination(final String toolData) {

        return this.trunk() + "/" + toolData + "@" + this.proxyDomain() + this.dialOptions();
    }

    public abstract String trunk();

    public abstract String proxyDomain();

    public abstract String dialOptions();
}
