import org.jnetpcap.Pcap;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PingThread extends Thread {

    private String ipAddresss ;
    private static final Map<String, PingThread> THREAD_MAP = new ConcurrentHashMap<>();

    private String macaddress;
    private SwitchMacTable smts;
    private volatile boolean stop = false;
    private long lastPacketTime = System.currentTimeMillis();;
 private Pcap pcap;
    public PingThread(String ipAddress,SwitchMacTable smt,String mac,
        Pcap pcap,int old,int newp) {
        this.ipAddresss = ipAddress;
        this.pcap=pcap;
        this.smts=smt;

        this.macaddress=mac;
        this.oldplug=old;
        this.unplug=newp;
    }

    public String getMacaddress()
    {
        return macaddress;
    }

    public void setMacaddress(String macaddress)
    {
        this.macaddress = macaddress;
    }

    public Thread getThread(String ip)
    {
       return THREAD_MAP.get(ip);
    }
    public String getIpAddresss() {
        return ipAddresss;
    }

  public boolean isStop()
  {
    return stop;
  }

  public void setStop(boolean stop)
  {
    this.stop = stop;
  }

  public void setIpAddresss(String ipAddresss) {
        this.ipAddresss = ipAddresss;
    }





    public SwitchMacTable getSmts() {
        return smts;
    }

    public int getUnplug()
    {
        return unplug;
    }

    public void setUnplug(int unplug)
    {
        this.unplug = unplug;
    }

    public void setSmts(SwitchMacTable smts) {
        this.smts = smts;
    }
     public int unplug=1;
    public int oldplug=0;
    @Override
    public void run() {
        while (!stop) {
            try {


              //  if (System.currentTimeMillis() - lastPacketTime > 5000) {
                //    System.out.println("No more traffic");
                  //  break;
                //}



                InetAddress address = InetAddress.getByName(ipAddresss);


                if (unplug>oldplug) {

                } else if(unplug<=oldplug){



                    Thread.sleep(1000);

                    if(unplug+7<=oldplug)
                    {
                        System.out.println("NO REPLY PROVIDED FROM" + address);
                        smts.removeMacAddress(macaddress);


                        break;
                    }

                }
                oldplug++;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
     stop = true;

        THREAD_MAP.remove(ipAddresss);
    }

    public boolean unplugged() {

        return stop;
    }


}