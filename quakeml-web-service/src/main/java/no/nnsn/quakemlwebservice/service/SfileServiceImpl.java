package no.nnsn.quakemlwebservice.service;

import no.nnsn.quakemlwebservice.repository.SfileRepository;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SfileServiceImpl implements SfileService {
    @Autowired
    SfileRepository repo;

    @Override
    public SfileCheck getSfileById(String id) {
        return repo.findById(id).get();
    }

    @Override
    public List<SfileCheck> getSfiles(List<String> sfileIds) {
        return repo.getSfiles(sfileIds);
    }

}
