//package com.finalproject.ispan.util;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeSet;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.finalproject.ispan.domain.OrderBean;
//import com.finalproject.ispan.repository.OrderRepository;
//
//@Component
//public class EcpayFunctions {
//	@Autowired
//	private OrderRepository orderRepository;
////	 public EcpayFunctions(OrderRepository orderRepository) {
////	        this.orderRepository = orderRepository;
////	  }
//	
//	private static final String ACTION_URL = "https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5";
//	private static final String RETURN_URL = "https://c9a7-122-100-70-67.ngrok-free.app/pages/ecpay/return";  //我新生成的ngrok url
////	private static final String RETURN_URL = "https://f9d4-1-160-6-252.ngrok-free.app/pages/ecpay/return";  //本機測試時使用ngrok
//	private static final String MERCHANT_ID = "2000132";
//    private static final String HASH_KEY = "5294y06JbISpM5x9";
//    private static final String HASH_IV = "v77hoKGq4kWxNNIS";
//    
//    public String buildEcpayForm(String body) {
//    	// 从 body 中提取 orderID
//        JSONObject obj = new JSONObject(body);
//        String orderId = obj.getString("orderId"); // 提取 orderID
//    	// 從資料庫查詢訂單資料
//    	OrderBean order = orderRepository.findByOrderId(orderId)
//                .orElseThrow(() -> new RuntimeException("訂單資料不存在，OrderId: " + orderId));
//
//    	Integer finalAmount = order.getFinalAmount(); //後來加上
//        // 使用查詢到的資料生成表單
//        String id = "BooksOrder" + order.getOrderId(); // 訂單ID作為 MerchantTradeNo
//        // 如果希望动态生成商品名称
//        String name = "書籍商品-" + order.getOrderId();
//        String total = finalAmount.toString(); // 訂單總金額 把order.getTotalAmount().toString();換掉
//        String desc = "書店訂單支付"; // 固定描述 // 訂單描述
//        String date = order.getOrderCreationTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")); // 格式化日期
//    	
//    	Map<String, String> parameters = this.createEcpayData(id, name, total, desc, date);
//    	
//        StringBuilder builder = new StringBuilder();
//        builder = builder.append("<form id='payForm' target='_self' action='"+ACTION_URL+"' method='POST'>");
//
//        Set<String> keys = parameters.keySet();
//        for(String key : keys) {
//        	String value = parameters.get(key);
//        	builder = builder.append("<input type='hidden' name='")
//        					.append(key).append("' value='")
//        					.append(value).append("'/>");
//        }
//        builder = builder.append("<script>payForm.submit()</script>");
//        builder = builder.append("</form>");
//    	
//    	return builder.toString();
//    }
//    
//    private Map<String, String> createEcpayData(String id, String name, String total, String desc, String date) {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("MerchantID", MERCHANT_ID);
//        parameters.put("MerchantTradeNo", id);
//        parameters.put("ItemName", name);
//        parameters.put("TotalAmount", total);
//        parameters.put("TradeDesc", desc);
//        parameters.put("MerchantTradeDate", date);
//        parameters.put("PaymentType", "aio");
//        parameters.put("ChoosePayment", "ALL");
//        parameters.put("ReturnURL", RETURN_URL);   // 綠界通知後端
//        parameters.put("ClientBackURL", "http://localhost:8080/callback.html"); // 顧客支付成功後按下"返回商店"按鈕跳轉的頁面
//
//        String checkMacValue = genCheckMacValue(parameters, HASH_KEY, HASH_IV);
//        parameters.put("CheckMacValue", checkMacValue);
//
//        return parameters;
//	}
//    
//	private String genCheckMacValue(Map<String, String> params, String hashKey, String hashIV){
//		// 1. 排序參數名稱（ASCII 碼順序，不分大小寫）
//		Set<String> keySet = params.keySet();
//		TreeSet<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
//		treeSet.addAll(keySet);
//		// 2. 組合參數字串
//		StringBuilder paramStr = new StringBuilder();
//	    for (String key : treeSet) {
//	        if (!"CheckMacValue".equals(key)) { // 排除 CheckMacValue
//	            if (paramStr.length() > 0) {
//	                paramStr.append("&");
//	            }
//	            paramStr.append(key).append("=").append(params.get(key));
//	        }
//	    }
//	    // original舊版:
////		String name[] = treeSet.toArray(new String[treeSet.size()]);
////		String paramStr = "";
////		for(int i = 0; i < name.length; i++){
////			if(!name[i].equals("CheckMacValue")){
////				paramStr += "&" + name[i] + "=" + params.get(name[i]);
////			}
////		}
//	    // 3. 加上 HashKey & HashIV，進行 URL Encode（UTF-8），並轉成小寫
//		String urlEncode = urlEncode("HashKey=" + hashKey + "&" + paramStr + "&HashIV=" + hashIV).toLowerCase();
//		urlEncode = urlEncode.replaceAll("%21", "\\!").replaceAll("%28", "\\(").replaceAll("%29", "\\)");
//		// 4. SHA-256 加密並轉成十六進位
//		return hash(urlEncode.getBytes(), "SHA-256");
//	}
//	private static String urlEncode(String data) {
//		String result = "";
//		try{
//			result = URLEncoder.encode(data, "UTF-8");
//		} catch(UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return result; 
//	}
//	
//	private String hash(byte data[], String mode){
//		MessageDigest md = null;
//		try{
//			if(mode == "MD5"){
//				md = MessageDigest.getInstance("MD5");
//			} else if("SHA-256".equals(mode)){
//				md = MessageDigest.getInstance("SHA-256");
//			}
//		} catch(NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return bytesToHex(md.digest(data));
//	}
//	
//	private String bytesToHex(byte[] bytes) {
//		final char[] hexArray = "0123456789ABCDEF".toCharArray();
//	    char[] hexChars = new char[bytes.length * 2];
//	    for ( int j = 0; j < bytes.length; j++ ) {
//	        int v = bytes[j] & 0xFF;
//	        hexChars[j * 2] = hexArray[v >>> 4];
//	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
//	    }
//	    return new String(hexChars);
//	}
//}
