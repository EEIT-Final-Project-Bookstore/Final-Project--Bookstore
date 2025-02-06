package bookstore.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
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

import bookstore.domain.CustomerBean;
import bookstore.repository.CustomerRepository;
import bookstore.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	// 若日期需要轉換，保留此設定
	@InitBinder
	public void method1(WebDataBinder webDataBinder) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(format, true);
		webDataBinder.registerCustomEditor(java.util.Date.class, editor);
	}

	// === 以下為 RESTful API ===

	// 1. 查詢所有客戶 (支援條件查詢: username)
	@GetMapping
	public ResponseEntity<List<CustomerBean>> getAllCustomers(@RequestParam(required = false) String username) {
		CustomerBean query = new CustomerBean();
		query.setUsername(username);
		List<CustomerBean> result = customerService.select(query);
		return ResponseEntity.ok(result);
	}

	// 2. 檢查 Email 是否已存在
	@GetMapping("/check-email")
	public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
		boolean exists = customerRepository.existsByEmail(email);
		Map<String, Boolean> response = new HashMap<>();
		response.put("exists", exists);
		return ResponseEntity.ok(response);
	}

	// 3. 根據用戶名查詢客戶
	@GetMapping("/{username}")
	public ResponseEntity<CustomerBean> getCustomerByUsername(@PathVariable String username) {
		CustomerBean customer = customerService.findCustomerByUsername(username);
		if (customer != null) {
			return ResponseEntity.ok(customer);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 4. 新增客戶
	@PostMapping
	public ResponseEntity<CustomerBean> addCustomer(@RequestBody CustomerBean customer) {
		CustomerBean createdCustomer = customerService.insert(customer);
		if (createdCustomer != null) {
			return ResponseEntity.ok(createdCustomer);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	// 5. 修改客戶
	@PutMapping("/{username}")
	public ResponseEntity<CustomerBean> updateCustomer(@PathVariable String username,
			@RequestBody CustomerBean customer) {
		customer.setUsername(username);
		CustomerBean updatedCustomer = customerService.update(customer);
		if (updatedCustomer != null) {
			return ResponseEntity.ok(updatedCustomer);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 6. 刪除客戶
	@DeleteMapping("/{username}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable String username) {
		boolean deleted = customerService.remove(username);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}