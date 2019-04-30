package ru.tokens.site.repository;

import ru.tokens.site.entities.ActivationLink;

/**
 *
 * @author solon4ak
 */
public interface ActivationLinkRepository {
    
    ActivationLink findByToken(final String activationUUIDToken);
    
    void save(final ActivationLink link);
    
    void delete(final ActivationLink link);
}
