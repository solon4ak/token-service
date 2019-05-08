package ru.tokens.site.repository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.Contact;

/**
 *
 * @author solon4ak
 */
@Repository
public class ContactRepositoryInMemoryImpl implements ContactRepository {
    
    private volatile long CONTACT_ID_SEQUENCE = 1L;

    private synchronized long getNextContactId() {
        return this.CONTACT_ID_SEQUENCE ++;
    }
    
    private Map<Long, Contact> contactRepository = new Hashtable<>();

    @Override
    public List<Contact> getAll() {
        return new ArrayList<>(this.contactRepository.values());
    }

    @Override
    public Contact get(long id) {
        return this.contactRepository.get(id);
    }

    @Override
    public void add(Contact contact) {
        contact.setContactId(this.getNextContactId());
        this.contactRepository.put(contact.getContactId(), contact);
    }

    @Override
    public void update(Contact contact) {
        this.contactRepository.put(contact.getContactId(), contact);
    }

    @Override
    public void delete(long id) {
        this.contactRepository.remove(id);
    }
    
}
