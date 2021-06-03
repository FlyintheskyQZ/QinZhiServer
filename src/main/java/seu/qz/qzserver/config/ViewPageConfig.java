package seu.qz.qzserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import seu.qz.qzserver.interceptor.LoginHandlerInterceptor;

@Configuration
public class ViewPageConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("page-login");
        registry.addViewController("/index").setViewName("page-login");
        registry.addViewController("/user_main.html").setViewName("dashboard");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/user_main.html");
    }
}
