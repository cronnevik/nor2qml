package no.nnsn.ingestorcore.dao;

import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import java.util.List;

public class SfileCheckDaoImpl implements SfileCheckDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addSfile(SfileCheck sfileCheck) {
        sessionFactory.getCurrentSession().save(sfileCheck);
    }

    @Override
    public List<SfileCheck> getSfiles() {
        @SuppressWarnings("unchecked")
        TypedQuery<SfileCheck> query=sessionFactory.getCurrentSession().createQuery("from SfileCheck");
        return query.getResultList();
    }
}
