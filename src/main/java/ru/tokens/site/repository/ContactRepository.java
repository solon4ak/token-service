package ru.tokens.site.repository;

import java.util.List;
import ru.tokens.site.entities.Contact;

/**
 *
 * @author solon4ak
 */
public interface ContactRepository {
    
    List<Contact> getAll();
    Contact get(long id);
    void add(Contact contact);
    void update(Contact contact);
    void delete(long id);
}
