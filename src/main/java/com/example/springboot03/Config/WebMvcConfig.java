//package com.example.springboot03.Config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//    //解决跨域问题
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .allowedMethods("POST","GET","PUT","DELETE")
//                .allowedOrigins("*")
//                .exposedHeaders("*");
//    }
//
//}
