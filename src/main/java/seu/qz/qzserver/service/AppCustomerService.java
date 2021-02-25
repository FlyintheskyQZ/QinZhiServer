package seu.qz.qzserver.service;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import seu.qz.qzserver.dao.AppCustomerDao;
import seu.qz.qzserver.entity.AlreadyExistCustomer;
import seu.qz.qzserver.entity.AppCustomer;
import seu.qz.qzserver.mybatismapper.AppCustomerMapper;

import java.util.List;

@Service
public class AppCustomerService {

    @Autowired
    public AppCustomerDao dao;

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    public AppCustomer getCustomerById(Integer id){
        SqlSession session = factory.openSession();
        AppCustomerMapper mapper = session.getMapper(AppCustomerMapper.class);
        AppCustomer customer = null;
        try{
            customer = dao.getAppCustomerById(id, mapper);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return customer;
    }

    public AppCustomer getCustomerByNickeame(String name){
        SqlSession session = factory.openSession();
        AppCustomerMapper mapper = session.getMapper(AppCustomerMapper.class);
        AppCustomer customer = null;
        try{
            customer = dao.getAppCustomerByNickname(name, mapper);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return customer;
    }

    public AlreadyExistCustomer addCustomer(AppCustomer customer){
        SqlSession session = factory.openSession();
        AppCustomerMapper mapper = session.getMapper(AppCustomerMapper.class);
        AlreadyExistCustomer existCustomer = new AlreadyExistCustomer();
        try{
            if(dao.getAppCustomerByNickname(customer.getUser_nickName(), mapper) != null){
                existCustomer.setExisted(true);
                existCustomer.setReason(new String("nickName is existed!"));
            }else if(dao.getAppCustomerByRegisterName(customer.getUser_registerName(), mapper) != null){
                existCustomer.setExisted(true);
                existCustomer.setReason(new String("registerName is existed!"));
            }else if(dao.getAppCustomerByPhoneNumber(customer.getPhoneNumber(), mapper) != null){
                existCustomer.setExisted(true);
                existCustomer.setReason(new String("phoneNumber is existed!"));
            }else if(dao.getAppCustomerByEmail(customer.getEmail(), mapper) != null){
                existCustomer.setExisted(true);
                existCustomer.setReason(new String("email is existed!"));
            }else{
                existCustomer.setExisted(false);
                dao.addCustomer(customer, mapper);
                existCustomer.setCustomer(dao.getAppCustomerByNickname(customer.getUser_nickName(), mapper));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return existCustomer;
    }

    //由于商家撤销未订购的订单而需要更新的numberForProvideOrders属性-1
    public boolean updateSalerForDeleteProvideOrder(int saler_id) {
        SqlSession session = factory.openSession();
        AppCustomerMapper mapper = session.getMapper(AppCustomerMapper.class);
        try {
            AppCustomer saler = dao.getAppCustomerById(saler_id, mapper);
            if (saler != null){
                int numberForProvideOrders = saler.getNumberForProvideOrders();
                saler.setNumberForProvideOrders(numberForProvideOrders - 1);
                return dao.updateAppCustomer(saler, mapper);
            }else {
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

    public boolean updateAppCustomer(AppCustomer customer) {
        SqlSession session = factory.openSession();
        AppCustomerMapper mapper = session.getMapper(AppCustomerMapper.class);
        try{
            return dao.updateAppCustomer(customer, mapper);
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    public AppCustomer getCustomerByEmail(String email) {
        SqlSession session = factory.openSession();
        AppCustomerMapper mapper = session.getMapper(AppCustomerMapper.class);
        try{
            return dao.getAppCustomerByEmail(email, mapper);
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public AppCustomer getCustomerByPhone(String phoneNumber) {
        SqlSession session = factory.openSession();
        AppCustomerMapper mapper = session.getMapper(AppCustomerMapper.class);
        try{
            return dao.getAppCustomerByPhoneNumber(phoneNumber, mapper);
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public List<AppCustomer> getCustomersByEmail(String email) {
        SqlSession session = factory.openSession();
        AppCustomerMapper mapper = session.getMapper(AppCustomerMapper.class);
        try{
            return dao.getAppCustomersByEmail(email, mapper);
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public List<AppCustomer> getCustomersByPhone(String phoneNumber) {
        SqlSession session = factory.openSession();
        AppCustomerMapper mapper = session.getMapper(AppCustomerMapper.class);
        try{
            return dao.getAppCustomersByPhoneNumber(phoneNumber, mapper);
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
            return null;
        }finally {
            session.close();
        }
    }
}
