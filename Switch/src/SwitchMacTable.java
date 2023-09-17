

import javafx.application.Platform;
import org.jnetpcap.nio.JBuffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

    public class SwitchMacTable {
        private static SwitchMacTable instance = null;
        private Map<String, MacTableEntry> macTable;
        private long expirationTime = TimeUnit.SECONDS.toMillis(15);

        private long oldtime = TimeUnit.SECONDS.toMillis(15);

        private SwitchMacTable() {
            macTable = new HashMap<>();
            startTimerThread();
        }

        public static synchronized SwitchMacTable getInstance() {
            if (instance == null) {
                instance = new SwitchMacTable();
            }
            return instance;
        }

        public synchronized void addToMacTable(JBuffer buffer, int portNumber,Port p) {


                byte macs[] = buffer.getByteArray(6, 6);
                String macs1 = p.getIn().bytesToHexString(macs);
                MacTableEntry entry = new MacTableEntry(portNumber, System.currentTimeMillis() + expirationTime,macs1);

                macTable.put(macs1, entry);


        }

        public synchronized void changePortForMacAddress(String macAddress, int newPortNumber) {
            MacTableEntry entry = macTable.get(macAddress);
            if (entry != null && entry.getTimer() > System.currentTimeMillis()) {
                entry.setPortNumber(newPortNumber);
            }
        }
        public synchronized void setExpirationTime(long seconds) {
            Platform.runLater(() -> {
                oldtime =  expirationTime;
                expirationTime = TimeUnit.SECONDS.toMillis(seconds);
                ArrayList<MacTableInfo> result = new ArrayList<MacTableInfo>();
                ArrayList<String> removables=new ArrayList<>();
                Iterator<Map.Entry<String, MacTableEntry>> iterator = macTable.entrySet().iterator();

                while  (iterator.hasNext()) {
                    Map.Entry<String, MacTableEntry> entry = iterator.next();
                    long currentTime = System.currentTimeMillis();

                    MacTableEntry macEntry = entry.getValue();
                    long timer = macEntry.getTimer();


                    if ((timer - oldtime) + expirationTime < currentTime) {
                        System.out.println("REMOVING ENTRY:");

                        removables.add(entry.getKey());


                    } else {
                        long L = timer - oldtime - currentTime + expirationTime;

                            macEntry.setTimer(timer - oldtime + expirationTime);



                    }
                }
                for (int i = 0; i < removables.size(); i++) {
                    macTable.remove(removables.get(i));
                }
                oldtime=expirationTime;

            });


        }
        public synchronized void clearMacTable() {
            macTable.clear();
        }
        public synchronized ArrayList<MacTableInfo> getMacAddress() {
            ArrayList<MacTableInfo> result = new ArrayList<MacTableInfo>();
            ArrayList<String> removables=new ArrayList<>();
            Iterator<Map.Entry<String, MacTableEntry>> iterator = macTable.entrySet().iterator();

                while  (iterator.hasNext()) {
                    Map.Entry<String, MacTableEntry> entry = iterator.next();
                    long currentTime = System.currentTimeMillis();

                    MacTableEntry macEntry = entry.getValue();
                    long timer = macEntry.getTimer();

                    String macAddress = entry.getKey();
                    int port = macEntry.getPortNumber();


                    if ((timer - oldtime) + expirationTime < currentTime) {


                           removables.add(entry.getKey());


                    } else {
                        long L = timer - oldtime - currentTime + expirationTime;
                        Platform.runLater(() -> {
                            macEntry.setTimer(timer - oldtime + expirationTime);

                        });


                        String[] macGroups = macAddress.split("(?<=\\G..)");

                        String officialMacAddress = String.join("-", macGroups).toUpperCase();


                        MacTableInfo entryString = new MacTableInfo("Fa0/" + port, String.valueOf(L / 1000), officialMacAddress);


                        result.add(entryString);
                    }
                }
            for (int i = 0; i < removables.size(); i++) {
                macTable.remove(removables.get(i));
            }

            


            return result;
        }
        public synchronized void removeMacAddress(String macAddress) {
            macTable.remove(macAddress);
        }
        private void startTimerThread() {
            Thread timerThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(1)); // Sleep for 1 second
                        long currentTimer = System.currentTimeMillis();
                        synchronized (SwitchMacTable.this) {
                            macTable.entrySet().removeIf(entry -> entry.getValue().getTimer() < currentTimer);
                        }
                    } catch (InterruptedException e) {
                        // Thread interrupted, stop the timer thread
                        break;
                    }
                }
            });
            timerThread.setDaemon(true); // Set the thread as daemon to automatically stop when the program exits
            timerThread.start();
        }



}
