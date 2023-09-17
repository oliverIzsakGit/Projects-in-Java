import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import org.jnetpcap.Pcap;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GUI {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @FXML
    private ComboBox<String> choiceBox1;


    @FXML
    private ComboBox<String> choiceBox2;

    @FXML
    private Button ruleButton;

    @FXML
    private TextField interfaceNo;

    @FXML
    private TextField io;

    @FXML
    private TextField allow;

    @FXML
    private TextField srcMac;

    @FXML
    private TextField dstMac;

    @FXML
    private TextField srcIP;

    @FXML
    private TextField dstIP;

    @FXML
    private TextField type;

    @FXML
    private TextField prot;

    @FXML
    private TextField srcPort;

    @FXML
    private TextField dstPort;

    @FXML
    private TextArea ruleText;

    @FXML
    private Button removeButton;

    @FXML
    private TextField removeField;

    @FXML
    private Label ARPI1;

    @FXML
    private Label ARPI2;

    @FXML
    private Label ARPO1;

    @FXML
    private Label ARPO2;

    @FXML
    private Label HTTPI1;

    @FXML
    private Label adLabel1;

    @FXML
    private Label adLabel2;

    @FXML
    private Label HTTPI2;

    @FXML
    private Label HTTPO1;
    @FXML
    private Button addApp1;

    @FXML
    private Button addApp2;
    @FXML
    private Label HTTPO2;

    @FXML
    private Label HTTPSI1;

    @FXML
    private Label HTTPSI2;

    @FXML
    private Label HTTPSO1;

    @FXML
    private Label HTTPSO2;

    @FXML
    private Label ICMPI1;

    @FXML
    private Label ICMPI2;

    @FXML
    private Label ICMPO1;

    @FXML
    private Label ICMPO2;

    @FXML
    private Label IPI1;

    @FXML
    private Label IPI2;

    @FXML
    private Label IPO1;
    @FXML
    private Label IPO2;

    @FXML
    private Label TCPI1;

    @FXML
    private Label TCPI2;

    @FXML
    private Label TCPO1;

    @FXML
    private Label TCPO2;

    @FXML
    private Label TOTALI1;

    @FXML
    private Label TOTALI2;

    @FXML
    private Label TOTALO1;

    @FXML
    private Label TOTALO2;

    @FXML
    private Label UDPI1;

    @FXML
    private Label UDPI2;

    @FXML
    private Label UDPO1;

    @FXML
    private Label UDPO2;

    @FXML
    private Label ethernetI1;

    @FXML
    private Label ethernetI2;

    @FXML
    private Label ethernetO1;

    @FXML
    private Label ethernetO2;

   ArrayList<String> rules =new ArrayList<>();

    @FXML
    private Button startProgram;

    @FXML
    private Button stopProgram;

    @FXML
    private Button timerBtn;


    @FXML
    private TableColumn<MacTableInfo, String> macCol;
    @FXML
    private TableView<MacTableInfo> macTable;

    @FXML
    private TableColumn<MacTableInfo, String> portCol;

    @FXML
    private Button resb;
    @FXML
    private Button clear1;
    @FXML
    private Button clear2;
    @FXML
    private TableColumn<MacTableInfo, Integer> timerCol;

    ObservableList<MacTableInfo> list = FXCollections.observableArrayList();
    ObservableList<String> choiceList = FXCollections.observableArrayList();
    private Port port1;
    private Port port2;

    @FXML
    void changeTimer(ActionEvent event) {
        Platform.runLater(() -> {
            port1.getMacTable().setExpirationTime( Integer.parseInt(timerField.getText()));
        });


    }

    @FXML
    void clearPort1(ActionEvent event) {
        System.out.println("RESETING IN GUI1");
        Platform.runLater(() -> {
            port1.getIn().reset();
            port1.getOut().reset();

        });

    }

    @FXML
    void clearPort2(ActionEvent event) {
        System.out.println("RESETING IN GUI2");
        Platform.runLater(() -> {

            port2.getIn().reset();
            port2.getOut().reset();
        });

    }


    @FXML
    void startCapture(ActionEvent event) {
        System.out.println("Startin rn !!!!!!!!!!!!!!!!");
        port1.setStop(0);
        port1.setStart(1);



    }

    @FXML
    void stopCapture(ActionEvent event) {

        System.out.println("STOPING");
        try{
            port1.setStop(1);
            port1.setStart(0);

            getPort1().getPcap().breakloop();
            getPort2().getPcap().breakloop();
            System.out.println("IS this exist?: "+ getPort1().getPcap());
            System.out.println("IS this exist?: "+ getPort2().getPcap().toString());

        }
        catch (Exception e)
        {
            System.out.println("Already closed");
        }


    }

    @FXML
    void resetMacTable(ActionEvent event) {
        Platform.runLater(() -> {
            getPort1().getMacTable().clearMacTable();
            list.clear();
        });

    }

    @FXML
    private TextField timerField;


    @FXML
    public void initialize(Port gui1,Port gui2) {
        macCol.setCellValueFactory(new PropertyValueFactory<MacTableInfo, String>("macCol"));
        portCol.setCellValueFactory(new PropertyValueFactory<MacTableInfo, String>("portCol"));
        timerCol.setCellValueFactory(new PropertyValueFactory<MacTableInfo, Integer>("timerCol"));
        macTable.setItems(list);
        this.port1=gui1;
        this.port2 = gui2;
    }

    public void addCombos(ArrayList<String> devices)
    {
        ObservableList<String> r = FXCollections.observableArrayList();
        r.addAll(devices);

        choiceList.addAll(r);
        choiceBox1.setItems(choiceList);
        choiceBox2.setItems(choiceList);
    }

    public Port getPort1() {
        return port1;
    }

    public void setPort1(Port port1) {
        this.port1 = port1;
    }

    public Port getPort2() {
        return port2;
    }

    public void setPort2(Port port2) {
        this.port2 = port2;
    }

    public synchronized void refresh(ArrayList<MacTableInfo> macarray)
    {
        Platform.runLater(() -> {

            list.clear();

            ObservableList<MacTableInfo> nlist = FXCollections.observableArrayList();
            nlist.addAll(macarray);

            list.addAll(nlist);
        });


    }

    public synchronized void refreshRule(ArrayList<String> macarray)
    {
        Platform.runLater(() -> {

            ruleText.clear();

            for (int i = 0; i <macarray.size() ; i++)
            {
                ruleText.textProperty().setValue("Rules");
                ruleText.appendText(macarray.get(i));
            }

        });


    }



    public synchronized void refreshPacketCounters(ArrayList<String> lst,int type)
    {
        //example

            Platform.runLater(() -> {
                if (type==0)

                {
                    ethernetI1.setText(lst.get(0));
                    IPI1.setText(lst.get(1));
                    ARPI1.setText(lst.get(2));
                    TCPI1.setText(lst.get(3));
                    UDPI1.setText(lst.get(4));
                    ICMPI1.setText(lst.get(5));
                    HTTPI1.setText(lst.get(6));
                    HTTPSI1.setText(lst.get(7));
                    TOTALI1.setText(lst.get(8));

                }else if(type==1)
                {
                    ethernetI2.setText(lst.get(0));
                    IPI2.setText(lst.get(1));
                    ARPI2.setText(lst.get(2));
                    TCPI2.setText(lst.get(3));
                    UDPI2.setText(lst.get(4));
                    ICMPI2.setText(lst.get(5));
                    HTTPI2.setText(lst.get(6));
                    HTTPSI2.setText(lst.get(7));
                    TOTALI2.setText(lst.get(8));

                }
                else if(type==2)
                {
                    ethernetO1.setText(lst.get(0));
                    IPO1.setText(lst.get(1));
                    ARPO1.setText(lst.get(2));
                    TCPO1.setText(lst.get(3));
                    UDPO1.setText(lst.get(4));
                    ICMPO1.setText(lst.get(5));
                    HTTPO1.setText(lst.get(6));
                    HTTPSO1.setText(lst.get(7));
                    TOTALO1.setText(lst.get(8));


                }
                else if(type==3){
                    ethernetO2.setText(lst.get(0));
                    IPO2.setText(lst.get(1));
                    ARPO2.setText(lst.get(2));
                    TCPO2.setText(lst.get(3));
                    UDPO2.setText(lst.get(4));
                    ICMPO2.setText(lst.get(5));
                    HTTPO2.setText(lst.get(6));
                    HTTPSO2.setText(lst.get(7));
                    TOTALO2.setText(lst.get(8));
                }
            });




    }
    public String adapter1 = "";  public String adapter2 = "";
 public int adapterC1=0;
    public int adapterC2=0;

    public int getAdapterC1() {
        return adapterC1;
    }

    public void setAdapterC1(int adapterC1) {
        this.adapterC1 = adapterC1;
    }

    public int getAdapterC2() {
        return adapterC2;
    }

    public void setAdapterC2(int adapterC2) {
        this.adapterC2 = adapterC2;
    }

    public void choice1(ActionEvent actionEvent) {
       adapter1 = choiceBox1.getValue();

    }

    public void choice2(ActionEvent actionEvent) {
       adapter2 = choiceBox2.getValue();
    }

    public void addAdapter1(ActionEvent actionEvent) {
        adLabel1.setText(adapter1);
       adapterC1= choiceBox1.getItems().indexOf(adapter1);
    }

    public void addAdapter2(ActionEvent actionEvent) {
        adLabel2.setText(adapter2);
        adapterC2= choiceBox2.getItems().indexOf(adapter2);
    }

    public synchronized void addRule(ActionEvent actionEvent)
    { ArrayList<String> rules = new ArrayList<>();
    String prt = "fa/01";
    if(interfaceNo.getText().equals("2"))
    {
      prt="fa/02";
    }
      StringBuilder fin = new StringBuilder(prt + ",");
      rules.add(io.getText());
      rules.add(allow.getText());
      rules.add((srcMac.getText()));
      rules.add(dstMac.getText());
      rules.add(srcIP.getText());
      rules.add(dstIP.getText());
      rules.add(type.getText());
      rules.add(prot.getText());
      rules.add(srcPort.getText());
      rules.add(dstPort.getText());

      ACLFilter.ACLRule rl = new ACLFilter.ACLRule(this.rules.size(),null,allow.getText(),null,null,null,null,null,null,null,null);

      for (int i = 0; i <rules.size() ; i++)
      {
        if (rules.get(i).equals("") || rules.get(i).equals(" "))
        {
          rules.set(i, "null");


        }


          if (i == 0)
          {
            rl.setIn(rules.get(i));
          }
          else if (i == 1)
          {
            rl.setAction(rules.get(i));

          }
          else if (i == 2)
          {
            rl.setSourceIP(rules.get(i));
          }
          else if (i == 3)
          {
            rl.setDestinationIP(rules.get(i));

          }
          else if (i == 4)
          {
            rl.setSourcePortMac(rules.get(i));

          }
          else if (i == 5)
          {
            rl.setDestinationPortMac(rules.get(i));

          }
          else if (i == 6)
          {
            rl.setType(rules.get(i));
          }
          else if (i == 7)
          {
            rl.setProtocol(rules.get(i));
          }
          else if (i == 8)
          {
            rl.setSourcePort(rules.get(i));
          }
          else
          {
            rl.setDestinationPort(rules.get(i));
          }


        if(i==8){
          fin.append(rules.get(i));
        }else
        {
          fin.append(rules.get(i)).append(",");
        }
      }
      System.out.println("This is fin: "+ fin);
      System.out.println("test : "+ fin.toString());
      String newLine = System.getProperty("line.separator");
      this.rules.add(fin.toString());
 String fina = "Rules: "+ newLine;

      ruleText.setMaxHeight(Double.MAX_VALUE);

      for (int i = 0; i <this.rules.size() ; i++)
      {

        fina +=  this.rules.get(i)+ newLine;

      }
      ruleText.setText(fina);

        System.out.println("fa is : "+ prt);
        if(prt.equals("fa/01"))
        {
            System.out.println("ADDING NEW ACL TO fa/01 : " + rl.getType());
          port1.getAcl().addRule(rl);
            System.out.println("RIGHT AFTER ADDOG : "+ port1.getAcl().lenght());
        }else {
          port2.getAcl().addRule(rl);
        }







    }

    public synchronized  void  removeRule(ActionEvent actionEvent)
    { Platform.runLater(() -> {


        int m = Integer.parseInt(removeField.getText());
        int f1=0;
        int f2=0;
        for (int i = 0; i <this.rules.size() ; i++)
        {
            if(this.rules.get(i).contains("fa/01"))
            {
                if(m==i){
                    System.out.println("This is acl1 list : "+ port1.getAcl().lenght());
                    port1.getAcl().removeRule(f1);
                    break;
                }
                f1++;

            }else if(this.rules.get(i).contains("fa/02"))
            {
                if(m==i){
                    System.out.println("This is acl2 list : "+ port2.getAcl().lenght());
                    port2.getAcl().removeRule(f2);
                    break;
                }
                f2++;
            }
        }


        this.rules.remove(m);

        String newLine = System.getProperty("line.separator");
        ruleText.setText("");
        String fina = "Rules: "+ newLine;

        ruleText.setMaxHeight(Double.MAX_VALUE);

        for (int i = 0; i <this.rules.size() ; i++)
        {

            fina +=  this.rules.get(i)+ newLine;

        }
        ruleText.setText(fina);
    });

    }
}
