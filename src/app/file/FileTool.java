package app.file;

import app.AppConfig;
import app.AppTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileTool {

    public static FileInfo getFileInfoFromPath(String rootDirectory, String path) {
        path = rootDirectory + "\\" + path;
        File f = new File(path);

        if (!f.exists()) {
            AppTool.timestampedErrorPrint("File " + path + " doesn't exist.");
            return null;
        }
        if (f.isDirectory()) {
            AppTool.timestampedErrorPrint(path + " is a directory and not a file.");
            return null;
        }

        try {
            String filePath = path.replace(rootDirectory + "\\", "");

            BufferedReader reader = new BufferedReader(new FileReader(f));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line + "\n");
            }
            reader.close();

            return new FileInfo(filePath, fileContent.toString(), AppConfig.myServentInfo.getPhysicalId());
        } catch (IOException e) {
            AppTool.timestampedErrorPrint("Couldn't read " + path + ".");
        }

        return null;
    }

    public static List<FileInfo> getDirectoryInfoFromPath(String rootDirectory, String path) {

        List<FileInfo> fileInfoList = new ArrayList<>();

        path = rootDirectory + "\\" + path;
        File f = new File(path);
        if (!f.exists()) {
            AppTool.timestampedErrorPrint("Directory " + path + " doesn't exist.");
            return fileInfoList;
        }

        if (f.isFile()) {
            AppTool.timestampedErrorPrint(path + " is a file and not a directory.");
            return fileInfoList;
        }

        Queue<String> directories = new LinkedList<>();
        directories.add(path);

        while (!directories.isEmpty()) {
            String dirPath = directories.poll();
            List<String> subFiles = new ArrayList<>();

            File directory = new File(dirPath);
            for (File file : directory.listFiles()) {
                String filePath = file.getPath().replace(rootDirectory + "\\", "");
                subFiles.add(filePath);

                if (file.isFile()) {
                    FileInfo fileInfo = getFileInfoFromPath(rootDirectory, filePath);
                    if (fileInfo != null) {
                        fileInfoList.add(fileInfo);
                    }
                } else {
                    directories.add(file.getPath());
                }
            }

            dirPath = dirPath.replace(rootDirectory + "\\", "");
            fileInfoList.add(new FileInfo(dirPath, subFiles, AppConfig.myServentInfo.getPhysicalId()));
        }
        return fileInfoList;
    }

    public static boolean isPathFile(String rootDirectory, String path) {
        File f = new File(rootDirectory + "\\" + path);
        AppTool.timestampedStandardPrint("File exists with this path. " + rootDirectory + "\\" + path);
        return f.isFile();
    }
}
