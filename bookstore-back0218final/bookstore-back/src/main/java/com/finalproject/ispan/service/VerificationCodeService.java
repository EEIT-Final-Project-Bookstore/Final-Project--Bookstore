package com.finalproject.ispan.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finalproject.ispan.domain.EmailVerificationCodes;
import com.finalproject.ispan.jwt.VerificationCodeGenerator;
import com.finalproject.ispan.repository.EmailVerificationCodeRepository;

@Service
public class VerificationCodeService {

    private final EmailVerificationCodeRepository codeRepository;
    private final EmailService emailService;

//    @Autowired
    public VerificationCodeService(EmailVerificationCodeRepository codeRepository, EmailService emailService) {
        this.codeRepository = codeRepository;
        this.emailService = emailService;
    }

    public void sendVerificationCode(String email) {
        // 1. 產生亂數驗證碼
        String code = VerificationCodeGenerator.generateCode(6);

        // 2. 計算到期時間，如 1 小時後
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);

        // 3. 寫入資料庫
        EmailVerificationCodes entity = new EmailVerificationCodes();
        entity.setEmail(email);
        entity.setCode(code);
        entity.setExpirationTime(expirationTime);
        entity.setCreateTime(LocalDateTime.now());
        codeRepository.save(entity);

        // 4. 寄送 Email
        String subject = "森途書店帳號申請";
        String text = "您的驗證碼為：" + code + "，請在 1 小時內輸入完畢。";
        emailService.sendSimpleMail(email, subject, text);
    }

    public boolean verifyCode(String email, String inputCode) {
        // 1. 從 DB 查詢最新的驗證碼 (若有多筆，可根據 create_time 排序)
        Optional<EmailVerificationCodes> optional = codeRepository.findTopByEmailOrderByCreateTimeDesc(email);

        if (optional.isEmpty()) {
            return false; // 無記錄
        }

        EmailVerificationCodes entity = optional.get();

        // 2. 檢查是否過期
        if (LocalDateTime.now().isAfter(entity.getExpirationTime())) {
            return false; // 過期
        }

        // 3. 比對驗證碼
        if (!entity.getCode().equals(inputCode)) {
            return false; // 驗證碼不符
        }

        return true; // 驗證成功
    }
}
