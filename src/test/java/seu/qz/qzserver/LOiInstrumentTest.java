package seu.qz.qzserver;

import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import seu.qz.qzserver.dao.LOIInstrumentDao;
import seu.qz.qzserver.entity.LOIInstrument;
import seu.qz.qzserver.mybatismapper.LOIInstrumentMapper;

import java.util.List;

@SpringBootTest
public class LOiInstrumentTest {

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    @Autowired
    public LOIInstrumentDao dao;

    @Test
    public void testGetInstrument(){
        SqlSession sqlSession = factory.openSession();
        LOIInstrumentMapper mapper = sqlSession.getMapper(LOIInstrumentMapper.class);
        try{
            List<LOIInstrument> instruments = mapper.getAllInstruments();
            for(int i = 0; i < instruments.size(); i++){
                System.out.println(instruments.get(i).getDevice_id());
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateInstrument(){
        LOIInstrument instrument = new LOIInstrument(null, "111", "222", "333", "444", "555", "666", null, null, "777", "888", 1, 2);
        SqlSession sqlSession = factory.openSession();
        LOIInstrumentMapper mapper = sqlSession.getMapper(LOIInstrumentMapper.class);
        try{
           dao.addLOIInstrument(instrument, mapper);
           System.out.println(instrument.getDevice_id());
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAddInstrument(){

    }

    @Test
    public void testDeleteInstrument(){
        SqlSession sqlSession = factory.openSession();
        LOIInstrumentMapper mapper = sqlSession.getMapper(LOIInstrumentMapper.class);
        try{
            mapper.deleteInstrumentById(2);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }
}
