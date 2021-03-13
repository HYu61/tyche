package pers.hyu.tyche.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.hyu.tyche.interceptor.AuthTokenInterceptor;
import pers.hyu.tyche.zk.client.ZKCuratorClient;


@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Value("${file-space}")
    private String fileSpace;

    @Bean
    public AuthTokenInterceptor authTokenInterceptor() {
        return new AuthTokenInterceptor();
    }

    @Bean(initMethod = "init")
    public ZKCuratorClient zkCuratorClient(){return new ZKCuratorClient();}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + fileSpace + "/")
                .addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authTokenInterceptor()).
//                addPathPatterns("/bgms")
//                .addPathPatterns("/sessions/*")
//                .addPathPatterns("/users/**")
//                .excludePathPatterns("/users/password")
//                .excludePathPatterns("/users")
//                .excludePathPatterns("/users/*/profile/*")
//                .excludePathPatterns("/users/*/videos/**")
////        .addPathPatterns("/videos/vip/*")
////        .addPathPatterns("/videos/*/like_count")
//        .addPathPatterns("/videos/*/*");
        String[] excludes = {"/sessions", "/users", "/users/password",  "/users/*/profile/**","/search_content", "/bgms/*"};
        String[] swaggers = {"/swagger-ui.html", "/swagger-resources/**", "/error", "/webjars/**"};
        registry.addInterceptor(authTokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludes)
                .excludePathPatterns(swaggers);
    }
}
