package admins.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Admins") // 對應資料表名稱
public class AdminBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminID") // 對應資料表中的 AdminID 欄位
    private Long adminId;

    @Column(unique = true, name = "AdminsAccount") // 對應資料表中的 AdminsAccount 欄位
    private String adminAccount;

    @Column(name = "Password") // 對應資料表中的 Password 欄位
    private byte[] password;

    @Column(name = "Email") // 對應資料表中的 Email 欄位
    private String email;

    @ManyToOne
    @JoinColumn(name = "RankID", referencedColumnName = "RankId") // 對應資料表中的 RankID 欄位，外鍵指向 Rank 表的主鍵 RankId
    private RankBean rank;

    @Override
    public String toString() {
        return "AdminBean [adminId=" + adminId + ", adminAccount=" + adminAccount + 
                ", password=" + password + ", email=" + email + ", rank=" + rank + "]";
    }

    // Getters and Setters
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RankBean getRank() {
        return rank;
    }

    public void setRank(RankBean rank) {
        this.rank = rank;
    }
}
