package seu.qz.qzserver.dao;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import seu.qz.qzserver.entity.LOIInstrument;
import seu.qz.qzserver.mybatismapper.LOIInstrumentMapper;

import java.util.List;

@Component
public class LOIInstrumentDao {




    public LOIInstrument getInstrumentById(Integer id, LOIInstrumentMapper mapper){
        LOIInstrument instrument = mapper.getInstrumentById(id);
        return instrument;
    }

    public boolean updateLOIInstrument(LOIInstrument instrument, LOIInstrumentMapper mapper) {
        return mapper.updateInstrument(instrument) > 0 ? true : false;
    }

    public List<LOIInstrument> getInstrumentsById(int saler_id, LOIInstrumentMapper mapper) {
        return mapper.getInstrumentsById(saler_id);
    }

    public List<LOIInstrument> getAllInstruments(LOIInstrumentMapper mapper) {
        return mapper.getAllInstruments();
    }

    public boolean addLOIInstrument(LOIInstrument instrument, LOIInstrumentMapper mapper) {
        return mapper.addInstrument(instrument) > 0;
    }
}
