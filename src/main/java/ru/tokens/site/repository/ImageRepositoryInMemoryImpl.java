package ru.tokens.site.repository;

import java.util.Hashtable;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.Image;

/**
 *
 * @author solon4ak
 */
@Repository
public class ImageRepositoryInMemoryImpl implements ImageRepository {

    private Map<Long, Image> imgRepository = new Hashtable<>();

    private volatile long IMG_ID_SEQUENCE = 1L;

    private synchronized long getNextId() {
        return this.IMG_ID_SEQUENCE++;
    }

    @Override
    public Image findById(long id) {
        return this.imgRepository.get(id);
    }

    @Override
    public Image save(Image image) {
        image.setId(this.getNextId());
        this.imgRepository.put(image.getId(), image);
        return image;
    }

    @Override
    public void delete(long id) {
        this.imgRepository.remove(id);
    }

}
