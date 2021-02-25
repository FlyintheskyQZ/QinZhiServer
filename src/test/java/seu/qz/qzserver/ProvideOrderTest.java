package seu.qz.qzserver;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import seu.qz.qzserver.entity.ProvideOrder;
import seu.qz.qzserver.mybatismapper.FinishedOrderMapper;
import seu.qz.qzserver.mybatismapper.ProvideOrderMapper;
import seu.qz.qzserver.util.DateFormatTransUtils;


import java.sql.Date;
import java.util.Calendar;


@SpringBootTest
public class ProvideOrderTest {

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    Calendar calendar = Calendar.getInstance();

    @Autowired
    public DateFormatTransUtils utils;

    @Test
    public void testSelectOrders(){
        SqlSession sqlSession = factory.openSession();
        ProvideOrderMapper mapper = sqlSession.getMapper(ProvideOrderMapper.class);
        try{
            int num = mapper.getConfirmedCounts();
            System.out.println(num);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateOrders(){
        SqlSession sqlSession = factory.openSession();
        FinishedOrderMapper mapper = sqlSession.getMapper(FinishedOrderMapper.class);
        try{
            int num = mapper.getCounts();
            System.out.println(num);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAddOrders(){

    }

    @Test
    public void testDeleteOrders(){
        SqlSession sqlSession = factory.openSession();
        ProvideOrderMapper mapper = sqlSession.getMapper(ProvideOrderMapper.class);
        try{
            mapper.deleteOrderById(5);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }
}
