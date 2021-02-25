package seu.qz.qzserver.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import seu.qz.qzserver.entity.FinishedOrder;
import seu.qz.qzserver.entity.ProvideOrder;
import seu.qz.qzserver.mybatismapper.FinishedOrderMapper;
import seu.qz.qzserver.mybatismapper.ProvideOrderMapper;

import java.util.List;

@Component
public class ProvideOrderDao {

    public ProvideOrder getOrderById(Integer id, ProvideOrderMapper mapper){
        return  mapper.getOrderById(id);
    }

    public List<ProvideOrder> getOrdersByNickName(String user_nickName, ProvideOrderMapper mapper){
        return mapper.getOrdersByNickName(user_nickName);
    }

    public List<ProvideOrder> getOrdersBySalerName(String saler_name, ProvideOrderMapper mapper) {
        return mapper.getOrdersBySaler(saler_name);
    }

    public boolean deleteProvideOrderById(int order_id, ProvideOrderMapper mapper) {
        return mapper.deleteOrderById(order_id) > 0 ? true : false;
    }

    public boolean updateOrder(ProvideOrder order, ProvideOrderMapper mapper) {
        return mapper.updateOrder(order) > 0 ? true : false;
    }

    public int getConfirmedCounts(ProvideOrderMapper mapper) {
        return mapper.getConfirmedCounts();
    }

    public List<ProvideOrder> getAllOriginalOrders(ProvideOrderMapper mapper) {
        return mapper.getAllOriginalOrders();
    }

    public boolean addProvideOrder(ProvideOrder order, ProvideOrderMapper mapper) {
        return mapper.addOrder(order) > 0;
    }
}
