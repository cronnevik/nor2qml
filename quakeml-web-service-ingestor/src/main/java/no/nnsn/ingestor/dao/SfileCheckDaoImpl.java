package no.nnsn.ingestor.dao;

import no.nnsn.seisanquakemljpa.models.catalog.SfileInformation;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import java.util.List;

public class SfileCheckDaoImpl implements SfileCheckDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addSfile(SfileInformation sfileInformation) {
        sessionFactory.getCurrentSession().save(sfileInformation);
    }

    @Override
    public List<SfileInformation> getSfiles() {
        @SuppressWarnings("unchecked")
        TypedQuery<SfileInformation> query=sessionFactory.getCurrentSession().createQuery("from SfileInformation");
        return query.getResultList();
    }
}
