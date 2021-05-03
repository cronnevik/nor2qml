package no.nnsn.seisanquakemlcommonsfile;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

@Getter
public class FileInfo {
    private Set<Path> filePaths;
    private String catalogName;
    private int qmlFileEqualCount;
    private int sFileEqualCount;

    public FileInfo(String path, String fileType) throws IOException {
        this.filePaths = new HashSet<>();
        this.setFilePath(path, fileType);
        this.qmlFileEqualCount = 0;
        this.sFileEqualCount = 0;
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
            try {
                Files.walkFileTree(Paths.get(path), fileScan);
                this.filePaths = fileScan.getFilePaths();
                this.catalogName = fileScan.getCatalogName();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }


            Set<Path> skippedFolders = fileScan.getSkippedFolders();
            if (skippedFolders.size() > 0) {
                skippedFolders.forEach(f -> System.out.println("Skipped folder: " + f.getFileName()));
            }
            Set<Path> failedFileVisit = fileScan.getFailedFileVisit();
            if (failedFileVisit.size() > 0) {
                failedFileVisit.forEach(p -> System.out.println("Skipped file: " + p.getFileName()));
            }
        }
    }

    public int getsFileCount() {
        return this.filePaths.size();
    }
}