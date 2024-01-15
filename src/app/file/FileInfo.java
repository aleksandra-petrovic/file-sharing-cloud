package app.file;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileInfo implements Serializable {
    private static final long serialVersionUID = 728973183738344229L;

    private final String path;
    private final String content;
    private final boolean isDirectory;
    private final List<String> subDirectories;
    private final int nodeId;
    public FileInfo(String path, String content, boolean isDirectory, List<String> subDirectories, int nodeId){
        this.path = path;
        this.content = content;
        this.isDirectory = isDirectory;
        this.subDirectories = new ArrayList<>();
        if(subDirectories != null) {
            this.subDirectories.addAll(subDirectories);
        }
        this.nodeId = nodeId;
    }

    public FileInfo(String path, String content, int nodeId){
        this.path = path;
        this.content = content;
        isDirectory = false;
        this.subDirectories = null;
        this.nodeId = nodeId;
    }

    public FileInfo(String path, List<String> subDirectories, int nodeId) {
        this.path = path;
        this.content = "";
        this.isDirectory = true;
        this.subDirectories = new ArrayList<>();
        if(subDirectories != null) {
            this.subDirectories.addAll(subDirectories);
        }
        this.nodeId = nodeId;
    }

    public String getPath() {
        return path;
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getContent() {
        return content;
    }

    public List<String> getSubDirectories() {
        return subDirectories;
    }
}
