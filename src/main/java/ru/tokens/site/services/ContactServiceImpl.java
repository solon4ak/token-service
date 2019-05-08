package ru.tokens.site.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.Contact;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.repository.ContactRepository;

/**
 *
 * @author solon4ak
 */
@Service
public class ContactServiceImpl implements ContactService {
    
    @Autowired
    private ContactRepository repository;

    @Override
    public List<Contact> getAllContacts() {
        return this.repository.getAll();
    }

    @Override
    public Contact findById(long id) {
        return this.repository.get(id);
    }

    @Override
    public Contact findByEmail(String email) {
        for (Contact c : this.getAllContacts()) {
            if (email.equals(c.getEmail())) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void saveContact(Contact contact) {
        if (contact.getContactId() < 1) {
            this.repository.add(contact);
        } else {
            this.repository.update(contact);
        }
    }

    @Override
    public void deleteContact(Contact contact) {
        this.repository.delete(contact.getContactId());
    }
    
    @Override
    public void deleteContact(long id) {
        this.repository.delete(id);
    }
    
}
