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
public class FinishedOrderDao {


    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    public FinishedOrder getOrderById(Integer id, FinishedOrderMapper mapper){

        return mapper.getOrderById(id);
    }

    public List<FinishedOrder> getOrdersByNickName(String user_nickName, FinishedOrderMapper mapper) {
        return mapper.getOrdersByNickName(user_nickName);
    }

    public List<FinishedOrder> getOrdersBySalerName(String saler_name, FinishedOrderMapper mapper) {
        return mapper.getOrdersBySalerName(saler_name);
    }

    public int getOrdersCounts(FinishedOrderMapper mapper) {
        return mapper.getCounts();
    }

    public List<FinishedOrder> getOrdersByUserId(int userid, FinishedOrderMapper mapper) {
        return mapper.getOrdersByUserId(userid);
    }
}
