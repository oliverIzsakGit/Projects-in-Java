import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;

import java.util.ArrayList;

public class Port {
    private String interfaceName;
    private JBuffer lastPacket;
    private String portName;

    private Packet in;
    private Packet out;
    private int start=0;
    private int stop=0;
    private Pcap pcap;
    private ACLFilter acl;

    public int getStop() {
        return stop;
    }

    public Pcap getPcap() {
        return pcap;
    }

    public void setPcap(Pcap pcap) {
        this.pcap = pcap;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public Port(String interfaceName, PcapPacket lastPacket, String portName, Packet in, Packet out, SwitchMacTable macTable) {
        this.interfaceName = interfaceName;
        this.lastPacket = lastPacket;
        this.portName = portName;
        this.in = in;
        this.out = out;
        this.macTable = macTable;

    }

    public ACLFilter getAcl()
    {
        return acl;
    }

    public void setAcl(ACLFilter acl)
    {
        this.acl = acl;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public JBuffer getLastPacket() {
        return lastPacket;
    }

    public void setLastPacket(JBuffer lastPacket) {
        this.lastPacket = lastPacket;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public Packet getIn() {
        return in;
    }

    public void setIn(Packet in) {
        this.in = in;
    }

    public Packet getOut() {
        return out;
    }

    public void setOut(Packet out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "Port{" +
                "interfaceName='" + interfaceName + '\'' +
                ", portName='" + portName + '\'' +
                '}';
    }



    public SwitchMacTable getMacTable() {
        return macTable;
    }

    public void setMacTable(SwitchMacTable macTable) {
        this.macTable = macTable;
    }

    private SwitchMacTable macTable;
}
