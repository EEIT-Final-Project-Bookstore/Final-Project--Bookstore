package com.finalproject.ispan.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.ispan.domain.AuthorBean;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorBean, Integer> {
    // 可自訂查詢方法，例如依作者名稱查詢
    List<AuthorBean> findByAuthorNameContaining(String authorName);
}
