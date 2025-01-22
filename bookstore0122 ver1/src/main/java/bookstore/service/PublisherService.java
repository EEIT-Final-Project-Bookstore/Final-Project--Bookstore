package bookstore.service;

import bookstore.domain.PublisherBean;
import bookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    // 查詢所有出版社
    public List<PublisherBean> getAllPublishers() {
        return publisherRepository.findAll();
    }

    // 依 ID 查詢出版社
    public PublisherBean getPublisherById(Integer id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
    }

    // 新增出版社
    public PublisherBean createPublisher(PublisherBean publisher) {
        return publisherRepository.save(publisher);
    }

    // 刪除出版社
    public void deletePublisher(Integer id) {
        if (!publisherRepository.existsById(id)) {
            throw new RuntimeException("Publisher not found");
        }
        publisherRepository.deleteById(id);
    }
}
