package com.tousie.securities.common.async;

public class SchedulingProperties {

    private String mdcPrefix;
    private String cron;

    public String getMdcPrefix() {
        return mdcPrefix;
    }

    public void setMdcPrefix(String mdcPrefix) {
        this.mdcPrefix = mdcPrefix;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
