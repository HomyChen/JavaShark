package sample;

/**
 * Created by Homy Chen on 2017-04-02.
 */
public class UsageStatRow {

    private String ipAddress;
    private Long inboundRate;
    private Long outboundRate;
    private double inboundRatePer;
    private double outboundRatePer;

    public UsageStatRow(String ipAddress, long inboundRate, long outboundRate, double inboundRatePer, double outboundRatePer) {
        this.ipAddress = ipAddress;
        this.inboundRate = inboundRate;
        this.outboundRate = outboundRate;
        this.inboundRatePer = inboundRatePer;
        this.outboundRatePer = outboundRatePer;
    }

    public UsageStatRow(String ipAddress){
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public long getInboundRate() {
        return inboundRate;
    }

    public long getOutboundRate() {
        return outboundRate;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setInboundRate(long inboundRate) {
        this.inboundRate = inboundRate;
    }

    public void setOutboundRate(long outboundRate) {
        this.outboundRate = outboundRate;
    }

    public double getInboundRatePer() {
        return inboundRatePer;
    }

    public double getOutboundRatePer() {
        return outboundRatePer;
    }

    public void setInboundRatePer(double inboundRatePer) {
        this.inboundRatePer = inboundRatePer;
    }

    public void setOutboundRatePer(double outboundRatePer) {
        this.outboundRatePer = outboundRatePer;
    }
}
