package sample;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jnetpcap.*;
import org.jnetpcap.packet.*;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import org.jnetpcap.util.PcapPacketArrayList;

import static sample.Controller.packetInfo;

/**

 * Doing some IO functions related to PCAP files.
 */
public class pcap {

    /*************************************************
     * Local Variables
     *************************************************/
    String FileAddress = "";
    private int tcpCount = 0;
    private int udpCount = 0;



    /**
     *
     * @param FileAddress  Address and the name of the PCAP file.
     */
    public pcap(String FileAddress)
    {
        this.FileAddress = FileAddress;
    }
    public int getTcpCount(){
        return  tcpCount;
    }
    public int getUdpCount(){
        return udpCount;
    }

    /**
     * Opens the offline Pcap-formatted file.
     *
     * @return PcapPacketArrayList  List of packets in the file
     * @throws ExceptionReadingPcapFiles Facing any erro in opening the file
     */
    public PcapPacketArrayList readOfflineFiles() throws ExceptionReadingPcapFiles
    {
        //First, setup error buffer and name for our file
        final StringBuilder errbuf = new StringBuilder(); // For any error msgs

        //Second ,open up the selected file using openOffline call
        Pcap pcap = Pcap.openOffline(FileAddress, errbuf);
        //System.out.println("File address is:" + FileAddress); // Fan testing1


        //Throw exception if it cannot open the file
        if (pcap == null) {
            System.out.println("Could not open pcap file....");
            throw new ExceptionReadingPcapFiles(errbuf.toString());

        }

        //Next, we create a packet handler which will receive packets from the libpcap loop.
        PcapPacketHandler<PcapPacketArrayList> jpacketHandler = new PcapPacketHandler<PcapPacketArrayList>() {

            public void nextPacket(PcapPacket packet, PcapPacketArrayList PacketsList) {
                //System.out.println("Step in-----------nextPacket method");
                Ip4 ip = new Ip4();
                Ip6 ip1 = new Ip6();
                Tcp tcp = new Tcp();
                Udp udp = new Udp();

                if(packet.hasHeader(tcp)){
                    //System.out.println("TCP");
                    tcpCount++;
                    //System.out.println("TCP number" + tcpCount);
                } else if (packet.hasHeader(udp)) {
                    //System.out.println("UDP");
                    udpCount++;
                    //System.out.println("UDP number" + udpCount);
                }
                //---Fan testing--- Print out information from packet
               /* System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
                        new Date(packet.getCaptureHeader().timestampInMillis()),
                        packet.getCaptureHeader().caplen(),  // Length actually captured
                        packet.getCaptureHeader().wirelen(), // Original length
                        "Fan testing"                                 // placeholder
                );


//Create loop to add value into Jtable
                for(int i=0;i<PacketsList.size();i++)
                {
                    PcapPacket Packet= PacketsList.get(i);

                   // Timestamp timestamp = new Ip4.Timestamp(Packet.getCaptureHeader().timestampInMillis());
                    packetInfo[i][0]=i+1;
                    packetInfo[i][1]=new Date(Packet.getCaptureHeader().timestampInMillis());
                    Packet.getHeader(ip);
                    if(Packet.hasHeader(ip))
                    {
                        packetInfo[i][2]= FormatUtils.ip(ip.source());
                        packetInfo[i][3]=FormatUtils.ip(ip.destination());

                    }
                    packetInfo[i][4]=Packet
                    Packet.getHeader(tcp);
                    if (Packet.hasHeader(tcp))
                    {
                        obj[i][3] = new Integer(tcp.destination()).toString();
                        obj[i][5] = new Integer(tcp.source()).toString();
                    }
                    obj[i][6]=Packet.toString();
                }
                DefaultTableModel model= new DefaultTableModel(obj, col);

                jTable1.setModel(model);*/
                PacketsList.add(packet);

                Integer i = new Integer(PacketsList.size());

                Integer l = new Integer(packet.getCaptureHeader().wirelen());

              //  byte[] data = packet.getByteArray(0, packet.size());
               // byte[] sIP = new byte[4]; // Should be outside the callback method for efficiency
               // byte[] dIP = new byte[4];
                if (packet.hasHeader(ip) == false && packet.hasHeader(ip1)== false) {
                    return; // Not IP packet
                }

                String sourceIP = "";
                String destinationIP = "";
                //ip.source(sIP);
               // ip.destination(dIP);
                if(packet.hasHeader(ip)){
                sourceIP = org.jnetpcap.packet.format.FormatUtils.ip(packet.getHeader(ip).source());
               destinationIP = org.jnetpcap.packet.format.FormatUtils.ip(packet.getHeader(ip).destination());}
                else{
                    sourceIP = org.jnetpcap.packet.format.FormatUtils.ip(packet.getHeader(ip1).source());
                     destinationIP = org.jnetpcap.packet.format.FormatUtils.ip(packet.getHeader(ip1).destination());
                }



                Date date = new Date(packet.getCaptureHeader().timestampInMicros());
                Format formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
                String t = formatter.format(date);

                String s = "";
                final JHeaderPool headers = new JHeaderPool();
                int id = packet.getHeaderIdByIndex(packet.getHeaderCount()-1);
                int id1 = packet.getHeaderIdByIndex(packet.getHeaderCount()-2);
                // Numerical ID of the header
                 JHeader header = headers.getHeader(id);
                 JHeader header1 = headers.getHeader(id1);
                if(header.getName().equals("Payload")){
                s = header1.getName();
                }else{

                    s = header.getName();
                }

                //packetInfo.add(new packetProperty(i, t, FormatUtils.ip(ip.source()), FormatUtils.ip(ip.destination()),"TCP", l));
                packetInfo.add(new packetProperty(i, t, sourceIP, destinationIP,s, l));

                //System.out.println("testing1:" + Controller.packetInfo.get(PacketsList.size()-1).index + " "+ Controller.packetInfo.get(PacketsList.size()-1).time);

                //System.out.println("Step in-----------nextPacket method ----7");
            }
        };

        /***************************************************************************
         * (From jNetPcap comments:)
         * Fourth we enter the loop and tell it to capture unlimited packets. The loop
         * method does a mapping of pcap.datalink() DLT value to JProtocol ID, which
         * is needed by JScanner. The scanner scans the packet buffer and decodes
         * the headers. The mapping is done automatically, although a variation on
         * the loop method exists that allows the programmer to specify exactly
         * which protocol ID to use as the data link type for this pcap interface.
         **************************************************************************/

        try {
            PcapPacketArrayList packets = new PcapPacketArrayList();
            pcap.loop(-1,jpacketHandler,packets);

            return packets;
        } finally {
            //Last thing to do is close the pcap handle
            pcap.close();
        }
    }


    /****------HOMY/IRIS------START****/
    public void printPacketsArrayList() throws ExceptionReadingPcapFiles {
        PcapPacketArrayList pcapPacketArrayList = this.readOfflineFiles();
        int totalDataInBytes = 0;
        long timeStampBegin = 0;
        long timeStampEnd = 0;
        for(PcapPacket packet : pcapPacketArrayList){
            if(packet.equals(pcapPacketArrayList.get(0))){
                timeStampBegin = packet.getCaptureHeader().timestampInNanos();
            }
            if(packet.equals(pcapPacketArrayList.get(pcapPacketArrayList.size()-1))){
                timeStampEnd = packet.getCaptureHeader().timestampInNanos();
            }
            //---Fan testing--- Print out information from packet
            System.out.printf("\nReceived packet at %s caplen=%-4d len=%-4d %s\n",
                    new Date(packet.getCaptureHeader().timestampInMillis()),
                    packet.getCaptureHeader().caplen(),  // Length actually captured
                    packet.getCaptureHeader().wirelen(), // Original length
                    ""                                 // placeholder
            );
            //Add the packet length to the total (for measurement)
            totalDataInBytes = totalDataInBytes + packet.getCaptureHeader().wirelen();
            //If the packet has an ip header, print IP addresses
            Ip4 ip = new Ip4();
            Tcp tcp = new Tcp();
            if (packet.hasHeader(ip)) {
                try {
                    //Source IP
                    InetAddress ip4Src = InetAddress.getByAddress(ip.source());
                    System.out.println("Source IP: "+ip4Src.toString());
                    //Destination IP
                    InetAddress ip4Dest = InetAddress.getByAddress(ip.destination());
                    System.out.println("Destination IP: "+ip4Dest.toString());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
            //If it is a TCP packet, print TCP flag
            if(packet.hasHeader(tcp)) {
                System.out.println("TCP Flag: "+returnFlags(tcp));
            }
        }
        System.out.println("\nTotal data in .pcap file (bytes): "+totalDataInBytes);
        double totalTimeSeconds = ((timeStampEnd-timeStampBegin)/(1e9));
        System.out.println("Time Covered in .pcap file (seconds): "+totalTimeSeconds);
        System.out.println("Average Data Rate (kb/s): "+((totalDataInBytes/1000)/totalTimeSeconds));
    }

    public int getTotalData() throws ExceptionReadingPcapFiles {
        PcapPacketArrayList pcapPacketArrayList = this.readOfflineFiles();
        int totalDataInBytes = 0;
        for(PcapPacket packet : pcapPacketArrayList){
            totalDataInBytes = totalDataInBytes + packet.getCaptureHeader().wirelen();
        }
        return totalDataInBytes;
    }

    public double getTotalTime() throws ExceptionReadingPcapFiles {
        PcapPacketArrayList pcapPacketArrayList = this.readOfflineFiles();
        long timeStampBegin = 0;
        long timeStampEnd = 0;
        for(PcapPacket packet : pcapPacketArrayList){
            if(packet.equals(pcapPacketArrayList.get(0))){
                timeStampBegin = packet.getCaptureHeader().timestampInNanos();
            }
            if(packet.equals(pcapPacketArrayList.get(pcapPacketArrayList.size()-1))){
                timeStampEnd = packet.getCaptureHeader().timestampInNanos();
            }
        }
        double totalTimeSeconds = ((timeStampEnd-timeStampBegin)/(1e9));
        return totalTimeSeconds;
    }

    private HashSet<String> getAllAddresses() throws ExceptionReadingPcapFiles {
        PcapPacketArrayList packets = this.readOfflineFiles();
        HashSet<String> ipAddresses = new HashSet<>();
        Ip4 ip = new Ip4();
        Ip6 ip6 = new Ip6();
        for(PcapPacket packet : packets){
            if (packet.hasHeader(ip)){
                try {
                    InetAddress ip4Src = InetAddress.getByAddress(ip.source());
                    ipAddresses.add(ip4Src.toString());
                    InetAddress ip4Dest = InetAddress.getByAddress(ip.destination());
                    ipAddresses.add(ip4Dest.toString());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
            if(packet.hasHeader(ip6)){
                try {
                    InetAddress ip6Src = InetAddress.getByAddress(ip6.source());
                    ipAddresses.add(ip6Src.toString());
                    InetAddress ip6Dest = InetAddress.getByAddress(ip6.destination());
                    ipAddresses.add(ip6Dest.toString());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        return ipAddresses;
    }

    public HashMap<String, ArrayList<Long>> getUsageStat() throws ExceptionReadingPcapFiles {
        HashMap<String, ArrayList<Long>> usageStat = new HashMap<>();
        PcapPacketArrayList packets = this.readOfflineFiles();
        Ip4 ip4 = new Ip4();
        Ip6 ip6 = new Ip6();
        HashSet<String> ipSet = this.getAllAddresses();
        //Add all IP Addresses into HashMap as keys
        for(String ipAddress : ipSet){
            ArrayList<Long> newArray = new ArrayList<>(2);
            //Array index 0 for inbound data in bytes - starts at 0 bytes
            newArray.add((long) 0);
            //Array index 1 for outbound data in bytes - starts at 0 bytes
            newArray.add((long) 0);
            //Put Arrays and IP Address into the HashMap
            usageStat.put(ipAddress, newArray);
        }
        //Iterate over packets and add data into HashMap array
        for(PcapPacket packet : packets){
            if (packet.hasHeader(ip4)){
                try {
                    //Search through HashMap for the destination IP of this packet as the key, add the packet length into inbound part of the array (index 0)
                    InetAddress ip4Dest = InetAddress.getByAddress(ip4.destination());
                    String ip4DestString = ip4Dest.toString();
                    long oldInbound = usageStat.get(ip4DestString).get(0);
                    usageStat.get(ip4DestString).set(0, (oldInbound + (long) packet.getCaptureHeader().wirelen()));

                    //Search through HashMap for the source IP of this packet as the key, add the packet length into outbound part of the array (index 1)
                    InetAddress ip4Src = InetAddress.getByAddress(ip4.source());
                    String ip4SrcString = ip4Src.toString();
                    long oldOutbound = usageStat.get(ip4SrcString).get(1);
                    usageStat.get(ip4SrcString).set(1, (oldOutbound + (long) packet.getCaptureHeader().wirelen()));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
            if(packet.hasHeader(ip6)){
                try {
                    //Search through HashMap for the destination IP of this packet as the key, add the packet length into inbound part of the array (index 0)
                    InetAddress ip6Dest = InetAddress.getByAddress(ip6.destination());
                    String ip6DestString = ip6Dest.toString();
                    long oldInbound = usageStat.get(ip6DestString).get(0);
                    usageStat.get(ip6DestString).set(0, (oldInbound + (long) packet.getCaptureHeader().wirelen()));

                    //Search through HashMap for the source IP of this packet as the key, add the packet length into outbound part of the array (index 1)
                    InetAddress ip6Src = InetAddress.getByAddress(ip6.source());
                    String ip6SrcString = ip6Src.toString();
                    long oldOutbound = usageStat.get(ip6SrcString).get(1);
                    usageStat.get(ip6SrcString).set(1, (oldOutbound + (long) packet.getCaptureHeader().wirelen()));

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        }
        return usageStat;
    }
    /****------HOMY/IRIS------END****/

    /****------HOMY------START****/
    // Homy -- own method for finding TCP flags - the one from jNet, Tcp.flagsEnum(), does not seem to work
    private ArrayList returnFlags(Tcp tcp){
        ArrayList<String> flags = new ArrayList<String>();
        Map<String, Boolean> flagsMap = new HashMap<String, Boolean>();
        flagsMap.put("CWR", tcp.flags_CWR());
        flagsMap.put("ECE", tcp.flags_ECE());
        flagsMap.put("URG", tcp.flags_URG());
        flagsMap.put("ACK", tcp.flags_ACK());
        flagsMap.put("PSH", tcp.flags_PSH());
        flagsMap.put("RST", tcp.flags_RST());
        flagsMap.put("SYN", tcp.flags_SYN());
        flagsMap.put("FIN", tcp.flags_FIN());
        Set<String> flagsSet = flagsMap.keySet();
        for(String s : flagsSet){
            if(flagsMap.get(s)){
                flags.add(s);
            }
        }
        return flags;
    }

    /****------HOMY------END****/
}

