package no.nnsn.ingestor.utils;

import lombok.Getter;

@Getter
public class CleanLog {
    private int files;

    public CleanLog() {
        this.files = 0;
    }

    public void increaseFiles(int files) {
        this.files += files;
    }
}
