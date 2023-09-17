import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Packet {

    private int ETHERNET_II;

    private int ICMP;
    private int UDP;
    private int TCP;
    private int IP;
    private int HTTP;
    private int HTTPS;
    private int ARP;
    private int totalPackets;
    private int bytes;
    private int Undefined;


    public int getETHERNET_II() {
        return ETHERNET_II;
    }

    public void setETHERNET_II(int ETHERNET_II) {
        this.ETHERNET_II = ETHERNET_II;
    }

    public int getICMP() {
        return ICMP;
    }

    public void setICMP(int ICMP) {
        this.ICMP = ICMP;
    }

    public int getUDP() {
        return UDP;
    }

    public void setUDP(int UDP) {
        this.UDP = UDP;
    }

    public int getTCP() {
        return TCP;
    }

    public void setTCP(int TCP) {
        this.TCP = TCP;
    }

    public int getIP() {
        return IP;
    }

    public void setIP(int IP) {
        this.IP = IP;
    }

    public int getHTTP() {
        return HTTP;
    }

    public void setHTTP(int HTTP) {
        this.HTTP = HTTP;
    }

    public int getHTTPS() {
        return HTTPS;
    }

    public void setHTTPS(int HTTPS) {
        this.HTTPS = HTTPS;
    }

    public int getARP() {
        return ARP;
    }

    public void setARP(int ARP) {
        this.ARP = ARP;
    }

    public int getTotalPackets() {
        return totalPackets;
    }

    public void setTotalPackets(int totalPackets) {
        this.totalPackets = totalPackets;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public int getUndefined() {
        return Undefined;
    }

    public void setUndefined(int undefined) {
        Undefined = undefined;
    }

    public Packet() {

        this.ETHERNET_II = 0;
        this.ICMP = 0;
        this.UDP = 0;
        this.TCP = 0;
        this.IP = 0;
        this.HTTP = 0;
        this.HTTPS = 0;
        this.ARP = 0;
        this.totalPackets =0;
        this.bytes = 0;
    }
    public void reset()
    {
        System.out.println("RESETGING IN PACKET CLASS");
        this.ETHERNET_II = 0;
        System.out.println("this : " + this.ETHERNET_II);
        this.ICMP = 0;
        System.out.println("this : " + this.ICMP);
        this.UDP = 0;
        this.TCP = 0;
        this.IP = 0;
        this.HTTP = 0;
        this.HTTPS = 0;
        this.ARP = 0;
        this.totalPackets =0;
        System.out.println("this : " + this.totalPackets);
        this.bytes = 0;
    }

    public String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for(byte b : bytes) {
            sb.append(String.format("%02x", b&0xff));
        }

        return sb.toString();
    }
    public ArrayList<String> categorize(PcapPacket packet)
    { int pdu;

        JBuffer buffer = packet;
        String mpd= bytesToHexString(packet.getByteArray(6, 6));


        pdu = buffer.getUShort(12);
        int tl = buffer.getUShort(16);
        bytes+=tl+14;
        totalPackets++;
        ArrayList<String> iplist = new ArrayList<>();
        byte ips[] = buffer.getByteArray(26, 4);
        byte ipd[] = buffer.getByteArray(30, 4);
        String ipd1 = bytesToHexString(ipd);
        String ips1 = bytesToHexString(ips);


        if (pdu >= 1536) {
            ETHERNET_II++;
            if (pdu == 2054)
            {ARP++;

                ipd = buffer.getByteArray(28, 4);
                ips = buffer.getByteArray(38, 4);
                ipd1 = bytesToHexString(ipd);
             ips1 = bytesToHexString(ips);

            }
            else if(pdu==34525){IP++;}
            else if (pdu == 2048) {IP++;

                if(buffer.getUByte(23)==1)
                {ICMP++;}
                else    if(buffer.getUByte(23)==17)
                {
                    UDP++;
                }
                else    if(buffer.getUByte(23)==6)
                {
                    TCP++;
                    if(buffer.getUShort(34)==443)
                    {
                        HTTPS++;
                    }else if(buffer.getUShort(34)==80)
                    {HTTP++;
                    }
                    else if(buffer.getUShort(36)==80)
                    {HTTP++;
                    }
                    else if(buffer.getUShort(36)==443)
                    {
                        HTTPS++;
                    }
                    else {
                        Undefined++;
                    }
                }else {
                   Undefined++;
                }

            }
        }
        else if (pdu > 0 && pdu <= 1500) {
Undefined++;
        }
        iplist.add(hexToIpAddress(ipd1));
        iplist.add(hexToIpAddress(ips1));
        iplist.add(mpd);


        return iplist;

    }
    public  String hexToIpAddress(String hex) {
        String[] parts = new String[4];
        for (int i = 0; i < 4; i++) {
            String hexPart = hex.substring(i * 2, i * 2 + 2);
            parts[i] = Integer.parseInt(hexPart, 16) + "";
        }
        return String.join(".", parts);
    }
    public ArrayList<String> getChange() {
        ArrayList<String> ar =new ArrayList<String>();
        ar.add(String.valueOf(getETHERNET_II()));
        ar.add(String.valueOf(getIP()));
        ar.add(String.valueOf(getARP()));  ar.add(String.valueOf(getTCP()));
        ar.add(String.valueOf(getUDP()));

        ar.add(String.valueOf(getICMP()));
        ar.add(String.valueOf(getHTTP()));
        ar.add(String.valueOf(getHTTPS()));
        ar.add(String.valueOf(getTotalPackets()));
        return ar;

    }

    public String byteToeHex(byte b) {
        int i = b & 0xFF;
        return Integer.toHexString(i);
    }
}
