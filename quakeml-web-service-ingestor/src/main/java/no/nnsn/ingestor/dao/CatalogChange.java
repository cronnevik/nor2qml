package no.nnsn.ingestor.dao;


import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CatalogChange {
    @Getter Map<String, String> newFiles;
    @Getter Map<String, String> modifiedFiles;
    @Getter Set<String> deletedFiles;

    public CatalogChange() {
        this.newFiles = new HashMap<>();
        this.modifiedFiles = new HashMap<>();
        this.deletedFiles = new HashSet<>();
    }

    public void addNew(String sfileID, String path) {newFiles.put(sfileID, path);}
    public void addModified(String dbSfileID, String path) {modifiedFiles.put(dbSfileID, path);}
    public void addDeleted(String dbSfileID) {deletedFiles.add(dbSfileID);}
}
