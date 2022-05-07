package no.nnsn.ingestor.dao;


import lombok.Getter;

import java.util.*;

public class CatalogChange {
    @Getter Map<String, String> newFiles;
    @Getter Map<String, String> modifiedFiles;
    @Getter Set<String> deletedFiles;

    public CatalogChange() {
        this.newFiles = new HashMap<>();
        this.modifiedFiles = new HashMap<>();
        this.deletedFiles = new HashSet<>();
    }

    public void addNewFiles(Map<String, String> sfiles) {
        for (Map.Entry<String, String> sfile : sfiles.entrySet()) {
            newFiles.put(sfile.getKey(), sfile.getValue());
        }
    }
    public void addModified(String dbSfileID, String path) {modifiedFiles.put(dbSfileID, path);}
    public void addDeleted(String dbSfileID) {deletedFiles.add(dbSfileID);}
}
