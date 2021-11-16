package no.nnsn.seisanquakemlcommonsfile;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class FileScan implements FileVisitor<Path> {

    @Getter private String parentPath;
    @Getter private String catalogName;
    @Getter private Set<Path> filePaths = new HashSet<>();
    @Getter private Set<Path> skippedFolders = new HashSet<>();
    @Getter private Set<Path> skippedFiles = new HashSet<>();
    @Getter private Set<Path> failedFileVisit = new HashSet<>();

    public FileScan(String parentPath) {
        this.parentPath = Paths.get(parentPath).getFileName().toString();
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        final String dirName = dir.getFileName().toString();

        if (dirName.equals(this.parentPath)) {
            this.catalogName = dirName;
            return FileVisitResult.CONTINUE;
        }

        if (dirName.equals("CAT") || dirName.equals("LOG")) {
            skippedFolders.add(dir);
            return FileVisitResult.SKIP_SIBLINGS;
        } else {
            return FileVisitResult.CONTINUE;
        }

    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

        // Get Extension
        final String extension = FilenameUtils.getExtension(file.getFileName().toString());

        try {
            if (extension.charAt(0) == 'S') { // Indicator for Sfile
                // Extension can be SLINK
                if (extension.charAt(1) != 'L') {
                    filePaths.add(file);
                }
            } else {
                skippedFiles.add(file);
            }
        } catch (Exception e) { // No extension for path
            skippedFiles.add(file);
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        failedFileVisit.add(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

}
