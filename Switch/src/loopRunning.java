import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacketHandler;

public class loopRunning  extends Thread{
    private Pcap p;
    private PcapPacketHandler<String> port2;
    private String name;


    public void setP(Pcap p) {
        this.p = p;
    }

    public void setPort2(PcapPacketHandler<String> port2) {
        this.port2 = port2;
    }

    public void setNameP(String name) {
        this.name = name;
    }

    @Override public void run(){

        System.out.println("IAM RUNNINGGG G G G G G G G G "+ name);
        p.loop(Pcap.LOOP_INFINITE, port2, name);
     }

}
