package ru.tokens.site.repository;

import java.util.List;
import ru.tokens.site.entities.DataEntry;

/**
 *
 * @author solon4ak
 */
public interface DataEntryRepository {
    
    List<DataEntry> list();
    
    DataEntry find(long id);
    
    void create(DataEntry entry);
    
    void update(DataEntry entry);
    
    void delete(long id);
}
