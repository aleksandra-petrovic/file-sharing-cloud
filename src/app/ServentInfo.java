package app;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is an immutable class that holds all the information for a servent.
 *
 * @author bmilojkovic
 */

public class ServentInfo implements Serializable {

    private static final long serialVersionUID = 5304170042791281555L;
    private int physicalId;
    private String ipAddress;
    private final short listenerPort;
    private Set<Short> neighbors;

    public ServentInfo(String ipAddress, int physicalId, short listenerPort, Set<Short> neighbors) {
        this.ipAddress = ipAddress;
        this.listenerPort = listenerPort;
        this.physicalId = physicalId;
        this.neighbors = neighbors;
    }

    public ServentInfo(short listenerPort) {
        this.listenerPort = listenerPort;
    }

    public String getIpAddress() { return ipAddress; }

    public void setNeighbors(Set<Short> neighbors) {
        this.neighbors = neighbors;
    }

    public void clearNeighbors(){
        this.neighbors = new HashSet<>();
    }

    public void addNeighbor(Short port){
        this.neighbors.add(port);
    }

    public Set<Short> getNeighbors() {
        return neighbors;
    }

    public short getListenerPort() {
        return listenerPort;
    }
    public int getPhysicalId() { return physicalId; }
    @Override
    public String toString() {
        return "[" + physicalId + "|" + ipAddress + "|" + listenerPort + "]";
    }

}
