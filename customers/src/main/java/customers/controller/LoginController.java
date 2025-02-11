package customers.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import customers.domain.AdminBean;
import customers.domain.CustomerBean;
import customers.jwt.JsonWebTokenUtility;
import customers.service.AdminService;
import customers.service.CustomerService;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

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

    @PostMapping("/api/admins/login")
    public String adminLogin(@RequestBody String entity) {
        JSONObject responseJson = new JSONObject();

        // 1. 接收 JSON 資料
        JSONObject obj = new JSONObject(entity);
        String adminAccount = obj.optString("username", null);
        String passwordStr = obj.optString("password", null);

        // 2. 簡單檢查是否為空
        if (adminAccount == null || adminAccount.isEmpty() ||
                passwordStr == null || passwordStr.isEmpty()) {
            responseJson.put("success", false);
            responseJson.put("message", "請輸入帳號/密碼");
            return responseJson.toString();
        }

        // 3. 呼叫 Service：嘗試登入
        // - 假設 Service 端的 login(...) 回傳 null 代表失敗
        AdminBean adminBean = adminService.adminLogin(adminAccount, passwordStr);

        if (adminBean == null) {
            // 登入失敗
            responseJson.put("success", false);
            responseJson.put("message", "登入失敗，帳號或密碼錯誤。");
        } else {
            // 登入成功
            responseJson.put("success", true);
            responseJson.put("message", "登入成功");

            // 可以將部分管理員資料放入 JWT Payload
            JSONObject payload = new JSONObject();
            payload.put("adminId", adminBean.getAdminId());
            payload.put("adminAccount", adminBean.getAdminAccount());
            payload.put("email", adminBean.getEmail());
            if (adminBean.getRank() != null) {
                payload.put("rankId", adminBean.getRank().getRankID());
            }
            // 產生 Token
            String token = jsonWebTokenUtility.createToken(payload.toString());

            // 前端需要用到的資訊，也可以在最外層 responseJson 中一併回傳
            responseJson.put("token", token);

            // 回傳一個 "user" 物件，方便前端 Pinia 直接存
            responseJson.put("user", payload);

            // 若前端想個別取得 adminId, adminAccount, email，也可以另外放
            responseJson.put("adminId", adminBean.getAdminId());
            responseJson.put("adminAccount", adminBean.getAdminAccount());
        }

        return responseJson.toString();
    }
}
