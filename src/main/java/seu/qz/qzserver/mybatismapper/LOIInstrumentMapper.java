package seu.qz.qzserver.mybatismapper;

import seu.qz.qzserver.entity.LOIInstrument;

import java.util.List;

public interface LOIInstrumentMapper {

    public LOIInstrument getInstrumentById(Integer id);

    public int addInstrument(LOIInstrument instrument);

    public int deleteInstrumentById(Integer id);

    public int updateInstrument(LOIInstrument instrument);

    List<LOIInstrument> getInstrumentsById(int saler_id);

    List<LOIInstrument> getAllInstruments();
}
