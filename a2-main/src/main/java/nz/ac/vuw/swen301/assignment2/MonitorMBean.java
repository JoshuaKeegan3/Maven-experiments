package nz.ac.vuw.swen301.assignment2;

public interface MonitorMBean {
    // operations

    public String[] getLogs();

    // attributes

    // a read-write attribute called Message of type String
    public long getLogCount();
    public long getDiscardedLogCount();
    public void exportToJSON(String fileName);

}
