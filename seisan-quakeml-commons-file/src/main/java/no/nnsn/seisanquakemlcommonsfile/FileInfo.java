package no.nnsn.seisanquakemlcommonsfile;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class FileInfo {
    private Set<Path> filePaths;
    private String catalogName;
    private int qmlFileEqualCount;
    private int sFileEqualCount;
    private Set<Path> skippedFiles;
    private List<String> ignoreFolders;

    public FileInfo(String path, String fileType, List<String> ignoreFolders) throws IOException {
        this.filePaths = new HashSet<>();
        this.qmlFileEqualCount = 0;
        this.sFileEqualCount = 0;
        this.ignoreFolders = ignoreFolders;
        this.setFilePath(path, fileType);
    }

    public void addQml(Path path) {
        this.filePaths.add(path);
    }


    public void addQmlEqual() {
        this.qmlFileEqualCount++;
    }


    public void addSfile(Path path) {
        this.filePaths.add(path);
    }

    public void addSfileEqual() {
        this.sFileEqualCount++;
    }

    public void setFilePath(String path, String fileType) throws IOException {
        Path filePath = new File(path).toPath();
        if (Files.isRegularFile(filePath)) { // is file
            if (fileType.equals("qml")) {
                this.addQml(filePath);
            }
            if (fileType.equals("sfile")) {
                this.addSfile(filePath);
            }
        } else if (Files.isDirectory(filePath)) { // is directory

            FileScan fileScan = new FileScan(path);
            fileScan.setIgnoreFolders(this.ignoreFolders);
            try {
                Files.walkFileTree(Paths.get(path), fileScan);
                this.filePaths = fileScan.getFilePaths();
                this.catalogName = fileScan.getCatalogName();
                this.skippedFiles = fileScan.getSkippedFiles();
            } catch (Exception e) {
                System.out.println("Could not scan through the catalog");
            }

            Set<Path> failedFileVisit = fileScan.getFailedFileVisit();
            if (failedFileVisit.size() > 0) {
                failedFileVisit.forEach(p -> System.out.println("Could not access file: " + p.getFileName()));
            }
        }
    }

    public int getsFileCount() {
        return this.filePaths.size();
    }
}