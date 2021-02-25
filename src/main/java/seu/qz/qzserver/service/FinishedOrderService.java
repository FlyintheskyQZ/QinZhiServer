package seu.qz.qzserver.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import seu.qz.qzserver.dao.FinishedOrderDao;
import seu.qz.qzserver.entity.*;
import seu.qz.qzserver.mybatismapper.FinishedOrderMapper;
import seu.qz.qzserver.mybatismapper.ProvideOrderMapper;
import seu.qz.qzserver.util.OrderComparatorUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinishedOrderService {

    @Autowired
    public FinishedOrderDao dao;

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    @Autowired
    public LOIInstrumentService instrumentService;

    public FinishedOrder getOrderById(Integer id){
        SqlSession session = factory.openSession();
        FinishedOrderMapper mapper = session.getMapper(FinishedOrderMapper.class);
        FinishedOrder order = null;
        try{
            order = dao.getOrderById(id, mapper);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return order;
    }

    public List<BriefOrderItem> getBriefOrdersByUsernameInOrder(String user_nickName) {
        SqlSession session = factory.openSession();
        FinishedOrderMapper mapper = session.getMapper(FinishedOrderMapper.class);
        List<FinishedOrder> list = null;
        List<BriefOrderItem> briefItems = null;
        try{
            list = dao.getOrdersByNickName(user_nickName, mapper);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        if(list == null || list.size() == 0){
            return null;
        }else{
            if(list.size() > 1){
                OrderComparatorUtils.adjustOrderFromFinishedForUser(list);
            }
            briefItems = new ArrayList<>();
            List<LOIInstrument> instruments = instrumentService.getAllInstruments();
            for(int i = 0; i < list.size(); i++){
                FinishedOrder order = list.get(i);
                LOIInstrument instrument = instruments.get(order.getDevice_id() - 1);
                String location = instrument.getFactory_longitude() + ":" + instrument.getFactory_latitude();
                briefItems.add(new BriefOrderItem(order, location));
            }
            return briefItems;
        }
    }

    public List<BriefOrderItem> getBriefOrdersBySalernameInOrder(String saler_name) {
        SqlSession session = factory.openSession();
        FinishedOrderMapper mapper = session.getMapper(FinishedOrderMapper.class);
        List<FinishedOrder> list = null;
        List<BriefOrderItem> briefItems = null;
        try{
            list = dao.getOrdersBySalerName(saler_name, mapper);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        if(list == null || list.size() == 0){
            return null;
        }else{
            List<LOIInstrument> instruments = instrumentService.getAllInstruments();
            briefItems = new ArrayList<>();
            if(list.size() > 1){
                OrderComparatorUtils.adjustOrderFromFinishedForSaler(list);
            }
            for(int i = 0; i < list.size(); i++){
                FinishedOrder order = list.get(i);
                LOIInstrument instrument = instruments.get(order.getDevice_id() - 1);
                String location = instrument.getFactory_longitude() + ":" + instrument.getFactory_latitude();
                briefItems.add(new BriefOrderItem(order, location));
            }
            return briefItems;
        }
    }

    public int getCountsForFinishedOrders() {
        SqlSession session = factory.openSession();
        FinishedOrderMapper mapper = session.getMapper(FinishedOrderMapper.class);
        try {
            return dao.getOrdersCounts(mapper);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }finally {
            session.close();
        }
    }

    public List<BriefReportItem> getReportItemsByUserId(int userid) {
        SqlSession session = factory.openSession();
        FinishedOrderMapper mapper = session.getMapper(FinishedOrderMapper.class);
        List<FinishedOrder> list = null;
        List<BriefReportItem> briefItems = null;
        try{
            list = dao.getOrdersByUserId(userid, mapper);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        if(list == null || list.size() == 0){
            return new ArrayList<>();
        }else{
            briefItems = new ArrayList<>();
            if(list.size() > 1){
                OrderComparatorUtils.adjustOrderFromFinishedForUser(list);
            }
            for(int i = 0; i < list.size(); i++){
                FinishedOrder order = list.get(i);
                briefItems.add(new BriefReportItem(order));
            }
            return briefItems;
        }
    }
}
