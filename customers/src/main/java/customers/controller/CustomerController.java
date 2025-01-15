package customers.controller;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import customers.domain.CustomerBean;
import customers.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @InitBinder
	public void method1(WebDataBinder webDataBinder) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(format, true);
		webDataBinder.registerCustomEditor(java.util.Date.class, editor);
	}

	@GetMapping("/")
	public String handleGetRequest(Model model) {
		return "/pages/customers";
	}
	// 查詢所有客戶
    @GetMapping
    public ResponseEntity<List<CustomerBean>> getAllCustomers(@RequestParam(required = false) String username) {
        CustomerBean query = new CustomerBean();
        query.setUsername(username);
        List<CustomerBean> result = customerService.select(query);
        return ResponseEntity.ok(result);
    }

    // 根據用戶名查詢單一客戶
    @GetMapping("/{username}")
    public ResponseEntity<CustomerBean> getCustomerByUsername(@PathVariable String username) {
        CustomerBean customer = customerService.findCustomerByUsername(username);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 新增客戶
    @PostMapping
    public ResponseEntity<CustomerBean> addCustomer(@RequestBody CustomerBean customer) {
        CustomerBean createdCustomer = customerService.insert(customer);
        if (createdCustomer != null) {
            return ResponseEntity.ok(createdCustomer);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 修改客戶
    @PutMapping("/{username}")
    public ResponseEntity<CustomerBean> updateCustomer(@PathVariable String username, @RequestBody CustomerBean customer) {
        customer.setUsername(username);
        CustomerBean updatedCustomer = customerService.update(customer);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 刪除客戶
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String username) {
        boolean deleted = customerService.remove(username);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	@PostMapping("/pages/customers/controller")
	public String method2(Model model, CustomerBean bean, Errors errors, String custaction) {
		Map<String, String> error = new HashMap<>();
		model.addAttribute("error", error);
		if (errors != null && errors.hasFieldErrors("username")) {
			error.put("username", "帳號必須是字串");
		}
		if (errors != null && errors.hasFieldErrors("customerName")) {
			error.put("customerName", "用戶名必須是字串");
		}
		if (errors != null && errors.hasFieldErrors("email")) {
			error.put("email", "電子信箱必須是字串");
		}
		if (errors != null && errors.hasFieldErrors("phoneNumber")) {
			error.put("phoneNumber", "市話必須是數字");
		}
		if (errors != null && errors.hasFieldErrors("mobileNumber")) {
			error.put("mobileNumber", "手機號碼必須是數字");
		}

		// 驗證
		if ("Insert".equals(custaction) || "Update".equals(custaction) || "Delete".equals(custaction)) {
			if (errors != null && !errors.hasFieldErrors("id")) {
				if (bean == null || bean.getUsername() == null || bean.getUsername().length() == 0) {
					error.put("username", "帳號是必要欄位");
				}
			}
		}
		//
		if (error != null && !error.isEmpty()) {
			return "/pages/customers";
		}
		if ("Select".equals(custaction)) {
			List<CustomerBean> result = customerService.select(bean);
			model.addAttribute("select", result);
			return "/pages/display";
		}

		Long timeStamp = System.currentTimeMillis();
		LocalDateTime timeResult = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.of("Asia/Taipei"));
		bean.setRegistrationTime(timeResult);

		if ("Insert".equals(custaction)) {
			CustomerBean result = customerService.insert(bean);
			if (result != null) {
				model.addAttribute("insert", result);
			} else {
				error.put("action", "此帳號已存在，新增失敗");
			}
		}
		if ("Update".equals(custaction)) {
			CustomerBean result = customerService.update(bean);
			if (result != null) {
				model.addAttribute("update", result);
			} else {
				error.put("action", "此帳號不存在，修改失敗");
			}
		}
		if ("Delete".equals(custaction)) {
			boolean success = customerService.delete(bean);
			if (success) {
				model.addAttribute("delete", "刪除成功");
			} else {
				error.put("action", "此帳號不存在，刪除失敗");
			}
		}

		return "/pages/customers";
	}
}
