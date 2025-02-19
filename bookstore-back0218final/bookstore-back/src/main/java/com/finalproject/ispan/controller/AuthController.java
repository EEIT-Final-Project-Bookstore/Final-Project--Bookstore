package com.finalproject.ispan.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.ispan.service.VerificationCodeService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/sendCode")
    public ResponseEntity<String> sendCode(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        verificationCodeService.sendVerificationCode(email);
        return ResponseEntity.ok("驗證碼已寄出");
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String code = req.get("code");

        boolean result = verificationCodeService.verifyCode(email, code);
        if (result) {
            return ResponseEntity.ok("驗證成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("驗證失敗或已過期");
        }
    }
}