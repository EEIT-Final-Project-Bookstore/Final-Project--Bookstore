package com.finalproject.ispan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ispan.domain.PublisherBean;
import com.finalproject.ispan.repository.PublisherRepository;

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

    // 依名稱模糊查詢
    public List<PublisherBean> findPublishersByName(String name) {
        return publisherRepository.findByPublisherNameContaining(name);
    }

    // 新增出版社
    public PublisherBean createPublisher(PublisherBean publisher) {
        // 可選：檢查名稱是否重複
        if (publisherRepository.findByPublisherNameContaining(publisher.getPublisherName()).size() > 0) {
            throw new RuntimeException("Publisher with the same name already exists");
        }
        return publisherRepository.save(publisher);
    }

    // 更新出版社
    public PublisherBean updatePublisher(Integer id, PublisherBean updatedPublisher) {
        PublisherBean publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        // 更新資料
        publisher.setPublisherName(updatedPublisher.getPublisherName());
        publisher.setPublisherDescription(updatedPublisher.getPublisherDescription());
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