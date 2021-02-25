package seu.qz.qzserver;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import seu.qz.qzserver.entity.MaterialSpecies;
import seu.qz.qzserver.entity.ProvideOrder;
import seu.qz.qzserver.mybatismapper.MaterialSpeciesMapper;
import seu.qz.qzserver.mybatismapper.ProvideOrderMapper;

import java.sql.Date;
import java.util.Calendar;

@SpringBootTest
public class MaterialSpeciesTest {

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    Calendar calendar = Calendar.getInstance();

    @Test
    public void testSelectMaterials(){
        SqlSession sqlSession = factory.openSession();
        MaterialSpeciesMapper mapper = sqlSession.getMapper(MaterialSpeciesMapper.class);
        try{
            MaterialSpecies material = mapper.getMaterialById(1);
            System.out.println(material);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateMaterials(){
        SqlSession sqlSession = factory.openSession();
        MaterialSpeciesMapper mapper = sqlSession.getMapper(MaterialSpeciesMapper.class);
        try{
            MaterialSpecies material = new MaterialSpecies(2,"铝材", 2, 3);
            mapper.updateMaterial(material);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAddMaterials(){
        SqlSession sqlSession = factory.openSession();
        MaterialSpeciesMapper mapper = sqlSession.getMapper(MaterialSpeciesMapper.class);
        try{
            MaterialSpecies material = new MaterialSpecies(2,"钢材", null, 3);
            if(mapper.addMaterial(material) == 1){
                System.out.println(11);
            }else {
                System.out.println(22);
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
        System.out.println("2");
    }

    @Test
    public void testDeleteMaterials(){
        SqlSession sqlSession = factory.openSession();
        MaterialSpeciesMapper mapper = sqlSession.getMapper(MaterialSpeciesMapper.class);
        try{
            System.out.println(mapper.deleteMaterialById(4));
        }catch (Exception e){
            System.out.println(e);
        }finally {
            sqlSession.close();
        }
    }
}
