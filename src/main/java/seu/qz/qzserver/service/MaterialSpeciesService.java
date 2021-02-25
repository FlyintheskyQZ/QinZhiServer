package seu.qz.qzserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.qz.qzserver.dao.MaterialSpeciesDao;
import seu.qz.qzserver.entity.MaterialSpecies;

@Service
public class MaterialSpeciesService {

    @Autowired
    public MaterialSpeciesDao dao;

    public MaterialSpecies getMaterialById(Integer id){

        return dao.getMaterialById(id);
    }
}
