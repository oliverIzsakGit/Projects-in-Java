import java.util.ArrayList;
import java.util.List;

public class ACLFilter {

    private List<ACLRule> rules;

    public ACLFilter() {
        rules = new ArrayList<>();
    }

    public void addRule(ACLRule rule) {
        rules.add(rule);
    }

    public void removeRule(int id) {

          rules.remove(id);


    }
    public int lenght(){
        return rules.size();
    }

    public synchronized String allowPacket(ArrayList<String>packet, String in) {
      if(rules.size()==0)
      {
        return "Allowed";
      }
        for (ACLRule rule : rules) {
            if (rule.matches(packet) && rule.getIn().equals(in)) {
                boolean a = rule.getAction();
                String m ="";
                if(a)
                {
                    m="Allowed";
                }else {
                    m="Denied";
                }
                return  m ;
            }
        }
        return "Denied";
    }
    public static class ACLRule {
       private boolean action;


        private String sourceIP;
        private String destinationIP;
        private String sourcePortMac;
        private String destinationPortMac;
        private String sourcePort;
        private String destinationPort;

        private String in;
        private String protocol;
        private String type;
        private int id;

      public String getIn()
      {
        return in;
      }

      public void setIn(String in)
      {
        if(in.equals("1"))
        {
          list.add("1");
          this.in="in";
        }else {
          list.add("out");
          this.in="out";
        }
      }
ArrayList<String> list= new ArrayList<>();
      public ACLRule(int id,String inout, String action,String sourceIP, String destinationIP, String sourcePort, String destinationPort,String type,String protocol,String srcport,String dstport) {
            this.sourceIP = sourceIP;
            this.id=id;
            this.destinationIP = destinationIP;
            this.sourcePort = srcport;
            this.destinationPort = dstport;
            this.sourcePortMac= sourcePort;
            this.destinationPortMac= destinationPort;
            boolean asd = false;
            if(action.equals("1"))
            {asd=true;

            }
            this.action = asd;
            this.in= inout;
            this.type=type;
            this.protocol=protocol;

            list.add(inout);
            list.add(action);
            list.add(sourcePortMac);
            list.add(destinationPortMac);
            list.add(sourceIP);
            list.add(destinationIP);
            list.add(type);
            list.add(protocol);
            list.add(srcport);
            list.add(dstport);

        }

      public boolean isAction()
      {
        return action;
      }

      public void setAction(String action)
      {
        if(action.equals("1"))
        {
          list.add("1");
          this.action=true;
        }else {
          list.add("0");
          this.action = false;
        }


      }

      public void setSourceIP(String sourceIP)
      {
        this.sourceIP = sourceIP;
      }

      public void setDestinationIP(String destinationIP)
      {
        this.destinationIP = destinationIP;
      }

      public int getId()
      {
        return id;
      }

      public void setId(int id)
      {
        this.id = id;
      }

      public String getSourcePortMac()
      {
        return sourcePortMac;
      }

      public void setSourcePortMac(String sourcePortMac)
      {
        this.sourcePortMac = sourcePortMac;
      }

      public String getDestinationPortMac()
      {
        return destinationPortMac;
      }

      public void setDestinationPortMac(String destinationPortMac)
      {
        this.destinationPortMac = destinationPortMac;
      }

      public String getSourcePort()
      {
        return sourcePort;
      }

      public void setSourcePort(String sourcePort)
      {
        this.sourcePort = sourcePort;
      }

      public String getDestinationPort()
      {
        return destinationPort;
      }

      public void setDestinationPort(String destinationPort)
      {
        this.destinationPort = destinationPort;
      }

      public String getProtocol()
      {
        return protocol;
      }

      public void setProtocol(String protocol)
      {
        this.protocol = protocol;
      }

      public String getType()
      {
        return type;
      }

      public void setType(String type)
      {
        this.type = type;
      }

      public ArrayList<String> getList()
      {
        return list;
      }

      public void setList(ArrayList<String> list)
      {
        this.list = list;
      }

      public String getSourceIP() {
            return sourceIP;
        }

        public String getDestinationIP() {
            return destinationIP;
        }



        public boolean getAction() {
            return action;
        }

        public synchronized boolean matches(ArrayList<String> packet )
        { boolean ans=true;
        if(packet.size()<=0 ||list==null)
        {   System.out.println("1Something was  null: "+ packet.size() + " or "+ list);
          return false;
        }
        if(packet==null ||list.size()<=0)
        {
            System.out.println("2Something was  null: "+ packet + " or "+ list.size());
          return false;
        }
          for (int i = 0; i <packet.size() ; i++)
          {

            System.out.println("pack "+ packet.get(i));
            System.out.println("list: "+ list.get(i) + list + " + "+ list.size());
            System.out.println("pack "+ packet.get(i).equals(list.get(i)));
            System.out.println("heyo");
            if(list==null || list.get(i)==null)
            {
                System.out.println("3Something was  null: "+ list + " or "+ list.size());
              return false;
            }
            if(packet.get(i).equals(list.get(i)) || list.get(i+2).equals("null"))
            {
              ans=true;
            }else {

              return false;
            }
          }
          return ans;
      }
    }

}