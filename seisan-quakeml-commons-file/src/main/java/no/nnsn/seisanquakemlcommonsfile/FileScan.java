package no.nnsn.seisanquakemlcommonsfile;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
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
        this.parentPath = parentPath;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        final String dirName = dir.getFileName().toString();

        // Only scan through directories with number as name
        if(dirName.matches("^[a-zA-Z0-9]+[_]$")) { // Catalog folder is with letters and underscore at the end
            this.catalogName = dirName.substring(0, dirName.length() -1); // remove the underscore from catalog name
            return FileVisitResult.CONTINUE;
        } else if(dirName.matches("^[a-zA-Z0-9]+[_][_]$")) { // Catalog folder is with letters and two underscores at the end
            this.catalogName = dirName.substring(0, dirName.length() - 2); // remove the two underscores from catalog name
            return FileVisitResult.CONTINUE;
        } else if (dirName.matches("[0-9 ]+") || dir.toString().equals(this.parentPath)) {
            return FileVisitResult.CONTINUE;
        } else {
            // Skip subfolders if not valid by the regex patterns
            skippedFolders.add(dir);
            return FileVisitResult.SKIP_SUBTREE;
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
