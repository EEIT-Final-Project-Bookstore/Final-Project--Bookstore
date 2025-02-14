package bookstore.domain;

import jakarta.persistence.Column;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "email_verifications")
public class EmailVerificationCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;
    @Column(name = "code")
    private String code;
    @Column(name = "expires_at")
    private LocalDateTime expirationTime;
    @Column(name = "created_at")
    private LocalDateTime createTime;
}
