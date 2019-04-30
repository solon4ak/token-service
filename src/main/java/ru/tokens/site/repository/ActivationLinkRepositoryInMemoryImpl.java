package ru.tokens.site.repository;

import java.util.Hashtable;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.ActivationLink;

/**
 *
 * @author solon4ak
 */
@Repository
public class ActivationLinkRepositoryInMemoryImpl implements ActivationLinkRepository {
    
    private Map<String, ActivationLink> activationLinksRepository = new Hashtable<>();

    @Override
    public ActivationLink findByToken(final String activationUUIDToken) {
        return this.activationLinksRepository.get(activationUUIDToken);
    }

    @Override
    public void save(ActivationLink link) {
        this.activationLinksRepository.put(link.getToken(), link);
    }

    @Override
    public void delete(ActivationLink link) {
        this.activationLinksRepository.remove(link.getToken());
    }
    
    
}
