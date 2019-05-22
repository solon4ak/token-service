package ru.tokens.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.Image;
import ru.tokens.site.repository.ImageRepository;

/**
 *
 * @author solon4ak
 */
@Service
public class ImageServiceImpl implements ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image getImageById(long id) {
        return this.imageRepository.findById(id);
    }

    @Override
    public Image saveImage(Image image) {
        return this.imageRepository.save(image);
    }

    @Override
    public void deleteImage(long id) {
        this.imageRepository.delete(id);
    }
}
