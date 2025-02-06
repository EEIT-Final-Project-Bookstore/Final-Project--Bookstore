package tw.com.ispan.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tw.com.ispan.domain.OrdersBean;
import tw.com.ispan.repository.OrdersRepository;

@Component
public class EcpayFunctions {
	

	
	 //機器人
	 private final OrdersRepository ordersRepository;
	 //機器人
	 @Autowired
	 public EcpayFunctions(OrdersRepository ordersRepository) {
	        this.ordersRepository = ordersRepository;
	  }
	
	private static final String ACTION_URL = "https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5";
	private static final String RETURN_URL = "https://f9d4-1-160-6-252.ngrok-free.app/pages/ecpay/return";
    private static final String MERCHANT_ID = "2000132";
    private static final String HASH_KEY = "5294y06JbISpM5x9";
    private static final String HASH_IV = "v77hoKGq4kWxNNIS";
    
    
    public String buildEcpayForm(String body) {
    	// 从 body 中提取 orderID
        JSONObject obj = new JSONObject(body);
        Integer orderID = obj.getInt("orderID"); // 提取 orderID
    	// 從資料庫查詢訂單資料
    	OrdersBean order = ordersRepository.findById(orderID)
                .orElseThrow(() -> new RuntimeException("訂單資料不存在，OrderID: " + orderID));

        // 使用查詢到的資料生成表單
        String id = "BooksOrder" + order.getOrderID(); // 訂單ID作為 MerchantTradeNo
        // 如果希望动态生成商品名称
        String name = "書籍商品-" + order.getOrderID();
        String total = order.getTotalAmount().toString(); // 訂單總金額
        String desc = "書店訂單支付"; // 固定描述 // 訂單描述
        String date = order.getOrderCreationTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")); // 格式化日期
    	
    	Map<String, String> parameters = this.createEcpayData(id, name, total, desc, date);
    	
        StringBuilder builder = new StringBuilder();
        builder = builder.append("<form id='payForm' target='_self' action='"+ACTION_URL+"' method='POST'>");

        Set<String> keys = parameters.keySet();
        for(String key : keys) {
        	String value = parameters.get(key);
        	builder = builder.append("<input type='hidden' name='")
        					.append(key).append("' value='")
        					.append(value).append("'/>");
        }
        builder = builder.append("<script>payForm.submit()</script>");
        builder = builder.append("</form>");
    	
    	return builder.toString();
    }
    
    private Map<String, String> createEcpayData(String id, String name, String total, String desc, String date) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("MerchantID", MERCHANT_ID);
        parameters.put("MerchantTradeNo", id);
        parameters.put("ItemName", name);
        parameters.put("TotalAmount", total);
        parameters.put("TradeDesc", desc);
        parameters.put("MerchantTradeDate", date);
        parameters.put("PaymentType", "aio");
        parameters.put("ChoosePayment", "ALL");
        parameters.put("ReturnURL", RETURN_URL);
        parameters.put("ClientBackURL", "http://localhost:8080/callback.html"); // 支付成功後用戶跳轉的頁面

        String checkMacValue = genCheckMacValue(parameters, HASH_KEY, HASH_IV);
        parameters.put("CheckMacValue", checkMacValue);

        return parameters;
	}
    
	private String genCheckMacValue(Map<String, String> params, String hashKey, String hashIV){
		Set<String> keySet = params.keySet();
		TreeSet<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		treeSet.addAll(keySet);
		String name[] = treeSet.toArray(new String[treeSet.size()]);
		String paramStr = "";
		for(int i = 0; i < name.length; i++){
			if(!name[i].equals("CheckMacValue")){
				paramStr += "&" + name[i] + "=" + params.get(name[i]);
			}
		}
		
		String urlEncode = urlEncode("Hashkey=" + hashKey + paramStr + "&HashIV=" + hashIV).toLowerCase();
		urlEncode = urlEncode.replaceAll("%21", "\\!").replaceAll("%28", "\\(").replaceAll("%29", "\\)");
		
		return hash(urlEncode.getBytes(), "SHA-256");
	}
	private static String urlEncode(String data) {
		String result = "";
		try{
			result = URLEncoder.encode(data, "UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result; 
	}
	
	private String hash(byte data[], String mode){
		MessageDigest md = null;
		try{
			if(mode == "MD5"){
				md = MessageDigest.getInstance("MD5");
			} else if(mode == "SHA-256"){
				md = MessageDigest.getInstance("SHA-256");
			}
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytesToHex(md.digest(data));
	}
	
	private String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
}
