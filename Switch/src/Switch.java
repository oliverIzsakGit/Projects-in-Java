import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapIf;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.desktop.AppEvent;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Switch extends Application
{
  private int pcn1=0;
  private int pcn2=0;
  private int counter =0;
  private Map<String, ScheduledExecutorService> pingThreads;
  public static ArrayList<String> devices;
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  PcapPacketHashTable packetTable = PcapPacketHashTable.getInstance();





  @Override
  public void start(Stage primaryStage) throws Exception {
    //GUI SETUP
    FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
    Parent root = loader.load();
    primaryStage.setTitle("Network Traffic");
    primaryStage.setScene(new Scene(root, 835, 1000));
    primaryStage.show();


    //Switch setup
    SwitchMacTable macT= SwitchMacTable.getInstance();
    Port p1= new Port(null,null,"Fa0/",null,null,macT);
    Port p2= new Port(null,null,"Fa0/",null,null,macT);
    ACLFilter acl1 = new ACLFilter();
    ACLFilter acl2 = new ACLFilter();
    p1.setAcl(acl1);
    p2.setAcl(acl2);
    Packet statisticsInPort1 = new Packet();
    Packet statisticsOutPort1 = new Packet();
    Packet statisticsInPort2 = new Packet();
    Packet statisticsOutPort2 = new Packet();
    p1.setIn(statisticsInPort1);
    p1.setOut(statisticsOutPort2);
    p2.setIn(statisticsInPort2);
    p2.setOut(statisticsOutPort1);
    // packet sniffer
    int snaplen = 64 * 1024;
    int flags = Pcap.MODE_PROMISCUOUS;
    int timeout = 100000;           // 10ms
    int opt = 0;
    int mask = 0xffffff00;


    //start GUI
    GUI gui= loader.getController();
    gui.initialize(p1,p2);

    devices = new ArrayList<String>();



    List devicelist = new ArrayList();
    List<PcapIf> tempdevices = new ArrayList<PcapIf>();

    StringBuilder errbuf = new StringBuilder();
    String err = "";


    int foundd = Pcap.findAllDevs(devicelist, errbuf);

    if (foundd != Pcap.OK) {
      System.err.printf("Can't read list of devices, error is %s", errbuf.toString());

      return;
    }

    for (Object dev : devicelist) {
      tempdevices.add((PcapIf) dev);
      System.out.println("Dev: " + dev + " or : "+ dev.toString() + " or :" +((PcapIf) dev).getName() );

      devices.add(dev.toString());
    }

    System.out.println(tempdevices.size() + " network devices found:");


    int i = 0;
    ArrayList<String> dvar = new ArrayList<>();
    for (PcapIf device : tempdevices) {
      String description = (device.getDescription() != null) ? device.getDescription() : "No description available";

      dvar.add(description);



    }

    gui.addCombos(dvar);

    new Thread(() -> {
      while (true) {
        int iiii = 1;
        int b=5;
        int yt=iiii+b;
        System.out.print("smp");
        try {



          while(p1.getStop()==0) {
            System.out.print("loop2");

            while (p1.getStart() == 0) {
              //waiting for adapter choice.
              int ii = 1;int bbb=5;
              int yttt=iiii+bbb;
              System.out.print("loop1");
            }

            PcapIf device = tempdevices.get(gui.getAdapterC1());
  Thread.sleep(500);
            PcapIf device2 = tempdevices.get(gui.getAdapterC2());
            Thread.sleep(500);
            byte[] ar = device.getHardwareAddress();
            byte[] ar2 = device2.getHardwareAddress();
            Packet np = new Packet();
            String devname1 = device.getName();
            String devname2 = device2.getName();
            System.out.println("DEVICES CHOSEN: " + devname1 + "," + devname2);
            InetAddress adaIp1=null;
            InetAddress adaIp2=null;

            if (ar != null) {

              System.out.println("IP: "+device.getAddresses().get(0).getAddr());
              byte[] adapip = device.getAddresses().get(0).getAddr().getData();
              try {
                adaIp1 = InetAddress.getByAddress(adapip);
                System.out.println("THS SHOULD BE IT : "+ adaIp1.getHostAddress());

              } catch (UnknownHostException e) {
                e.printStackTrace();
                System.exit(0);
              }


            }
            if (ar2 != null) {

              System.out.println("IP: "+device2.getAddresses().get(0).getAddr());
              byte[] adapip = device2.getAddresses().get(0).getAddr().getData();
              try {
                adaIp2 = InetAddress.getByAddress(adapip);
                System.out.println("THS SHOULD BE IT : "+ adaIp2.getHostAddress());

              } catch (UnknownHostException e) {
                e.printStackTrace();
                System.exit(0);
              }

            }


            //Open the selected device to capture packets
            Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
            Thread.sleep(500);
            Pcap pcap2 = Pcap.openLive(device2.getName(), snaplen, flags, timeout, errbuf);
             Thread.sleep(500);

            if (pcap == null || pcap2 == null) {
              if (pcap == null) {
                System.err.printf("Error  while opening device for capture: " + errbuf.toString());
              } else {
                System.err.printf("Error while opening device for capture: " + errbuf.toString());
              }
              return;
            }

            PcapBpfProgram program = new PcapBpfProgram();
            String filter = "ether proto \\ip or arp or udp or tcp or icmp or (tcp port 80) or (tcp port 443)";

            if (pcap.compile(program, filter, opt, mask) != Pcap.OK) {
              System.out.println(pcap.getErr());
              return;
            }

            if (pcap.setFilter(program) != Pcap.OK) {
              System.out.println(pcap.getErr());
              return;
            }
            if (pcap2.compile(program, filter, opt, mask) != Pcap.OK) {
              System.out.println(pcap2.getErr());
              return;
            }

            if (pcap2.setFilter(program) != Pcap.OK) {
              System.out.println(pcap2.getErr());
              return;
            }

            System.out.println("start ...");

            p1.setInterfaceName(devname1);
            p2.setInterfaceName(devname2);
            p1.setPcap(pcap);
            p2.setPcap(pcap2);

            byte[] adapterMacAddress = device.getHardwareAddress();
            byte[] adapter2MacAddress = device2.getHardwareAddress();
            System.out.println("Real start");
            ArrayList<String> listOfHosts1 = new ArrayList<String>();
            ArrayList<String> listOfHosts2 = new ArrayList<String>();
            ArrayList<PingThread> pings = new ArrayList<>();

            // LOOP    1 1 1 1 1  LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOP

            PcapPacketHandler<String> port1 = (packet, user) -> {


              String macStr =  p1.getIn().bytesToHexString(packet.getByteArray(6, 6));
              String ipFor255 = p1.getIn().hexToIpAddress(p1.getIn().bytesToHexString(packet.getByteArray(30, 4)));
              String ipm= p1.getIn().bytesToHexString(packet.getByteArray(34, 2));
              System.out.println("Pack1");
              //filter
              if(acl1.allowPacket(getPacketInfo(packet,p1),"in").equals("Allowed") || ipm.equals("0089") ) //
              {




                if (ipm.equals("0089"))
                {
                  scheduler.scheduleAtFixedRate(() -> Platform.runLater(() -> {
                    gui.refresh(macT.getMacAddress());

                    gui.refreshPacketCounters(p1.getIn().getChange(), 0);
                    gui.refreshPacketCounters(p1.getOut().getChange(), 2);

                  }), 0, 1, TimeUnit.SECONDS);


                  if (listOfHosts1.contains(
                      p1.getIn().bytesToHexString(packet.getByteArray(6, 6))))
                  {


                    int check = -1;
                    for (int j = 0; j < pings.size(); j++)
                    {
                      if (pings.get(j).getIpAddresss().equals(p1.getIn().hexToIpAddress(p1.getIn()
                          .bytesToHexString(packet.getByteArray(26, 4)))))
                      {
                        check = j;
                      }

                    }
                    if (check == -1)
                    {

                      PingThread t = new PingThread(p1.getIn().hexToIpAddress(
                          p1.getIn().bytesToHexString(packet.getByteArray(26, 4))),
                          macT,
                          p1.getIn().bytesToHexString(packet.getByteArray(6, 6)),
                          pcap, 0, 1);
                      pings.add(t);
                      pings.get(pings.indexOf(t)).start();
                    }
                    else
                    {

                      int ch = pings.get(check).getUnplug();
                      pings.get(check).unplug = ch;
                      pings.get(check).setUnplug(ch + 1);
                      if (pings.get(check).unplugged())
                      {
                        pings.get(check).setStop(false);


                        pings.remove(check);

                      }
                    }

                  }
                }
                String[] parts = ipFor255.split("\\.");
                int lastDigit = Integer.parseInt(parts[3]);
                int lastDigit2 = Integer.parseInt(parts[0]);
                if (lastDigit == 255 || lastDigit2 == 224)
                {

                  return;
                }
                else if (ipFor255.equals("224.0.0.252") || ipFor255.equals("224.0.0.22"))
                {

                  return;
                }
                else if (p1.getIn().bytesToHexString(packet.getByteArray(0, 3)).equals("333300"))
                {
                  return;
                }
                else if (p1.getIn().bytesToHexString(packet.getByteArray(0, 6)).equals("ffffffffffff"))
                {
                  pcap2.sendPacket(packet);
                  return;
                }
                else if (ipFor255.equals("239.255.255.250"))
                {
                  return;
                }
                // if (Arrays.equals(packet.getByteArray(0, 6), adapterMacAddress) && p1.getIn().hexToIpAddress(p1.getIn().bytesToHexString(packet.getByteArray(30, 4))).equals(pt1.getSourceAddresss().getHostAddress())) {
                // System.out.println("Keep alive,-ignore");
                //return ;
                //}
                if ((Arrays.equals(packet.getByteArray(6, 6), adapterMacAddress))
                    || (Arrays
                    .equals(packet.getByteArray(6, 6), adapter2MacAddress)) || (Arrays
                    .equals(packet.getByteArray(0, 6), adapterMacAddress)) || (Arrays
                    .equals(packet.getByteArray(0, 6), adapter2MacAddress)))
                {

                  return;
                }

                if (packetTable.contains(packet))
                {

                  if (packetTable.getSize() > 100)
                  {
                    packetTable.removeOldestEntries();
                  }

                  return;
                }

                if (listOfHosts2.contains(macStr))
                {


                  macT.changePortForMacAddress(macStr, 2);

                  for (int lk = 0; lk < listOfHosts2.size(); lk++)
                  {

                    if (listOfHosts2.get(lk).equals(macStr))
                    {

                      listOfHosts2.remove(lk);

                    }
                  }
                }
                if (!listOfHosts1.contains(macStr))
                {

                  listOfHosts1.add(macStr);
                }
                pcn1++;
                if(acl1.allowPacket(getPacketInfo(packet,p1),"out").equals("Allowed")) //
                {
                  ArrayList<String> ars = p1.getIn().categorize(packet);
                String destIP = ars.get(0);
                String srcIP = ars.get(1);
                String maca = ars.get(2);


                //ICMP PING NOT YET IGNORED, DO IT NEXT TIME !!!!
                macT.addToMacTable(packet, 1, p1);  //addmac,refrespack1,setbytepack,sendpcap,categ2,refresh2

                Platform.runLater(() -> {

                  gui.refresh(macT.getMacAddress());
                });

                String macdest = p1.getIn()
                    .bytesToHexString(packet.getByteArray(0, 6));
                for (int lk = 0; lk < listOfHosts1.size(); lk++)
                {

                  if (listOfHosts1.get(lk).equals(macdest))
                  {

                    return;

                  }
                }

                  packetTable.put(packet);
                  pcap2.sendPacket(packet);

                  p1.getOut().categorize(packet);


                }else {

                }
              }else {

              }
            }
                ;




            // LOOP    2 2 222  LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOP



            PcapPacketHandler<String> port2 = (packet, user) -> {


              Platform.runLater(() -> {
                gui.refresh(macT.getMacAddress());
              });

              String ipFor255 = p1.getIn().hexToIpAddress(p1.getIn().bytesToHexString(packet.getByteArray(30, 4)));
              String[] parts = ipFor255.split("\\.");
              String ipm= p1.getIn().bytesToHexString(packet.getByteArray(34, 2));

              System.out.println("Pack2");
              if(acl2.allowPacket(getPacketInfo(packet,p2),"in").equals("Allowed") || ipm.equals("0089"))
              {


                if (ipm.equals("0089"))
                {
                  scheduler.scheduleAtFixedRate(() -> Platform.runLater(() -> {
                    gui.refresh(macT.getMacAddress());

                    gui.refreshPacketCounters(p2.getIn().getChange(), 1);
                    gui.refreshPacketCounters(p2.getOut().getChange(), 3);
                  }), 0, 2, TimeUnit.SECONDS);

                  if (listOfHosts2.contains(
                      p1.getIn().bytesToHexString(packet.getByteArray(6, 6))))
                  {


                    int check = -1;
                    for (int j = 0; j < pings.size(); j++)
                    {
                      if (pings.get(j).getIpAddresss().equals(p1.getIn().hexToIpAddress(p1.getIn()
                          .bytesToHexString(packet.getByteArray(26, 4)))))
                      {
                        check = j;
                      }

                    }
                    if (check == -1)
                    {

                      PingThread t = new PingThread(p1.getIn().hexToIpAddress(
                          p1.getIn().bytesToHexString(packet.getByteArray(26, 4))),
                          macT,
                          p1.getIn().bytesToHexString(packet.getByteArray(6, 6)),
                          pcap2, 0, 1);
                      pings.add(t);
                      pings.get(pings.indexOf(t)).start();
                    }
                    else
                    {

                      int ch = pings.get(check).getUnplug();
                      pings.get(check).unplug = ch;
                      pings.get(check).setUnplug(ch + 1);
                      if (pings.get(check).unplugged())
                      {
                        pings.get(check).setStop(false);

                        pings.remove(check);
                      }
                    }

                  }
                }
                int lastDigit = Integer.parseInt(parts[3]);
                int lastDigit2 = Integer.parseInt(parts[0]);
                if (lastDigit == 255 || lastDigit2 == 224)
                {

                  return;
                }
                else if (ipFor255.equals("224.0.0.252") || ipFor255.equals("224.0.0.22"))
                {

                  return;
                }
                else if (p1.getIn().bytesToHexString(packet.getByteArray(0, 3)).equals("333300"))
                {
                  return;
                }
                else if (p1.getIn().bytesToHexString(packet.getByteArray(0, 6)).equals("ffffffffffff"))
                {
                  pcap.sendPacket(packet);
                  return;
                }
                else if (ipFor255.equals("239.255.255.250"))
                {
                  return;
                }
                //    if (Arrays.equals(packet.getByteArray(0, 6), adapter2MacAddress) && p1.getIn().hexToIpAddress(p1.getIn().bytesToHexString(packet.getByteArray(30, 4))).equals(pt2.getSourceAddresss().getHostAddress()))
                //  {
                //  System.out.println("Keep alive,-ignore");
                // return ;
                //}
                if ((Arrays.equals(packet.getByteArray(6, 6), adapterMacAddress))
                    || (Arrays
                    .equals(packet.getByteArray(6, 6), adapter2MacAddress)) || (
                    Arrays.equals(packet.getByteArray(0, 6), adapter2MacAddress)
                        || (Arrays
                        .equals(packet.getByteArray(0, 6), adapterMacAddress))))
                {
                  return;
                }
                String macStr = p1.getIn()
                    .bytesToHexString(packet.getByteArray(6, 6));
                if (packetTable.contains(packet))
                {

                  if (packetTable.getSize() > 100)
                  {
                    packetTable.removeOldestEntries();
                  }

                  return;
                }
                if (listOfHosts1.contains(macStr))
                {

                  macT.changePortForMacAddress(macStr, 1);

                  for (int lk = 0; lk < listOfHosts1.size(); lk++)
                  {

                    if (listOfHosts1.get(lk).equals(macStr))
                    {

                      listOfHosts1.remove(lk);

                    }
                  }
                }
                if (!listOfHosts2.contains(macStr))
                {

                  listOfHosts2.add(macStr);
                }
                pcn1++;


                ArrayList<String> ars = p2.getIn().categorize(packet);
                String destIP = ars.get(0);
                String srcIP = ars.get(1);
                String maca = ars.get(2);
                if(acl2.allowPacket(getPacketInfo(packet,p2),"out").equals("Allowed"))//
                {

                macT.addToMacTable(packet, 2, p2);

                Platform.runLater(() -> {

                  gui.refresh(macT.getMacAddress());
                });
                String macdest = p1.getIn()
                    .bytesToHexString(packet.getByteArray(0, 6));
                for (int lk = 0; lk < listOfHosts2.size(); lk++)
                {

                  if (listOfHosts2.get(lk).equals(macdest))
                  {

                    return;

                  }
                }

                  packetTable.put(packet);
                  pcap.sendPacket(packet);

                  p2.getOut().categorize(packet);
                }else {

                }

              }else {

              }
            }
                ;

            System.out.println("Loop1 should start now :");
            new Thread(() -> {
              pcap.loop(Pcap.LOOP_INFINITE, port1, "pcap1");
            }).start();

            System.out.println("Loop2 should start now :");
            pcap2.loop(Pcap.LOOP_INFINITE, port2,"pcap2");







          }

        } catch (Exception e) {
          System.out.println("WTF?");

          e.printStackTrace();
        }



      }
    }).start();
  }



  public synchronized ArrayList<String> getPacketInfo(PcapPacket packet,Port p1)
  {

    ArrayList<String> rules = new ArrayList<>();

   String srcMac=  p1.getIn().bytesToHexString(packet.getByteArray(6, 6));
   String destMac=  p1.getIn().bytesToHexString(packet.getByteArray(0, 6));
   String srcIp =  p1.getIn().hexToIpAddress(p1.getIn().bytesToHexString(packet.getByteArray(26, 4)));
    String dstIp =  p1.getIn().hexToIpAddress(p1.getIn().bytesToHexString(packet.getByteArray(30, 4)));
   String type= p1.getIn().bytesToHexString(packet.getByteArray(12, 2));
   if(type.equals("0806"))
   {
     srcIp =  p1.getIn().hexToIpAddress(p1.getIn().bytesToHexString(packet.getByteArray(28, 4)));
     dstIp =  p1.getIn().hexToIpAddress(p1.getIn().bytesToHexString(packet.getByteArray(38, 4)));
     rules.add(srcMac);
     rules.add(destMac);
     rules.add(srcIp);
     rules.add(dstIp);
     rules.add(type);
     rules.add("null");
     rules.add("null");
     rules.add("null");

     return  rules;
   }
   String protocol= p1.getIn().bytesToHexString(packet.getByteArray(24, 1));
   String portSrc= p1.getIn().bytesToHexString(packet.getByteArray(34, 2));
    String portdst= p1.getIn().bytesToHexString(packet.getByteArray(36, 2));
    rules.add(srcMac);
    rules.add(destMac);
    rules.add(srcIp);
    rules.add(dstIp);
    rules.add(type);
    rules.add(protocol);
    rules.add(portSrc);
    rules.add(portdst);

   return rules;

  }



}
