package no.nnsn.seisanquakeml.models.catalog;

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
import java.time.LocalDateTime;
import java.util.*;


@Data
@EqualsAndHashCode(exclude = "sfileEvents")
@Entity
@Table(name = "sfile_information")
@XmlAccessorType(XmlAccessType.FIELD)
public class SfileInformation implements Serializable {

    @Id
    @Column(name = "sfileID", nullable=false, length=128)
    private String sfileID;

    @Lob
    @Column(name = "file", columnDefinition = "MEDIUMBLOB")
    private byte[] file;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "creationTime")
    private LocalDateTime creationTime;

    @Column(name = "lastModifiedTime")
    private LocalDateTime lastModifiedTime;

    @OneToMany(mappedBy = "sfile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<SfileEvent> sfileEvents = new HashSet<>();

    public SfileInformation() {}

    public SfileInformation(String sfileID, byte[] file, LocalDateTime creationTime, LocalDateTime lastModifiedTime) {
        this.sfileID = sfileID;
        this.file = file;
        this.creationTime = creationTime;
        this.lastModifiedTime = lastModifiedTime;
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
