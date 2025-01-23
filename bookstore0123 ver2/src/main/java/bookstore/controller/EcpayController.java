package bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bookstore.domain.OrderBean;
import bookstore.domain.StatusBean;
import bookstore.repository.OrderRepository;
import bookstore.repository.StatusesRepository;
import bookstore.util.EcpayFunctions;



@Controller
@RequestMapping("/pages/ecpay")
public class EcpayController {
	@Autowired
	private EcpayFunctions ecpayFunctions;
	@Autowired 
	private  OrderRepository orderRepository;
	@Autowired 
	private  StatusesRepository statusesRepository;
	@PostMapping("/return")
	public String ecpayReturn(@RequestBody String body) {
		System.out.println("ecpay return "+System.currentTimeMillis());
		System.out.println("body="+body);
		// 假設處理成功，更新訂單狀態
	    boolean isValid = true; // 這裡可以加入驗證邏輯
	    if (isValid) {
	        System.out.println("支付處理成功，跳轉到 call.html");
	    } else {
	        System.out.println("支付處理失敗");
	    }

	    
	    return "";
	}
	
	@PostMapping("/send")
	@ResponseBody
	@CrossOrigin
	public String send(@RequestBody String body) {
		String form = ecpayFunctions.buildEcpayForm(body);
		
        return form;
	}
	@GetMapping("/updateStatusOnReturn")
	public String updateStatusOnReturn(@RequestParam("MerchantTradeNo") String merchantTradeNo) {
	    // 提取訂單 ID
	    Integer orderID = extractOrderID(merchantTradeNo);

	    // 查詢訂單
	    OrderBean order = orderRepository.findById(orderID)
	            .orElseThrow(() -> new RuntimeException("未找到訂單，OrderID: " + orderID));

	    // 查詢目標狀態
	    StatusBean paidStatus = statusesRepository.findById(1)
	            .orElseThrow(() -> new RuntimeException("未找到狀態 ID 為 1 的記錄"));

	    // 更新訂單狀態
	    order.setStatus(paidStatus);
	    orderRepository.save(order); // 保存變更到資料庫

	    // 重定向到 callback.html
	    return "redirect:/callback.html";
	}
	// 提取訂單 ID 的方法
	private Integer extractOrderID(String merchantTradeNo) {
	    // 假設 MerchantTradeNo 格式為 "BooksOrder2025012116"
	    return Integer.parseInt(merchantTradeNo.replace("BooksOrder", ""));
	}


}
