package ru.tokens.site.services;

import ru.tokens.site.entities.Image;

/**
 *
 * @author solon4ak
 */
public interface ImageService {
    
    Image getImageById(long id);
    Image saveImage(Image image);
    void deleteImage(long id);
}
