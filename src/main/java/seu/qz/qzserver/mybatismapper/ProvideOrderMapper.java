package seu.qz.qzserver.mybatismapper;

import seu.qz.qzserver.entity.ProvideOrder;

import java.util.List;

public interface ProvideOrderMapper {

    public ProvideOrder getOrderById(Integer id);

    public int addOrder(ProvideOrder order);

    public int updateOrder(ProvideOrder order);

    public int deleteOrderById(Integer id);

    public List<ProvideOrder> getOrdersByNickName(String user_nickName);

    List<ProvideOrder> getOrdersBySaler(String saler_name);

    public Integer getConfirmedCounts();

    List<ProvideOrder> getAllOriginalOrders();
}
