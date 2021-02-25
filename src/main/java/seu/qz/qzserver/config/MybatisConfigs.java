package seu.qz.qzserver.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class MybatisConfigs {

    @Bean(name = "mbtSqlSessionFactory")
    public SqlSessionFactory mybatisSqlFactory(@Qualifier("druidDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/mybatisconfig/mybatis-config.xml"));
      //  setConfigLocation和setMapperLocation可任选一个起作用,使用ConfigLocation的话相关mapper配置映射都可以统一在mybatis-config.xml中进行管理
     //   factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
        return factoryBean.getObject();
    }



}
