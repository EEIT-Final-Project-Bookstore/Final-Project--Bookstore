package customers.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import customers.DTO.AdminDTO;
import customers.domain.AdminBean;
import customers.domain.RankBean;
import customers.repository.AdminRepository;
import customers.repository.RankRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RankRepository rankRepository;

    public AdminBean adminLogin(String adminAccount, String passwordStr) {
        // 1. 先從 DB 中找該帳號
        AdminBean admin = adminRepository.findByAdminAccount(adminAccount)
                .orElse(null);
        if (admin == null) {
            return null; // 帳號不存在
        }

        // 2. 比對密碼
        // 注意：你的 DB 目前存的是 byte[] (admin.getPassword())
        // 你需要確認此 byte[] 是否已經是雜湊後的結果
        // 如果只是明碼的 getBytes()，可直接 Arrays.equals
        // 否則需做雜湊比對
        byte[] storedPasswordBytes = admin.getPassword(); // DB 裡的
        byte[] inputPasswordBytes = passwordStr.getBytes(); // 前端輸入的

        // 簡單比對 (不含安全雜湊流程，僅示範)
        if (!java.util.Arrays.equals(storedPasswordBytes, inputPasswordBytes)) {
            return null; // 密碼不符
        }

        // 3. 成功，回傳 adminBean
        return admin;
    }

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
