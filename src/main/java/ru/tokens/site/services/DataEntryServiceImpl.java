package ru.tokens.site.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.DataEntry;
import ru.tokens.site.repository.DataEntryRepository;

/**
 *
 * @author solon4ak
 */
@Service
public class DataEntryServiceImpl implements DataEntryService {
    
    @Autowired
    private DataEntryRepository entryRepository;

    @Override
    public List<DataEntry> getAll() {
        return this.entryRepository.list();
    }

    @Override
    public DataEntry findById(long id) {
        return this.entryRepository.find(id);
    }

    @Override
    public void save(DataEntry entry) {
        if (entry.getId() < 1) {
            this.entryRepository.create(entry);
        } else {
            this.entryRepository.update(entry);
        }
    }

    @Override
    public void delete(long id) {
        this.entryRepository.delete(id);
    }
    
}
