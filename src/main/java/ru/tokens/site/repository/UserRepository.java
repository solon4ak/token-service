package ru.tokens.site.repository;

import java.util.List;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
public interface UserRepository {
    
    List<User> getAll();
    User get(long id);
    void add(User user);
    void update(User user);
    void delete(long id);
    
}
