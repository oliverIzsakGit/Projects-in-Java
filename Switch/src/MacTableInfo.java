public class MacTableInfo {
    private String portCol;
    private String timerCol;
    private String macCol;

    public MacTableInfo(String portCol, String timerCol,String macCol) {
        this.portCol = portCol;
        this.timerCol = timerCol;
        this.macCol= macCol;
    }

    public String getPortCol() {
        return portCol;
    }

    public void setPortCol(String portCol) {
        this.portCol = portCol;
    }

    public String getTimerCol() {
        return timerCol;
    }

    public void setTimerCol(String timerCol) {
        this.timerCol = timerCol;
    }

    public String getMacCol() {
        return macCol;
    }

    public void setMacCol(String macCol) {
        this.macCol = macCol;
    }
}
