package no.nnsn.ingestor.components;

import no.nnsn.ingestor.dao.CatalogChange;
import no.nnsn.ingestor.dao.IngestorOptions;
import no.nnsn.ingestor.dao.SfileCheckInfo;
import no.nnsn.seisanquakemlcommonsfile.FileInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileChecker {

    public FileChecker() {}

    private static Map<String, String> getFiles(FileInfo fileInfo) {
        Map<String, String> filesMap = new HashMap<>();
        fileInfo.getFilePaths().forEach(p -> filesMap.put(p.getFileName().toString(), p.toString()));
        return filesMap;
    }

    private static Boolean fileHasChanged(String path, String filename, SfileCheckInfo sfileInfoDB) {
        Path file = Paths.get(path);
        try {
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            SfileCheckInfo sfileInfoCatalog = new SfileCheckInfo(
                    filename,
                    LocalDateTime.ofInstant(attr.creationTime().toInstant(), ZoneId.of("Europe/Paris")),
                    LocalDateTime.ofInstant(attr.lastModifiedTime().toInstant(), ZoneId.of("Europe/Paris"))
            );

            return sfileInfoDB.getLastModifiedTime() == null ||
                    !sfileInfoDB.getLastModifiedTime().equals(sfileInfoCatalog.getLastModifiedTime());

        } catch (IOException exception) {

        }

        return false;
    }


    public static CatalogChange check(List<SfileCheckInfo> sfilesInDatabase, FileInfo fileInfo, IngestorOptions options) {
        Map<String, String> sfilesInCatalog = getFiles(fileInfo);
        Map<String, String> sfilesInCatalogCopy = new HashMap<>(sfilesInCatalog);
        CatalogChange catalogChange = new CatalogChange();
        if (!sfilesInDatabase.isEmpty()) {
            sfilesInDatabase.forEach(sfileInfoDB -> {
                final String dbSfileID = sfileInfoDB.getSfileID();
                if (sfilesInCatalog.containsKey(dbSfileID)) {
                    String path = sfilesInCatalog.get(dbSfileID);
                    if (fileHasChanged(path, dbSfileID, sfileInfoDB)) {
                        catalogChange.addModified(dbSfileID, path);
                    } else {
                        if (options.getForceIngestion()) {
                            catalogChange.addModified(dbSfileID, path);
                        } else {
                            fileInfo.addSfileEqual();
                        }
                    }
                    sfilesInCatalogCopy.remove(dbSfileID);
                } else {
                    catalogChange.addDeleted(dbSfileID);
                }
            });
        }
        catalogChange.addNewFiles(sfilesInCatalogCopy);
        return catalogChange;
    }

}
