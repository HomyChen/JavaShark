package sample;

/**
 * Created by Homy Chen on 2017-04-02.
 */
public class UsageStatRow {

    private String ipAddress;
    private Long inboundRate;
    private Long outboundRate;
    private String inboundRatePer;
    private String outboundRatePer;

    public UsageStatRow(String ipAddress, long inboundRate, long outboundRate, String inboundRatePer, String outboundRatePer) {
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

    public String getInboundRatePer() {
        return inboundRatePer;
    }

    public String getOutboundRatePer() {
        return outboundRatePer;
    }

    public void setInboundRatePer(String inboundRatePer) {
        this.inboundRatePer = inboundRatePer;
    }

    public void setOutboundRatePer(String outboundRatePer) {
        this.outboundRatePer = outboundRatePer;
    }
}
