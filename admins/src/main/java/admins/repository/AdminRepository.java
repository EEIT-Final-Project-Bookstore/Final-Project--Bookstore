package admins.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import admins.domain.AdminBean;

public interface AdminRepository extends JpaRepository<AdminBean, Long> {
	 // 依 adminAccount 來查找，回傳 Optional 型別，以利後續判斷該筆資料是否存在
    Optional<AdminBean> findByAdminAccount(String adminAccount);
    
    // 依 adminAccount 進行刪除
    void deleteByAdminAccount(String adminAccount);

    // 檢查某 adminAccount 是否已存在
    boolean existsByAdminAccount(String adminAccount);

}
