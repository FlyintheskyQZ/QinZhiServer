package seu.qz.qzserver.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import seu.qz.qzserver.entity.ExperimentResult;
import seu.qz.qzserver.mybatismapper.ExperimentResultMapper;

@Component
public class ExperimentResultDao {

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    public ExperimentResult getResultById(Integer id){
        SqlSession session = factory.openSession();
        ExperimentResultMapper mapper = session.getMapper(ExperimentResultMapper.class);
        ExperimentResult result = null;
        try{
            result = mapper.getExperimentResultById(id);
        }catch (Exception e){

        }finally {
            session.close();
        }
        return  result;
    }

}
