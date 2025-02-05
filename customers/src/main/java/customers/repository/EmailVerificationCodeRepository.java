package customers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import customers.domain.EmailVerificationCodes;

@Repository
public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCodes, Long> {

    // 查詢最新的驗證碼
    Optional<EmailVerificationCodes> findTopByEmailOrderByCreateTimeDesc(String email);
}
