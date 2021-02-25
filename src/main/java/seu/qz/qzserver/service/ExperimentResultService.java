package seu.qz.qzserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.qz.qzserver.dao.ExperimentResultDao;
import seu.qz.qzserver.entity.ExperimentResult;

@Service
public class ExperimentResultService {

    @Autowired
    public ExperimentResultDao dao;

    public ExperimentResult getResultById(Integer id){

        return dao.getResultById(id);
    }
}
