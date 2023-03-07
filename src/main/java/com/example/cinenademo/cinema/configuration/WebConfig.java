package com.example.cinenademo.cinema.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    //win
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///" + uploadPath + "/");


        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        System.out.println("file:///" + uploadPath + "/");
    }

    //    lun
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**")
//                .addResourceLocations("file://" + uploadPath + "/img/");
////                .addResourceLocations("file://" + uploadPath + "/");
//        registry
//                .addResourceHandler("/**")
//                .addResourceLocations("classpath:/static/");
//        System.out.println("file:" + uploadPath + "/img/");
//
//    }
//    win
//    file:///C:/Temp/whatever/m/
//    lin
//    file://home/Temp/whatever/m/


}
