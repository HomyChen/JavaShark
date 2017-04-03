package sample;

import org.jnetpcap.packet.PcapPacket;

/**
 * Created by Ariest on 2017-03-31.
 */
public class packetProperty {


    //private PcapPacket packet;
    private Integer index;
    private String time;
    private String IpSrc;
    private String IpDst;
    private String protocol;
    private Integer length;
    public packetProperty(Integer index,String time, String IpSrc, String IpDst, String protocol, Integer length){
        this.index = index;
        this.time = time;
        this.IpSrc = IpSrc;
        this.IpDst = IpDst;
        this.protocol = protocol;
        this.length = length;
    }
    public Integer getIndex() {
        return index;
    }
    public String getTime() {
        return time;
    }


    public String getIpSrc() {
        return IpSrc;
    }

    public void setIpSrc(String ipSrc) {
        IpSrc = ipSrc;
    }

    public String getIpDst() {
        return IpDst;
    }

    public void setIpDst(String ipDst) {
        IpDst = ipDst;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
