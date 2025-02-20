package com.finalproject.ispan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.ispan.domain.PublisherBean;
import com.finalproject.ispan.service.PublisherService;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    // 查詢所有出版社
    @GetMapping
    public List<PublisherBean> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    // 依 ID 查詢出版社
    @GetMapping("/{id}")
    public ResponseEntity<PublisherBean> getPublisherById(@PathVariable Integer id) {
        return ResponseEntity.ok(publisherService.getPublisherById(id));
    }

    // 新增出版社
    @PostMapping
    public ResponseEntity<PublisherBean> createPublisher(@RequestBody PublisherBean publisher) {
        return ResponseEntity.ok(publisherService.createPublisher(publisher));
    }

    // 更新出版社
    @PutMapping("/{id}")
    public ResponseEntity<PublisherBean> updatePublisher(@PathVariable Integer id, @RequestBody PublisherBean publisher) {
        return ResponseEntity.ok(publisherService.updatePublisher(id, publisher));
    }

    // 刪除出版社
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Integer id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
