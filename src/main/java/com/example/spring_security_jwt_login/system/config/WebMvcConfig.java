package com.example.spring_security_jwt_login.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 1. 서버에 매칭되는 요청을 로그로 확인하기 위한 CommonsRequestLoggingFilter 빈 객체 만들기
     */
    @Bean
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();
        //1. 클라이언트 주소, 세션 id값을 로그에 포함
        commonsRequestLoggingFilter.setIncludeClientInfo(true);
        //2. 요청내용을 포함
        commonsRequestLoggingFilter.setIncludePayload(true);
        //3. 헤더 정보를 포함
        commonsRequestLoggingFilter.setIncludeHeaders(true);
        //4. 쿼리스트링 포함
        commonsRequestLoggingFilter.setIncludeQueryString(true);
        //5. 로그 최대 길이 설정
        commonsRequestLoggingFilter.setMaxPayloadLength(1000);

        return commonsRequestLoggingFilter;
    }

    /**
     * 2. MappingJackson2JsonView 빈 객체 등록
     */
    @Bean("jsonView")
    public MappingJackson2JsonView jsonView(){
        return new MappingJackson2JsonView();
    }

    /**
     * 3. 정적자원 경로 매핑
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/web/**")
                .addResourceLocations("classpath:/static/web");

        registry.addResourceHandler("/m/**")
                .addResourceLocations("classpath:/static/m");
    }

    /**
     * 4. 인터셉터 등록
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){

    }

}
