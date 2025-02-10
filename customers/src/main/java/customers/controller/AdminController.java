package customers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import customers.DTO.AdminDTO;
import customers.service.AdminService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /*
     * 新增管理員
     */
    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody AdminDTO adminDTO) {
        try {
            AdminDTO createdAdmin = adminService.createAdmin(adminDTO);
            return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * 查詢全部管理員
     */
    @GetMapping
    public ResponseEntity<?> getAllAdmins() {
        try {
            List<AdminDTO> adminList = adminService.getAllAdmins();
            return ResponseEntity.ok(adminList);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 查詢單一管理員(以 adminAccount)
     */
    @GetMapping("/{adminAccount}")
    public ResponseEntity<?> getAdminByAccount(@PathVariable String adminAccount) {
        try {
            AdminDTO admin = adminService.getAdminByAccount(adminAccount);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /*
     * 修改管理員(以 adminAccount)
     */
    @PutMapping("/{adminAccount}")
    public ResponseEntity<?> updateAdmin(@PathVariable String adminAccount, @RequestBody AdminDTO adminDTO) {
        try {
            AdminDTO updatedAdmin = adminService.updateAdmin(adminAccount, adminDTO);
            return ResponseEntity.ok(updatedAdmin);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * 刪除管理員(以 adminAccount)
     */
    @DeleteMapping("/{adminAccount}")
    public ResponseEntity<?> deleteAdmin(@PathVariable String adminAccount) {
        try {
            adminService.deleteAdminByAccount(adminAccount);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
