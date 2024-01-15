package app.checkup;

public class PingingInfo {

    private short nodeToPing = -1;
    private short backupNode = -1;
    private short secondPredecessor = -1;
    private short secondSuccessor = -1;

    private int totalPings = 0;
    private int totalPongs = 0;
    private boolean suspicious = false;

    private boolean doneChecking = false;

    public int getTotalPings() {
        return totalPings;
    }

    public int getTotalPongs() {
        return totalPongs;
    }

    public short getNodeToPing() {
        return nodeToPing;
    }

    public short getBackupNode() {
        return backupNode;
    }

    public short getSecondPredecessor() {
        return secondPredecessor;
    }

    public void setTotalPongs(int totalPongs) {
        this.totalPongs = totalPongs;
    }

    public void setSecondPredecessor(short secondPredecessor) {
        this.secondPredecessor = secondPredecessor;
    }

    public short getSecondSuccessor() {
        return secondSuccessor;
    }

    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }

    public void setSecondSuccessor(short secondSuccessor) {
        this.secondSuccessor = secondSuccessor;
    }

    public void setBackupNode(short backupNode) {
        this.backupNode = backupNode;
    }

    public void setNodeToPing(short nodeToPing) {
        this.nodeToPing = nodeToPing;
    }

    public void setDoneChecking(boolean doneChecking) {
        this.doneChecking = doneChecking;
    }

    public void setTotalPings(int totalPings) {
        this.totalPings = totalPings;
    }

    public boolean isDoneChecking() {
        return doneChecking;
    }

    public boolean isSuspicious() {
        return suspicious;
    }
}
