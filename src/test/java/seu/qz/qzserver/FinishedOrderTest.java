package seu.qz.qzserver;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import seu.qz.qzserver.entity.FinishedOrder;
import seu.qz.qzserver.mybatismapper.FinishedOrderMapper;

import java.sql.Date;

@SpringBootTest
public class FinishedOrderTest {

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    @Test
    public void testSelectOrders(){
        SqlSession sqlSession = factory.openSession();
        FinishedOrderMapper mapper = sqlSession.getMapper(FinishedOrderMapper.class);
        try{
            FinishedOrder order = mapper.getOrderById(1);
            System.out.println(order);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateOrders(){

    }

    @Test
    public void testAddOrders(){

    }

    @Test
    public void testDeleteOrders(){
        SqlSession sqlSession = factory.openSession();
        FinishedOrderMapper mapper = sqlSession.getMapper(FinishedOrderMapper.class);
        try{
            mapper.deleteOrderById(2);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }


}
