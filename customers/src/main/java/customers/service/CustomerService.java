package customers.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import customers.domain.CustomerBean;
import customers.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	public CustomerBean login(String username, String password) {
		if(username!=null && username.length()!=0 &&
				password!=null && password.length()!=0) {
			Optional<CustomerBean> optional = customerRepository.findByUsername(username);
			if(optional.isPresent()) {
				CustomerBean bean = optional.get();
				byte[] pass = bean.getPassword();	//資料庫抓出
				byte[] temp = password.getBytes();	//使用者輸入
				if(Arrays.equals(pass, temp)) {
					return bean;
				}
			}
		}
		return null;
	}
	
	public List<CustomerBean> select(CustomerBean bean) {
		List<CustomerBean> result = null;
		if (bean != null && bean.getCustomerID() != null && bean.getCustomerID() > 0) {
			Optional<CustomerBean> optional = customerRepository.findByUsername(bean.getUsername());
			if (optional.isPresent()) {
				result = new ArrayList<CustomerBean>();
				result.add(optional.get());
			}
		} else {
			result = customerRepository.findAll();
		}
		return result;
	}

	public CustomerBean findCustomerByUsername(String username) {
		Optional<CustomerBean> optional = customerRepository.findByUsername(username);
		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	};

	@Transactional
	public CustomerBean insert(CustomerBean bean) {
		if (bean != null && bean.getUsername() != null && bean.getUsername().length() > 0) {
			if (customerRepository.existsByUsername(bean.getUsername())) {
				return null;
			}
			return customerRepository.save(bean);
		}
		return null;
	}

	@Transactional
	public CustomerBean update(CustomerBean bean) {
		Optional<CustomerBean> optional = customerRepository.findByUsername(bean.getUsername());
		if (optional.isPresent()) {
			CustomerBean existingBean = optional.get();
			existingBean.setCustomerName(bean.getCustomerName());
			existingBean.setPassword(bean.getPassword());
			existingBean.setPhoneNumber(bean.getPhoneNumber());
			existingBean.setMobileNumber(bean.getMobileNumber());
			return customerRepository.save(existingBean);
		}
		return null;
	}

	@Transactional
	public boolean delete(CustomerBean bean) {
		if (bean != null && bean.getUsername() != null && bean.getUsername().length() > 0) {
			if (customerRepository.existsByUsername(bean.getUsername())) {
				customerRepository.deleteByUsername(bean.getUsername());
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public boolean exists(String username) {
		if (username != null) {
			return customerRepository.existsByUsername(username);
		}
		return false;
	}

	public CustomerBean create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Long customerID = obj.isNull("customerID") ? null : obj.getLong("customerID");
			String username = obj.isNull("username") ? null : obj.getString("username");
			String customerName = obj.isNull("customerName") ? null : obj.getString("customerName");
			byte[] password = obj.isNull("password") ? null : obj.getString("password").getBytes();
			String email = obj.isNull("email") ? null : obj.getString("email");
			String phoneNumber = obj.isNull("phoneNumber") ? null : obj.getString("phoneNumber");
			String mobileNumber = obj.isNull("mobileNumber") ? null : obj.getString("mobileNumber");

			if (username != null && !customerRepository.existsByUsername(username) && username.length() != 0) {
				CustomerBean insert = new CustomerBean();
				insert.setCustomerID(customerID);
				insert.setUsername(username);
				insert.setCustomerName(customerName);
				insert.setPassword(password);
				insert.setEmail(email);
				insert.setPhoneNumber(phoneNumber);
				insert.setMobileNumber(mobileNumber);

				return customerRepository.save(insert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public CustomerBean modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Long customerID = obj.isNull("customerID") ? null : obj.getLong("customerID");
			String username = obj.isNull("username") ? null : obj.getString("username");
			String customerName = obj.isNull("customerName") ? null : obj.getString("customerName");
			byte[] password = obj.isNull("password") ? null : obj.getString("password").getBytes();
			String email = obj.isNull("email") ? null : obj.getString("email");
			String phoneNumber = obj.isNull("phoneNumber") ? null : obj.getString("phoneNumber");
			String mobileNumber = obj.isNull("mobileNumber") ? null : obj.getString("mobileNumber");

			if (username != null) {
				Optional<CustomerBean> optional = customerRepository.findByUsername(username);
				if (optional.isPresent()) {
					CustomerBean update = optional.get();
					update.setCustomerID(customerID);
					update.setUsername(username);
					update.setCustomerName(customerName);
					update.setPassword(password);
					update.setEmail(email);
					update.setPhoneNumber(phoneNumber);
					update.setMobileNumber(mobileNumber);
					return customerRepository.save(update);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean remove(String username) {
		if (username != null && customerRepository.existsByUsername(username)) {
			customerRepository.deleteByUsername(username);
			return true;
		}
		return false;
	}
}
