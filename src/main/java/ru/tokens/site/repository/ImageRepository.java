package ru.tokens.site.repository;

import ru.tokens.site.entities.Image;

/**
 *
 * @author solon4ak
 */
public interface ImageRepository {
    
    Image findById(long id);
    Image save(Image image);
    void delete(long id);
    
}
