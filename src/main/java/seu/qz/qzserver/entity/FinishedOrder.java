package seu.qz.qzserver.entity;


import java.util.Date;

/**
 * 完成的订单对象类：
 *      订单id，仪器id，用户id，仪器在其所在工厂的编号，买家名称，仪器所在工厂的名称，成交价格，租用起始时间，租用结束时间，操作员姓名，实验结果id，
 *      该订单对应其客户的第几个订单，该订单对应其商家的第几个订单，该订单对应的商家id
 */
public class FinishedOrder {

    private Integer order_id;
    private Integer device_id;
    private Integer user_id;
    private String saler_name;

    private Integer device_orderForSaler;
    private String user_name;
    private String factory_name;
    private Integer deal_price;
    private Date rentTime_begin;
    private Date rentTime_end;
    private String operator_name;

    private Integer result_id;
    private Integer orderForRelatedUser;
    private Integer orderForRelatedSaler;
    private Integer saler_id;

    private Date order_placed;
    private String materialName;
    private Integer materialType;
    private long result_data;
    private Integer placed_orderNumber;
    private String device_address;
    //此处对应ProvideOrder的material_explanation
    private String extra_explanation;

    public FinishedOrder() {
        super();
    }



    @Override
    public String toString() {
        return "FinishedOrder{" +
                "order_id=" + order_id +
                ", device_id=" + device_id +
                ", user_id=" + user_id +
                ", deal_price=" + deal_price +
                ", rentTime_begin=" + rentTime_begin +
                ", rentTime_end=" + rentTime_end +
                ", operator_name='" + operator_name + '\'' +
                ", result_id=" + result_id +
                '}';
    }

    public String getExtra_explanation() {
        return extra_explanation;
    }

    public void setExtra_explanation(String extra_explanation) {
        this.extra_explanation = extra_explanation;
    }

    public String getSaler_name() {
        return saler_name;
    }

    public void setSaler_name(String saler_name) {
        this.saler_name = saler_name;
    }

    public String getDevice_address() {
        return device_address;
    }

    public void setDevice_address(String device_address) {
        this.device_address = device_address;
    }

    public long getResult_data() {
        return result_data;
    }

    public void setResult_data(long result_data) {
        this.result_data = result_data;
    }

    public Integer getPlaced_orderNumber() {
        return placed_orderNumber;
    }

    public void setPlaced_orderNumber(Integer placed_orderNumber) {
        this.placed_orderNumber = placed_orderNumber;
    }

    public Date getOrder_placed() {
        return order_placed;
    }

    public void setOrder_placed(Date order_placed) {
        this.order_placed = order_placed;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getMaterialType() {
        return materialType;
    }

    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
    }

    public Integer getDevice_orderForSaler() {
        return device_orderForSaler;
    }

    public void setDevice_orderForSaler(Integer device_orderForSaler) {
        this.device_orderForSaler = device_orderForSaler;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFactory_name() {
        return factory_name;
    }

    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }

    public Integer getOrderForRelatedUser() {
        return orderForRelatedUser;
    }

    public void setOrderForRelatedUser(Integer orderForRelatedUser) {
        this.orderForRelatedUser = orderForRelatedUser;
    }

    public Integer getOrderForRelatedSaler() {
        return orderForRelatedSaler;
    }

    public void setOrderForRelatedSaler(Integer orderForRelatedSaler) {
        this.orderForRelatedSaler = orderForRelatedSaler;
    }

    public Integer getSaler_id() {
        return saler_id;
    }

    public void setSaler_id(Integer saler_id) {
        this.saler_id = saler_id;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDeal_price() {
        return deal_price;
    }

    public void setDeal_price(Integer deal_price) {
        this.deal_price = deal_price;
    }

    public Date getRentTime_begin() {
        return rentTime_begin;
    }

    public void setRentTime_begin(Date rentTime_begin) {
        this.rentTime_begin = rentTime_begin;
    }

    public Date getRentTime_end() {
        return rentTime_end;
    }

    public void setRentTime_end(Date rentTime_end) {
        this.rentTime_end = rentTime_end;
    }

    public Integer getResult_id() {
        return result_id;
    }

    public void setResult_id(Integer result_id) {
        this.result_id = result_id;
    }
}
