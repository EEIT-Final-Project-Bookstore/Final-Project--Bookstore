package bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bookstore.util.EcpayFunctions;



@Controller
@RequestMapping("/pages/ecpay")
public class EcpayController {
	@Autowired
	private EcpayFunctions ecpayFunctions;
	
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
}
