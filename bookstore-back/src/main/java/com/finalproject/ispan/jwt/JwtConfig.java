//package com.finalproject.ispan.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//// 放開註解=沒有登入就進不去購物車和訂單頁面，*應該設定返回到登入畫面*
//@Configuration
//public class JwtConfig implements WebMvcConfigurer {
//    @Autowired
//    private JsonWebTokenInterceptor jsonWebTokenInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jsonWebTokenInterceptor)
//            .addPathPatterns("api/cart/**");
//            .addPathPatterns("api/order/**");
//    }
//}