package ru.tokens.site.services;

import java.util.List;
import ru.tokens.site.entities.Contact;

/**
 *
 * @author solon4ak
 */
public interface ContactService {
    
    List<Contact> getAllContacts();
    
    Contact findById(long id);
    
    Contact findByEmail(String email);
    
    void saveContact(Contact contact);
    
    void deleteContact(Contact contact);
    
    void deleteContact(long id);
    
}
