package com.finalproject.ispan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.ispan.domain.NotificationTypeBean;
import com.finalproject.ispan.repository.NotificationTypeRepository;

@RestController
@RequestMapping("/api")  // ✅ 讓前端請求 "/api/notification-types" 來獲取通知類型
public class NotificationTypeController {
   @Autowired
   private NotificationTypeRepository notificationTypeRepository;
   /**
    * 獲取所有通知類型
    */
   @GetMapping("/notification-types")
   public List<NotificationTypeBean> getAllNotificationTypes() {
       return notificationTypeRepository.findAll();
   }
}
