package seu.qz.qzserver.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import seu.qz.qzserver.entity.MaterialSpecies;
import seu.qz.qzserver.mybatismapper.MaterialSpeciesMapper;

@Component
public class MaterialSpeciesDao {

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    public MaterialSpecies getMaterialById(Integer id){
        SqlSession session = factory.openSession();
        MaterialSpeciesMapper mapper = session.getMapper(MaterialSpeciesMapper.class);
        MaterialSpecies material = null;
        try{
            material = mapper.getMaterialById(id);
        }catch (Exception e){

        }finally {
            session.close();
        }
        return material;
    }

}
