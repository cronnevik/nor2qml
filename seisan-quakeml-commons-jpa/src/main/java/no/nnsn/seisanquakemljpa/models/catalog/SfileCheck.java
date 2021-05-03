package no.nnsn.seisanquakemljpa.models.catalog;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.*;


@Data
@EqualsAndHashCode(exclude = "sfileEvents")
@Entity
@Table(name = "sfile_checksum")
@XmlAccessorType(XmlAccessType.FIELD)
public class SfileCheck implements Serializable {

    @Id
    @Column(name = "sfileID", nullable=false, length=128)
    private String sfileID;

    @Lob
    @Column(name = "file", columnDefinition = "MEDIUMBLOB")
    private byte[] file;

    @Column(name = "checksum", columnDefinition = "CHAR(32)", length = 32)
    private String checksum;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @OneToMany(mappedBy = "sfile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<SfileEvent> sfileEvents = new HashSet<>();

    public SfileCheck() {}

    public SfileCheck(String sfileID, byte[] file, String checksum) {
        this.sfileID = sfileID;
        this.file = file;
        this.checksum = checksum;
    }

    public void addEvent(SfileEvent event) {
       this.sfileEvents.add(event);
    }

    public void setSfileEvents(Set<SfileEvent> sfileEvents) {
        this.sfileEvents = sfileEvents;
        // Hibernate relationship specific
        if (sfileEvents != null) {
            for (SfileEvent sf: sfileEvents) {
                sf.setSfile(this);
            }
        }
    }



}
