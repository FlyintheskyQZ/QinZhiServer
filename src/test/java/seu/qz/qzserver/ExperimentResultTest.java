package seu.qz.qzserver;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import seu.qz.qzserver.entity.ExperimentResult;
import seu.qz.qzserver.entity.FinishedOrder;
import seu.qz.qzserver.mybatismapper.ExperimentResultMapper;
import seu.qz.qzserver.mybatismapper.FinishedOrderMapper;

import java.sql.Date;

@SpringBootTest
public class ExperimentResultTest {


    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    @Test
    public void testSelectResults(){
        SqlSession sqlSession = factory.openSession();
        ExperimentResultMapper mapper = sqlSession.getMapper(ExperimentResultMapper.class);
        try{
            ExperimentResult result = mapper.getExperimentResultById(1);
            System.out.println(result);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateOrders(){
        SqlSession sqlSession = factory.openSession();
        ExperimentResultMapper mapper = sqlSession.getMapper(ExperimentResultMapper.class);
        try{
            ExperimentResult result = new ExperimentResult(7, 1, 2, 3, 3, 2, null, null, null, null,"18.1 19.3 21.3", "12 50 180", "5 23 56", "X O X X O O", -23.1f, 0.2f, 0.35f,true, 21.3f);
            mapper.updateExperimentResult(result);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAddOrders(){
        SqlSession sqlSession = factory.openSession();
        ExperimentResultMapper mapper = sqlSession.getMapper(ExperimentResultMapper.class);
        try{
            ExperimentResult result = new ExperimentResult(null, 1, 2, 3, 3, 2, null, null, null, null,"18.1 19.3 21.3", "12 50 180", "5 23 56", "X O X X O O", -23.1f, 0.2f, 0.35f,true, 21.3f);
            mapper.addExperimentResult(result);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteOrders(){
        SqlSession sqlSession = factory.openSession();
        ExperimentResultMapper mapper = sqlSession.getMapper(ExperimentResultMapper.class);
        try{
            mapper.deleteExperimentResultById(7);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }
}
