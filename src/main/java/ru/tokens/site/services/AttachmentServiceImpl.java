package ru.tokens.site.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.DataEntry;
import ru.tokens.site.entities.User;
import ru.tokens.site.repository.AttachmentRepository;

/**
 *
 * @author solon4ak
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public List<Attachment> getAll() {
        return this.attachmentRepository.list();
    }

    @Override
    public Attachment findById(long id) {
        return this.attachmentRepository.get(id);
    }

    @Override
    public void save(Attachment attachment) {
        if (attachment.getId() < 1) {
            this.attachmentRepository.create(attachment);
        } else {
            this.attachmentRepository.update(attachment);
        }
    }

    @Override
    public void delete(long id) {
        this.attachmentRepository.delete(id);
    }

}
