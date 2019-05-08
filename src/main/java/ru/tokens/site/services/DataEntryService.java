package ru.tokens.site.services;

import java.util.List;
import ru.tokens.site.entities.DataEntry;

/**
 *
 * @author solon4ak
 */
public interface DataEntryService {
    
    List<DataEntry> getAll();
    
    DataEntry findById(long id);
    
    void save(DataEntry entry);
    
    void delete(long id);
}
