package seu.qz.qzserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.qz.qzserver.entity.*;
import seu.qz.qzserver.service.AppCustomerService;
import seu.qz.qzserver.service.FinishedOrderService;
import seu.qz.qzserver.service.ProvideOrderService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    public AppCustomerService appCustomerService;

    @Autowired
    public FinishedOrderService finishedOrderService;

    @Autowired
    public ProvideOrderService provideOrderService;

    /**
     * 本控制器用于与服务器的MainActivity的orderFragment的RecyclerView内的Item进行对接，即展示订单界面的简要订单项
     * 由于AppCustomer分为user和saler（买家和卖家），故在展示各自相关订单时获取方式有所不同，订单的排序均是按照订单下单的顺序排序的，已provideorder在前，finishedorder在后
     * @param user_nickName
     * @param startnumber
     * @param contentnumber
     * @param orderType
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBriefItems")
    public List<BriefOrderItem> getBriefItemsFromProvide(String user_nickName, String startnumber, String contentnumber, String orderType){
        //如果起始序号小于0，内容长度小于等于0，则无语传回BriefOrderItem，直接返回null
        System.out.println("start search" + startnumber);
        int startNumber = Integer.parseInt(startnumber);
        int contentNumber = Integer.parseInt(contentnumber);
        if(startNumber < 0 || contentNumber <= 0){
            return null;
        }
        //获取按照ProvideOrder或者FinishedOrder的orderForRelatedUser属性排序后（大的排前）返回的集合
        List<BriefOrderItem> list = null;
        //查询并判断当前customer是用户还是商户，查找对应的order集合，并进行排序（按照下单顺序，且ProvideOrder先于FinishedOrder）
        AppCustomer customer = appCustomerService.getCustomerByNickeame(user_nickName);
        System.out.println(customer.getUser_nickName());
        if(customer.getAuthority_level().intValue() == 1){
            if(orderType.equals("ProvideOrder")){
                list = provideOrderService.getBriefOrdersByUsernameInOrder(user_nickName);
            }else if(orderType.equals("FinishedOrder")){
                list = finishedOrderService.getBriefOrdersByUsernameInOrder(user_nickName);
            }
        }else {
            if(orderType.equals("ProvideOrder")){
                list = provideOrderService.getBriefOrdersBySalerNameInOrder(customer.getUser_registerName());
            }else if(orderType.equals("FinishedOrder")){
                list = finishedOrderService.getBriefOrdersBySalernameInOrder(customer.getUser_registerName());
            }
        }
        System.out.println("start search2"+ list.size());
        //如果startNumber过大则直接返回null，表示已经加载所有的相关order
        if(list == null || startNumber > list.size()){
            return null;
        }
        System.out.println("start search 3");
        //根据startNuber和contentNumber的值，返回对应数量和位置的集合
        List<BriefOrderItem> returnList = new ArrayList<>();;
        if(startNumber + contentNumber > list.size()){
            for(int i = startNumber - 1; i < list.size(); i++){
                returnList.add(list.get(i));
            }
        }else {
            for(int i = startNumber - 1; i < startNumber + contentNumber - 1; i++){
                returnList.add(list.get(i));
            }
        }
        System.out.println(returnList.size()+"!!!!!!!!!!!!!!!!");
        return returnList;
    }


    @ResponseBody
    @RequestMapping("/getFinishedOrderById")
    public FinishedOrder getFinishedOrderById(String order_id){
        FinishedOrder order = finishedOrderService.getOrderById(Integer.valueOf(order_id));
        if(order == null){
            return null;
        }else {
            return order;
        }
    }

    @ResponseBody
    @RequestMapping("/getReportItems")
    public List<BriefReportItem> getReportItems(String user_id){
        if(user_id == null || user_id.isEmpty()){
            return null;
        }
        int userid = Integer.parseInt(user_id);
        List<BriefReportItem> list = finishedOrderService.getReportItemsByUserId(userid);
        return list;
    }

    @ResponseBody
    @RequestMapping("/getSearchedItems")
    public List<BriefOrderItem> getSearchedItems(){
        List<BriefOrderItem> list = provideOrderService.getAllOriginalItems();
        return list;
    }

    @ResponseBody
    @RequestMapping("/getProvideOrderById")
    public ProvideOrder getProvideOrderById(String order_id){
        ProvideOrder order = provideOrderService.getOrderById(Integer.valueOf(order_id));
        System.out.println(order.getRentTime_end().toString());
        if(order == null){
            return null;
        }else {
            return order;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteProvideOrder")
    public String deleteProvideOrder(String order_id){
        System.out.println("deleteOrder:" + order_id);
        if(provideOrderService.deleteProvideOrderById(Integer.parseInt(order_id))){
            System.out.println("Success");
            return "Success";
        }else {
            System.out.println("Failed");
            return "Failed";
        }
    }


    /**
     * 客户撤销订单
     * @param order_id
     * @return
     */
    @ResponseBody
    @RequestMapping("/drawbackProvideOrder")
    public String drawbackProvideOrder(String order_id){
        System.out.println("drawbackOrder:" + order_id);
        if(provideOrderService.drawbackProvideOrderById(Integer.parseInt(order_id))){
            System.out.println("Success");
            return "Success";
        }else {
            System.out.println("Failed");
            return "Failed";
        }
    }

    /**
     * 在数据库中更新ProvideOrder，以及与之相关的AppCustomer、LOIInstrument
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateProvideOrder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateProvideOrder(@RequestBody ProvideOrder order) {
        if(order == null){
            return "Failed";
        }
        System.out.println(order.toString());
        if(provideOrderService.updateProvideOrder(order)){
            System.out.println("Success");
            return "Success";
        }else {
            System.out.println("Failed");
            return "Failed";
        }
    }

    /**
     * 在数据库中添加ProvideOrder，以及更新与之相关的AppCustomer、LOIInstrument
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addNewProvideOrder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String addNewProvideOrder(@RequestBody ProvideOrder order) {
        if(order == null){
            return "Failed";
        }
        System.out.println(order.toString());
        if(provideOrderService.addNewProvideOrder(order)){
            System.out.println("Success");
            return "Success";
        }else {
            System.out.println("Failed");
            return "Failed";
        }
    }


    }
