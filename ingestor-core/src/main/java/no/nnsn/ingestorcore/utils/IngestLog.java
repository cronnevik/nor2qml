package no.nnsn.ingestorcore.utils;

import lombok.Getter;

@Getter
public class IngestLog {
    private int events;
    private int files;

    public IngestLog() {
        this.events = 0;
        this.files = 0;
    }

    public void increaseEvents(int events) {
        this.events += events;
    }

    public void increaseFiles(int files) {
        this.files += files;
    }
}
