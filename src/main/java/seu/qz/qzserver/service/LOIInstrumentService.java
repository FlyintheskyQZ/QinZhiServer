package seu.qz.qzserver.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import seu.qz.qzserver.dao.LOIInstrumentDao;
import seu.qz.qzserver.entity.AppCustomer;
import seu.qz.qzserver.entity.LOIInstrument;
import seu.qz.qzserver.mybatismapper.AppCustomerMapper;
import seu.qz.qzserver.mybatismapper.LOIInstrumentMapper;

import java.util.List;

@Service
public class LOIInstrumentService {

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    @Autowired
    public LOIInstrumentDao dao;

    @Autowired
    public AppCustomerService appCustomerService;

    public LOIInstrument getInstrumentById(Integer id){
        SqlSession session = factory.openSession();
        LOIInstrumentMapper mapper = session.getMapper(LOIInstrumentMapper.class);
        try {
            return dao.getInstrumentById(id, mapper);
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public boolean updateLOIInstrumentsForDeleteProvideOrder(int instrument_id, int provideorder_id) {
        SqlSession session = factory.openSession();
        LOIInstrumentMapper mapper = session.getMapper(LOIInstrumentMapper.class);
        System.out.println("new_orders is here");
        try {
            LOIInstrument instrument = dao.getInstrumentById(instrument_id, mapper);
            if(instrument != null){
                String p_orders_string = instrument.getP_orders_string();
                String[] orders = p_orders_string.split(";");
                StringBuilder builder = new StringBuilder();
                for(int i = 0; i < orders.length; i++){
                    if(!orders[i].equals(String.valueOf(provideorder_id))){
                        builder.append(orders[i] + ";");
                    }
                }
                //考虑只有一个provideorder，此时删除后builder为空
                String new_orders = null;
                if(builder.length() != 0){
                    new_orders = builder.toString();
                    new_orders = new_orders.substring(0, new_orders.length() - 1);
                }else {
                    new_orders = "";
                }
                System.out.println("new_orders is :" + new_orders);
                instrument.setP_orders_string(new_orders);
                return dao.updateLOIInstrument(instrument, mapper);
            }else {
                System.out.println("new_orders is null");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    public List<LOIInstrument> getInstrumentsById(int saler_id) {
        SqlSession session = factory.openSession();
        LOIInstrumentMapper mapper = session.getMapper(LOIInstrumentMapper.class);
        List<LOIInstrument> loiInstruments = null;
        try {
            loiInstruments = dao.getInstrumentsById(saler_id, mapper);
            return loiInstruments;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public boolean updateInstrument(LOIInstrument instrument) {
        SqlSession session = factory.openSession();
        LOIInstrumentMapper mapper = session.getMapper(LOIInstrumentMapper.class);
        try {
            return dao.updateLOIInstrument(instrument, mapper);
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    public List<LOIInstrument> getAllInstruments() {
        SqlSession session = factory.openSession();
        LOIInstrumentMapper mapper = session.getMapper(LOIInstrumentMapper.class);
        try {
            return dao.getAllInstruments(mapper);
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public boolean addNewInstrument(LOIInstrument instrument) {
        SqlSession session = factory.openSession();
        LOIInstrumentMapper mapper = session.getMapper(LOIInstrumentMapper.class);
        try {
            if(dao.addLOIInstrument(instrument, mapper)){
                AppCustomer customer = appCustomerService.getCustomerById(instrument.getRelated_user_id());
                String related_device = customer.getRelated_device_id();
                String device_id = instrument.getDevice_id().toString();
                if(related_device == null || related_device.isEmpty()){
                    customer.setRelated_device_id(device_id);
                }else {
                    customer.setRelated_device_id(related_device + ";" + device_id);
                }
                if(appCustomerService.updateAppCustomer(customer)){
                    return true;
                }
                session.rollback();
                return false;
            }
            session.rollback();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return false;
        }finally {
            session.close();
        }
    }
}
