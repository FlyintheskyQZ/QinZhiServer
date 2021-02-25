package seu.qz.qzserver.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import seu.qz.qzserver.dao.ProvideOrderDao;
import seu.qz.qzserver.entity.*;
import seu.qz.qzserver.mybatismapper.FinishedOrderMapper;
import seu.qz.qzserver.mybatismapper.ProvideOrderMapper;
import seu.qz.qzserver.util.OrderComparatorUtils;
import seu.qz.qzserver.util.StringAdjustUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvideOrderService {

    @Autowired
    public ProvideOrderDao dao;

    @Autowired
    public AppCustomerService appCustomerService;

    @Autowired
    public LOIInstrumentService instrumentService;

    @Autowired
    public FinishedOrderService finishedOrderService;

    @Qualifier("mbtSqlSessionFactory")
    @Autowired
    public SqlSessionFactory factory;

    public ProvideOrder getOrderById(Integer id){
        SqlSession session = factory.openSession();
        ProvideOrderMapper mapper = session.getMapper(ProvideOrderMapper.class);
        ProvideOrder order = null;
        try {
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
        ProvideOrderMapper mapper = session.getMapper(ProvideOrderMapper.class);
        List<ProvideOrder> list = null;
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
                OrderComparatorUtils.adjustOrderFromProvideForUser(list);
            }
            List<LOIInstrument> instruments = instrumentService.getAllInstruments();
            briefItems = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                ProvideOrder order = list.get(i);
                LOIInstrument instrument = instruments.get(order.getDevice_id() - 1);
                String location = instrument.getFactory_longitude() + ":" + instrument.getFactory_latitude();
                briefItems.add(new BriefOrderItem(order,location));
            }
            return briefItems;
        }
    }

    public List<BriefOrderItem> getBriefOrdersBySalerNameInOrder(String saler_name) {
        SqlSession session = factory.openSession();
        ProvideOrderMapper mapper = session.getMapper(ProvideOrderMapper.class);
        List<ProvideOrder> list = null;
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
                OrderComparatorUtils.adjustOrderFromProvideForSaler(list);
            }
            for(int i = 0; i < list.size(); i++){
                ProvideOrder order = list.get(i);
                LOIInstrument instrument = instruments.get(order.getDevice_id() - 1);
                String location = instrument.getFactory_longitude() + ":" + instrument.getFactory_latitude();
                briefItems.add(new BriefOrderItem(order, location));
            }
            return briefItems;
        }
    }

    //由于商家撤销未订购的订单而删除该订单更新商家属性numberForProvideOrders、仪器属性p_orders_string
    public boolean deleteProvideOrderById(int order_id) {
        SqlSession session = factory.openSession();
        ProvideOrderMapper mapper = session.getMapper(ProvideOrderMapper.class);
        try{
            ProvideOrder provideOrder = dao.getOrderById(order_id, mapper);
            if(provideOrder != null && provideOrder.getOrder_confirmed() == 0){
                if(dao.deleteProvideOrderById(order_id, mapper)){
                    int saler_id = provideOrder.getSaler_id();
                    int instrument_id = provideOrder.getDevice_id();
                    if(appCustomerService.updateSalerForDeleteProvideOrder(saler_id) &&
                    instrumentService.updateLOIInstrumentsForDeleteProvideOrder(instrument_id, order_id)){
                        return true;
                    }
                }
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

    public boolean updateProvideOrder(ProvideOrder order) {
        SqlSession session = factory.openSession();
        ProvideOrderMapper mapper = session.getMapper(ProvideOrderMapper.class);

        try{
            ProvideOrder old_order = dao.getOrderById(order.getOrder_id(), mapper);
            switch (order.getOrder_confirmed()){
                //订单状态0-0，saler修改订单：若订单选择的仪器发生改变，则仪器LOIInstrument的p_orders_string发生改变
                case 0:
                    if(old_order.getOrder_confirmed() == 0){
                        //设置该变量，当order的仪器发生改变时，则仪器更新也完成才能算成功；若仪器未发生改变，则直接判断更新order的结果
                        boolean updateSuccess = true;
                        if(order.getDevice_id().intValue() != old_order.getDevice_id()){
                            updateSuccess = false;
                            //此处表明订单的机器改变了，需要对两个机器的属性P_orders_string进行修改，后再对ProvideOrder进行更新
                            LOIInstrument old_instrument = instrumentService.getInstrumentById(old_order.getDevice_id());
                            LOIInstrument new_instrument = instrumentService.getInstrumentById(order.getDevice_id());
                            String old_provideOrders_string = StringAdjustUtils.interceptOrderStrings(old_instrument
                                    .getP_orders_string(), String.valueOf(order.getOrder_id()));
                            String new_provideOrders_string = StringAdjustUtils.appendOrderStrings(new_instrument
                                    .getP_orders_string(),String.valueOf(order.getOrder_id()));
                            old_instrument.setP_orders_string(old_provideOrders_string);
                            new_instrument.setP_orders_string(new_provideOrders_string);
                            if(instrumentService.updateInstrument(old_instrument) && instrumentService.updateInstrument(new_instrument)){
                                updateSuccess = true;
                            }
                        }
                        if(updateSuccess && dao.updateOrder(order, mapper)){
                            return true;
                        }
                    }
                    break;

                case 1:
                    //订单状态0-1，user订购订单：客户AppCustomer的numberForProvideOrders属性+1
                    if(old_order.getOrder_confirmed() == 0){
                        AppCustomer customer = appCustomerService.getCustomerById(order.getUser_id());
                        customer.setNumberForProvideOrders(customer.getNumberForProvideOrders() + 1);
                        if(appCustomerService.updateAppCustomer(customer)){
                            if(dao.updateOrder(order, mapper)){
                                //各种条件均满足，返回true，更新成功
                                return true;
                            }
                        }
                        //订单状态1-1，有两种情况：二者区别在于，客户修改不会涉及AppCustomer和LOIInstrument库的改变，而商家修改，
                        // 可能会涉及LOIInstrument的改变，只需判断订单的devic_id是否发生变化，再把相应的修改结果纳入最终更新的结果里就可以了
                        //客户修改订单
                        //商家修改订单
                    }else if(old_order.getOrder_confirmed() == 1){
                        //设置该变量，当order的仪器发生改变时，则仪器更新也完成才能算成功；若仪器未发生改变，则直接判断更新order的结果
                        boolean updateSuccess = true;
                        if(order.getDevice_id().intValue() != old_order.getDevice_id()){
                            updateSuccess = false;
                            //此处表明订单的机器改变了，需要对两个机器的属性P_orders_string进行修改，后再对ProvideOrder进行更新
                            LOIInstrument old_instrument = instrumentService.getInstrumentById(old_order.getDevice_id());
                            LOIInstrument new_instrument = instrumentService.getInstrumentById(order.getDevice_id());
                            String old_provideOrders_string = StringAdjustUtils.interceptOrderStrings(old_instrument
                                    .getP_orders_string(), String.valueOf(order.getOrder_id()));
                            String new_provideOrders_string = StringAdjustUtils.appendOrderStrings(new_instrument
                                    .getP_orders_string(),String.valueOf(order.getOrder_id()));
                            old_instrument.setP_orders_string(old_provideOrders_string);
                            new_instrument.setP_orders_string(new_provideOrders_string);
                            if(instrumentService.updateInstrument(old_instrument) && instrumentService.updateInstrument(new_instrument)){
                                updateSuccess = true;
                            }
                        }
                        if(updateSuccess && dao.updateOrder(order, mapper)){
                            return true;
                        }
                        //客户按要求修改了，订单状态2-1
                    }else if(old_order.getOrder_confirmed() == 2){
                        if(dao.updateOrder(order, mapper)){
                            return true;
                        }
                    }
                    break;
                case 2:
                    //订单状态1-2，商家审批未通过，需要客户修改，此时只需要更新订单即可
                    if(old_order.getOrder_confirmed() == 1){
                        if(dao.updateOrder(order, mapper)){
                            return true;
                        }
                        //订单状态2-2，商家修改订单资料，需要考虑LOIInstrument是否需要更新
                    }else if(old_order.getOrder_confirmed() == 2){
                        //设置该变量，当order的仪器发生改变时，则仪器更新也完成才能算成功；若仪器未发生改变，则直接判断更新order的结果
                        boolean updateSuccess = true;
                        if(order.getDevice_id().intValue() != old_order.getDevice_id()){
                            updateSuccess = false;
                            //此处表明订单的机器改变了，需要对两个机器的属性P_orders_string进行修改，后再对ProvideOrder进行更新
                            LOIInstrument old_instrument = instrumentService.getInstrumentById(old_order.getDevice_id());
                            LOIInstrument new_instrument = instrumentService.getInstrumentById(order.getDevice_id());
                            String old_provideOrders_string = StringAdjustUtils.interceptOrderStrings(old_instrument
                                    .getP_orders_string(), String.valueOf(order.getOrder_id()));
                            String new_provideOrders_string = StringAdjustUtils.appendOrderStrings(new_instrument
                                    .getP_orders_string(),String.valueOf(order.getOrder_id()));
                            old_instrument.setP_orders_string(old_provideOrders_string);
                            new_instrument.setP_orders_string(new_provideOrders_string);
                            if(instrumentService.updateInstrument(old_instrument) && instrumentService.updateInstrument(new_instrument)){
                                updateSuccess = true;
                            }
                        }
                        if(updateSuccess && dao.updateOrder(order, mapper)){
                            return true;
                        }
                    }
                    break;
                case 3:
                    //订单状态1-3，即商家在客户填写完（或修改完）资料后审核通过，额外需要更新客户的balance
                    if(old_order.getOrder_confirmed() == 1){
                        AppCustomer user = appCustomerService.getCustomerById(order.getUser_id());
                        user.setUser_balance(user.getUser_balance() - order.getDisplay_price());
                        if(appCustomerService.updateAppCustomer(user)){
                            int num_provide = dao.getConfirmedCounts(mapper);
                            int num_finished = finishedOrderService.getCountsForFinishedOrders();
                            order.setPlaced_orderNumber(num_finished + num_provide + 1);
                            if(dao.updateOrder(order, mapper)){
                                return true;
                            }
                        }
                    }else if(old_order.getOrder_confirmed() == 2){
                        AppCustomer user = appCustomerService.getCustomerById(order.getUser_id());
                        user.setUser_balance(user.getUser_balance() - order.getDisplay_price());
                        if(appCustomerService.updateAppCustomer(user)){
                            int num_provide = dao.getConfirmedCounts(mapper);
                            int num_finished = finishedOrderService.getCountsForFinishedOrders();
                            order.setPlaced_orderNumber(num_finished + num_provide + 1);
                            if(dao.updateOrder(order, mapper)){
                                return true;
                            }
                        }
                    }
                    break;
                default:break;
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

    //客户撤销订单,需要先更新相应客户AppCustomer的numberForProvideOrder,后更新ProvideOrder
    public boolean drawbackProvideOrderById(int order_id) {
        SqlSession session = factory.openSession();
        ProvideOrderMapper mapper = session.getMapper(ProvideOrderMapper.class);
        try{
            ProvideOrder provideOrder = dao.getOrderById(order_id, mapper);
            AppCustomer customer = appCustomerService.getCustomerById(provideOrder.getUser_id());
            customer.setNumberForProvideOrders(customer.getNumberForProvideOrders() - 1);
            provideOrder.setUser_id(null);
            provideOrder.setUser_name(null);
            provideOrder.setOrderForRelatedUser(null);
            provideOrder.setOrder_confirmed(0);
            provideOrder.setOrder_placed(null);
            provideOrder.setMaterialName(null);
            provideOrder.setMaterialType(null);
            provideOrder.setMaterial_explanation(null);
            provideOrder.setPlaced_orderNumber(null);
            provideOrder.setExtra_explanation(null);
            if(appCustomerService.updateAppCustomer(customer)){
                if(dao.updateOrder(provideOrder, mapper)){
                    return true;
                }
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

    public List<BriefOrderItem> getAllOriginalItems() {
        SqlSession session = factory.openSession();
        ProvideOrderMapper mapper = session.getMapper(ProvideOrderMapper.class);
        try {
            List<ProvideOrder> list_order = dao.getAllOriginalOrders(mapper);
            if(list_order == null || list_order.isEmpty()){
                return new ArrayList<>();
            }else {
                List<LOIInstrument> instruments = instrumentService.getAllInstruments();
                List<BriefOrderItem> list_items = new ArrayList<>();
                for(int i = 0; i < list_order.size(); i++){
                    ProvideOrder order = list_order.get(i);
                    LOIInstrument instrument = instruments.get(order.getDevice_id() - 1);
                    String location = instrument.getFactory_longitude() + ":" + instrument.getFactory_latitude();
                    list_items.add(new BriefOrderItem(order, location));
                }
                return list_items;
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }finally {
            session.close();
        }
    }

    public boolean addNewProvideOrder(ProvideOrder order) {
        SqlSession session = factory.openSession();
        ProvideOrderMapper mapper = session.getMapper(ProvideOrderMapper.class);
        try {
            if(dao.addProvideOrder(order, mapper)){
                LOIInstrument instrument = instrumentService.getInstrumentById(order.getDevice_id());
                String provideOrders_string = StringAdjustUtils.interceptOrderStrings(instrument
                        .getP_orders_string(), String.valueOf(order.getOrder_id()));
                instrument.setP_orders_string(provideOrders_string);
                AppCustomer customer = appCustomerService.getCustomerById(order.getSaler_id());
                customer.setNumberForProvideOrders(customer.getNumberForProvideOrders() + 1);
                if(appCustomerService.updateAppCustomer(customer) && instrumentService.updateInstrument(instrument)){
                    return true;
                }
                session.rollback();
                return false;
            }else {
                session.rollback();
                return false;
            }

        }catch (Exception e){
            session.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }
}
