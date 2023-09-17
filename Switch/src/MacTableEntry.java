public class MacTableEntry {
    private int portNumber;
    private long timer;
    private String mac;

    public MacTableEntry(int portNumber, long timer,String mac) {
        this.portNumber = portNumber;
        this.timer = timer;
        this.mac= mac;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public long getTimer() {
        return timer;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
