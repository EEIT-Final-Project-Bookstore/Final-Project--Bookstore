package bookstore.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookstore.domain.OrderBean;
import bookstore.domain.StatusBean;
import bookstore.repository.OrderRepository;
<<<<<<< HEAD
import bookstore.repository.StatusRepository;
=======
import bookstore.repository.StatusesRepository;
>>>>>>> a54bf433a72d9698fcf5758036056789b27b8af3
import bookstore.service.OrderService;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ecpay")
public class EcpayController {
	@Value("${myngrok.url}")
	private String myngrokURL;

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
<<<<<<< HEAD
	private StatusRepository statusRepository;
=======
	private StatusesRepository statusRepository;
>>>>>>> a54bf433a72d9698fcf5758036056789b27b8af3

	// 1.生成綠界支付頁面
	@GetMapping("/checkout/{orderId}")
	public void ecpayCheckout(@PathVariable String orderId, HttpServletResponse response) throws IOException {
		// 從資料庫查詢訂單資料
		OrderBean order = orderRepository.findByOrderId(orderId)
				.orElseThrow(() -> new RuntimeException("訂單資料不存在，OrderId: " + orderId));
		// System.out.println("資料庫中讀取的最終金額: " + order.getFinalAmount());
		// 設定金流:
		AllInOne aio = new AllInOne("");
		AioCheckOutOneTime aioCheck = new AioCheckOutOneTime();
		// 特店編號
		aioCheck.setMerchantID("2000132");
		// 訂單編號
		aioCheck.setMerchantTradeNo(order.getOrderId());
		// 特店交易時間
		aioCheck.setMerchantTradeDate(
				order.getOrderCreationTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))); // 格式化日期);
		// 交易金額
		aioCheck.setTotalAmount(order.getFinalAmount().toString());
		// 交易描述
		aioCheck.setTradeDesc("書店訂單支付");
		// 商品名稱
		aioCheck.setItemName("書籍商品-" + order.getOrderId());
		// 付款完成後後端接收付款結果網址
		aioCheck.setReturnURL(myngrokURL + "/ecpay/returnURL"); // 在.properties改ngrok的網址
		// 導向前端付款結果
		aioCheck.setClientBackURL("http://localhost:5173/orders/paymentResult");

		// 輸出畫面
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		out.print(aio.aioCheckOut(aioCheck, null));
	}

	// 2.綠界回傳
	@PostMapping("/returnURL") // 下面 @RequestParam 都是綠界在交易完成後回傳的參數
	public String returnURL(@RequestParam("MerchantTradeNo") String MerchantTradeNo,
			@RequestParam("RtnCode") int RtnCode,
			@RequestParam("TradeAmt") int TradeAmt,
			@RequestParam("PaymentDate") String PaymentDate) throws IOException {
		// 交易成功
		if (RtnCode == 1) {
			Optional<OrderBean> order = orderService.getOrderById(MerchantTradeNo);
			if (order.isEmpty()) {
				System.out.println("訂單不存在");
				return "0|err";
			}
			OrderBean orderbean = order.get();
			StatusBean paidStatus = statusRepository.findByDetailedStatus("已付款");
			orderbean.setStatus(paidStatus); // 設置為已付款的狀態
			// 生成發票號碼
			String invoiceNumber = InvoiceNumberGenerator.generateInvoiceNumber();
			orderbean.setInvoiceNumber(invoiceNumber);
			orderbean.setIssuedTime(LocalDateTime.now());
			// 將 PaymentDate 字串轉換為 LocalDateTime
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime paymentDateTime = LocalDateTime.parse(PaymentDate, formatter);
			orderbean.setLastUpdatedTime(paymentDateTime);
			orderService.updateOrder(order.get()); // 更新後保存到資料庫中
			System.out.println("訂單編號: " + MerchantTradeNo + " 交易成功，金額：" + TradeAmt);
			return "1|OK";
		} else {
			System.out.println("交易失敗");
			return "0|err";
		}
	}

	// 2a.發票號碼生成器 (Ex: AB12345678)
	public class InvoiceNumberGenerator {
		public static String generateInvoiceNumber() { // 生成10位數發票號碼
			Random random = new Random();
			// 前兩位-大寫英文字母
			char firstLetter = (char) (random.nextInt(26) + 'A');
			char secondLetter = (char) (random.nextInt(26) + 'A');
			// 後八位-數字亂碼
			StringBuilder digits = new StringBuilder();
			for (int i = 0; i < 8; i++) {
				digits.append(random.nextInt(10));
			}
			return "" + firstLetter + secondLetter + digits;
		}
	}

	// 3.取得付款結果
	@GetMapping("/paymentResult/{customerId}")
	public ResponseEntity<Map<String, Object>> getPaymentResult(@PathVariable Long customerId) {
		OrderBean order = orderRepository.findTopByCustomer_CustomerIDOrderByOrderCreationTimeDesc(customerId);
		if (order == null) {
			return ResponseEntity.notFound().build(); // 如果沒找到訂單，回傳 404
		}
		Map<String, Object> result = new HashMap<>();
		result.put("orderId", order.getOrderId());
		result.put("statusName", order.getStatus().getDetailedStatus());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		result.put("paymentTime", order.getOrderCreationTime().format(formatter));
		result.put("tradeAmt", order.getFinalAmount());
		return ResponseEntity.ok(result); // 回傳訂單結果
	}
}
