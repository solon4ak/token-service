package ru.tokens.site.repository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
@Repository
public class UserRepositoryInMemoryImpl implements UserRepository {
    
    private volatile long USER_ID_SEQUENCE = 1L;
    
    private synchronized long getNextUserId() {
        return this.USER_ID_SEQUENCE ++;
    }
    
    private final Map<Long, User> userDatabase = new Hashtable<>();

    public UserRepositoryInMemoryImpl() {
        super();
        
        User u1 = new User();
        u1.setUserEmailAddress("kastor@bk.ru");
        u1.setPassword("kastor");
        
        User u2 = new User();
        u2.setUserEmailAddress("solon4ak@gmail.com");
        u2.setPassword("solon4ak");
        
        this.add(u1);
        this.add(u2);
    }
        
    @Override
    public List<User> getAll() {
        return new ArrayList<>(this.userDatabase.values());
    }

    @Override
    public User get(long id) {
        return this.userDatabase.get(id);
    }

    @Override
    public void add(User user) {
        user.setUserId(this.getNextUserId());
        this.userDatabase.put(user.getUserId(), user);
    }

    @Override
    public void update(User user) {
        this.userDatabase.put(user.getUserId(), user);
    }

    @Override
    public void delete(long id) {
        this.userDatabase.remove(id);
    }
    
}
