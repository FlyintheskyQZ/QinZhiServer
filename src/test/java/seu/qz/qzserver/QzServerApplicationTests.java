package seu.qz.qzserver;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import seu.qz.qzserver.entity.AppCustomer;
import seu.qz.qzserver.mybatismapper.AppCustomerMapper;
import seu.qz.qzserver.mybatismapper.ProvideOrderMapper;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * springBoot单元测试
 * 可以使用spring的自动注入功能
 */
@SpringBootTest
class QzServerApplicationTests {

    @Autowired
    public DataSource dataSource;

    @Autowired
    public ApplicationContext context;

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    @Test
    void connectionTest() throws SQLException {
//        Connection connection = dataSource.getConnection();
//        System.out.print(connection);
//        connection.close();
//        DataSource dataSources = (DataSource) context.getBean("druidDatasource");
//        System.out.print("");
        String original = "￥123";
        System.out.println(original.replace("￥", "") + ":" + original);
    }
    @Test
    void mybatisTest() throws IOException {
//        String resource = "mybatisconfig/mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
        DataSource dataSources = (DataSource) context.getBean("druidDatasource");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        DataSource d = factory.getConfiguration().getEnvironment().getDataSource();
        System.out.println(dataSources == d);
        SqlSession sqlSession = factory.openSession();
        System.out.print(sqlSession);
        sqlSession.close();
    }

    @Test
    void selectObject(){

    }

    @Test
    void deleteObject(){
        String directory = "D:/GraduationDesign/References/pdf";
        File file = new File(directory);
        String[] fileNames = file.list();
        for(int i = 0; i < fileNames.length; i++){
            System.out.println(fileNames[i]);
        }
    }

    @Test
    public void updateCustomer(){
        SqlSession sqlSession = factory.openSession();
        AppCustomerMapper mapper = sqlSession.getMapper(AppCustomerMapper.class);
        AppCustomer customer_original;

        customer_original = mapper.getCustomerById(5);
        System.out.println(customer_original);
//            AppCustomer customer = new AppCustomer(5,"辉辉","123",123,"莉莉","456"
//                    ,Date.valueOf("2020-9-8"),1,2);
//            mapper.updateCustomer(customer);
//            sqlSession.commit();
        AppCustomer customer_changed = mapper.getCustomerById(5);
        System.out.println(customer_changed);
        System.out.println(customer_changed==customer_original);
        sqlSession.close();
        SqlSession sqlSession2 = factory.openSession();
        AppCustomerMapper mapper2 = sqlSession2.getMapper(AppCustomerMapper.class);
//        AppCustomer customer = new AppCustomer(5,"辉辉","123",123,"莉莉","456"
//                    ,Date.valueOf("2020-9-8"),1,2);
//        mapper2.updateCustomer(customer);
//        sqlSession2.commit();
        AppCustomer customer_changed2 = mapper2.getCustomerById(5);
        System.out.println(customer_changed2);
        System.out.println(customer_changed2==customer_changed);
        sqlSession2.close();
    }
}

