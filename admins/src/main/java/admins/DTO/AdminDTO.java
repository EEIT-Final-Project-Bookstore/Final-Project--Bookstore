package admins.DTO;

public class AdminDTO {
    private Long adminId; // 對應 AdminID
    private String adminAccount; // 對應 AdminsAccount
    private String email; // 對應 Email
    private byte[] password; // 對應 Password
    private Long rankId; // 對應 RankID

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    @Override
    public String toString() {
        return "AdminDTO [adminId=" + adminId + ", adminAccount=" + adminAccount +
                ", email=" + email + ", rankId=" + rankId + "]";
    }
}
