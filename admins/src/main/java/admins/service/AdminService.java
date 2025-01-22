package admins.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admins.DTO.AdminDTO;
import admins.domain.AdminBean;
import admins.domain.RankBean;
import admins.repository.AdminRepository;
import admins.repository.RankRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RankRepository rankRepository;

    /**
     * 新增管理員
     */
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        // 1. 透過 rankId 找到對應的 RankBean
        RankBean rank = rankRepository.findById(adminDTO.getRankId())
                .orElseThrow(() -> new RuntimeException("Rank not found"));

        // 2. 建立 AdminBean 並設定屬性
        AdminBean adminBean = new AdminBean();
        adminBean.setAdminAccount(adminDTO.getAdminAccount());
        adminBean.setEmail(adminDTO.getEmail());
        adminBean.setPassword(adminDTO.getPassword());
        adminBean.setRank(rank);

        // 3. 儲存到資料庫
        AdminBean savedAdmin = adminRepository.save(adminBean);

        // 4. 將儲存後的 AdminBean 轉成 AdminDTO
        return convertToDTO(savedAdmin);
    }

    /**
     * 查詢全部管理員
     */
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::convertToDTO) // 使用轉換方法
                .collect(Collectors.toList());
    }

    /**
     * 根據 adminAccount 查詢管理員
     */
    public AdminDTO getAdminByAccount(String adminAccount) {
        AdminBean admin = adminRepository.findByAdminAccount(adminAccount)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return convertToDTO(admin);
    }

    /**
     * 更新管理員
     */
    public AdminDTO updateAdmin(String adminAccount, AdminDTO adminDTO) {
        // 1. 先找到現有的 AdminBean
        AdminBean existingAdmin = adminRepository.findByAdminAccount(adminAccount)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // 2. 找到新的 RankBean
        RankBean rank = rankRepository.findById(adminDTO.getRankId())
                .orElseThrow(() -> new RuntimeException("Rank not found"));

        // 3. 更新資料
        existingAdmin.setEmail(adminDTO.getEmail());
        existingAdmin.setPassword(adminDTO.getPassword());
        existingAdmin.setRank(rank);

        // 4. 儲存並轉回 DTO
        AdminBean updatedAdmin = adminRepository.save(existingAdmin);
        return convertToDTO(updatedAdmin);
    }

    /**
     * 刪除管理員
     */
    public void deleteAdminByAccount(String adminAccount) {
        AdminBean admin = adminRepository.findByAdminAccount(adminAccount)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        adminRepository.delete(admin);
    }

    /**
     * 將 AdminBean 轉換為 AdminDTO
     */
    private AdminDTO convertToDTO(AdminBean adminBean) {
        AdminDTO dto = new AdminDTO();
        dto.setAdminId(adminBean.getAdminId());
        dto.setAdminAccount(adminBean.getAdminAccount());
        dto.setEmail(adminBean.getEmail());
        dto.setPassword(adminBean.getPassword());
        dto.setRankId(adminBean.getRank() != null ? adminBean.getRank().getRankID() : null);
        return dto;
    }
}
