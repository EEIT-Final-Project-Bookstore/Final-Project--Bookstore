package bookstore.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookstore.domain.CustomerBean;
import bookstore.jwt.JsonWebTokenUtility;
import bookstore.service.CustomerService;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private JsonWebTokenUtility jsonWebTokenUtility;

    @PostMapping("/api/customers/login")
    public String login(@RequestBody String entity) {
        JSONObject responseJson = new JSONObject();

        // 接收資料
        JSONObject obj = new JSONObject(entity);
        String username = obj.isNull("username") ? null : obj.getString("username");
        String password = obj.isNull("password") ? null : obj.getString("password");

        // 驗證資料
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            responseJson.put("success", false);
            responseJson.put("message", "請輸入帳號/密碼");
            return responseJson.toString();
        }

        // 呼叫 Service 進行登入驗證
        CustomerBean bean = customerService.login(username, password);

        // 根據 Service 執行結果決定要回傳的內容
        if (bean == null) {
            responseJson.put("success", false);
            responseJson.put("message", "登入失敗，帳號或密碼錯誤。");
        } else {
            responseJson.put("success", true);
            responseJson.put("message", "登入成功");

            // 將要放入 JWT Payload 的使用者資訊
            // 注意：這裡可以只放必要的欄位即可，不必全放
            JSONObject userPayload = new JSONObject()
                    .put("username", bean.getUsername()) // 重要：前端需要的 username
                    .put("email", bean.getEmail())
                    .put("customerID", bean.getCustomerID())
                    .put("customerName", bean.getCustomerName());

            // 產生 JWT Token
            String token = jsonWebTokenUtility.createToken(userPayload.toString());
            responseJson.put("token", token);

            // 回傳給前端的 "user" 欄位建議是一個 JSON 物件，內含 username 等欄位
            // 以便前端存入 authStore.user 時能直接使用 user.username
            responseJson.put("user", userPayload);

            // 若前端同時需要分開的 customerName 與 customerID，也可額外放置
            responseJson.put("customerName", bean.getCustomerName());
            responseJson.put("customerID", bean.getCustomerID());
        }
        return responseJson.toString();
    }
}