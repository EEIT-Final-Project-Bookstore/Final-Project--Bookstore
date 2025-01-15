package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.BooksBean;

public interface BookRepository extends JpaRepository<BooksBean, Integer> {
    List<BooksBean> findByBookNameContainingIgnoreCase(String keyword);


    Optional<BooksBean> findByBookId(Integer bookId);


}                   



