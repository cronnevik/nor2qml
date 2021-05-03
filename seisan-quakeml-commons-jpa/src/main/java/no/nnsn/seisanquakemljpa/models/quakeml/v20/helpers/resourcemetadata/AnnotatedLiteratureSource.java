package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_rmAnnLitSrc")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnnotatedLiteratureSource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer annLitSrcID;

    /*
     *   Relations
     */

    @OneToOne(mappedBy = "annotatedLiteratureSource", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private LiteratureSource source;

    @OneToOne(mappedBy = "annotatedLiteratureSource", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Comment annotation;

    /*
     *   Custom setter methods for relations
     */

    public void setComment(Comment cmt) {
        this.annotation = cmt;
        // Hibernate relationship specific
        if (cmt != null) {
            cmt.setAnnotatedLiteratureSource(this);
        }

    }

    public void setSource(LiteratureSource source) {
        this.source = source;
        if (source != null) {
            source.setAnnotatedLiteratureSource(this);
        }
    }
}
