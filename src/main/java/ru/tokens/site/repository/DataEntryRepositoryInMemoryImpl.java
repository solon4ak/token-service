package ru.tokens.site.repository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.DataEntry;

/**
 *
 * @author solon4ak
 */
@Repository
public class DataEntryRepositoryInMemoryImpl 
        implements DataEntryRepository {
    
    private Map<Long, DataEntry> repo = new Hashtable<>();
    
    private volatile long DATAENTRY_ID_SEQUENCE = 1L;
    
    private synchronized long getNextId() {
        return this.DATAENTRY_ID_SEQUENCE ++;
    }

    @Override
    public List<DataEntry> list() {
        return new ArrayList<>(this.repo.values());
    }

    @Override
    public DataEntry find(long id) {
        return this.repo.get(id);
    }

    @Override
    public void create(DataEntry entry) {
        entry.setId(this.getNextId());
        this.repo.put(entry.getId(), entry);
    }

    @Override
    public void update(DataEntry entry) {
        this.repo.put(entry.getId(), entry);
    }

    @Override
    public void delete(long id) {
        this.repo.remove(id);
    }    
    
}
