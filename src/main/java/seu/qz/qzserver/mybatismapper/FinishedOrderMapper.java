package seu.qz.qzserver.mybatismapper;

import seu.qz.qzserver.entity.FinishedOrder;
import seu.qz.qzserver.entity.ProvideOrder;

import java.util.List;

public interface FinishedOrderMapper {

    public FinishedOrder getOrderById(Integer id);

    public int updateOrder(FinishedOrder order);

    public int addOrder(FinishedOrder order);

    public int deleteOrderById(Integer id);

    List<FinishedOrder> getOrdersByNickName(String user_nickName);

    List<FinishedOrder> getOrdersBySalerName(String saler_name);

    public int getCounts();

    List<FinishedOrder> getOrdersByUserId(int user_id);
}
