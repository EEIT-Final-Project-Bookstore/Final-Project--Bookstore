package com.finalproject.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.ispan.domain.NotificationTypeBean;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationTypeBean, Integer> {

}
