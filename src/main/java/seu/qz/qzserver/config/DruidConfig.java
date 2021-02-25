package seu.qz.qzserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.util.*;


@Configuration
public class DruidConfig {

    @Value("${filter.exclusion}")
    public String filterExclusion;

    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "druidDatasource")
    public DataSource druidDatasource(){
        return new DruidDataSource();
    }

    //配置一个web监控的监听器
    @Bean
    public FilterRegistrationBean webStateFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("exclusions", filterExclusion);
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }

    //配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean();
        StatViewServlet statViewServlet = new StatViewServlet();
        String urlMapping = new String("/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "root");
        initParams.put("loginPassword", "717717");
        initParams.put("allow","");//默认所有允许访问

        bean.setInitParameters(initParams);
        bean.setServlet(statViewServlet);
        bean.addUrlMappings(urlMapping);
        return  bean;
    }
}
