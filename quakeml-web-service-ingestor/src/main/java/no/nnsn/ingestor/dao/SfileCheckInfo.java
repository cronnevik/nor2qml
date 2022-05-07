package no.nnsn.ingestor.dao;

import lombok.Getter;

import java.time.LocalDateTime;

public class SfileCheckInfo {
    @Getter
    private String sfileID;
    @Getter
    private LocalDateTime creationTime;
    @Getter
    private LocalDateTime lastModifiedTime;

    public SfileCheckInfo(String sfileID, LocalDateTime creationTime, LocalDateTime lastModifiedTime) {
        this.sfileID = sfileID;
        this.creationTime = creationTime;
        this.lastModifiedTime = lastModifiedTime;
    }
}
