package no.nnsn.ingestor.dao;

public class SfileChecksum {
    private String sfileID;
    private String checksum;

    public SfileChecksum(String sfileID, String checksum) {
        this.sfileID = sfileID;
        this.checksum = checksum;
    }

    public String getSfileID() {
        return sfileID;
    }

    public String getChecksum() {
        return checksum;
    }
}
