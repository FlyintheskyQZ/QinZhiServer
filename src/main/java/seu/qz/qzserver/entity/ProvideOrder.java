package seu.qz.qzserver.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 待定订单对象类：
 *      订单id，订单对应的仪器id，订单对应的用户id，买家的昵称，工厂的名称，仪器在其所在工厂的编号，对应买家的第几个待定订单序号，订单价格，订单起始时间，订单结束时间，订单确认标志
 *      对应卖家的第几个待定订单序号，对应的卖家id，下单时间（如果已下单则有 ，未下单则无）
 */
public class ProvideOrder implements Serializable {

    private Integer order_id;
    private Integer device_id;
    private Integer user_id;
    private String user_name;
    private String saler_name;
    private String factory_name;

    private Integer device_orderForSaler;
    private Integer orderForRelatedUser;
    private Integer display_price;
    private Date rentTime_begin;
    private Date rentTime_end;

    private Integer order_confirmed;
    private Integer orderForRelatedSaler;
    private Integer saler_id;
    private Date order_placed;
    private String materialName;
    private Integer materialType;
    private Integer placed_orderNumber;
    private String material_explanation;
    private String device_address;
    private String extra_explanation;



    public ProvideOrder() {
        super();
    }


    @Override
    public String toString() {
        return "ProvideOrder{" +
                "order_id=" + order_id +
                ", device_id=" + device_id +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", saler_name='" + saler_name + '\'' +
                ", factory_name='" + factory_name + '\'' +
                ", device_orderForSaler=" + device_orderForSaler +
                ", orderForRelatedUser=" + orderForRelatedUser +
                ", display_price=" + display_price +
                ", rentTime_begin=" + rentTime_begin +
                ", rentTime_end=" + rentTime_end +
                ", order_confirmed=" + order_confirmed +
                ", orderForRelatedSaler=" + orderForRelatedSaler +
                ", saler_id=" + saler_id +
                ", order_placed=" + order_placed +
                ", materialName='" + materialName + '\'' +
                ", materialType=" + materialType +
                ", placed_orderNumber=" + placed_orderNumber +
                ", material_explanation='" + material_explanation + '\'' +
                ", device_address='" + device_address + '\'' +
                ", extra_explanation='" + extra_explanation + '\'' +
                '}';
    }

    public String getExtra_explanation() {
        return extra_explanation;
    }

    public void setExtra_explanation(String extra_explanation) {
        this.extra_explanation = extra_explanation;
    }

    public String getDevice_address() {
        return device_address;
    }

    public void setDevice_address(String device_address) {
        this.device_address = device_address;
    }

    public String getMaterial_explanation() {
        return material_explanation;
    }

    public void setMaterial_explanation(String material_explanation) {
        this.material_explanation = material_explanation;
    }

    public String getSaler_name() {
        return saler_name;
    }

    public void setSaler_name(String saler_name) {
        this.saler_name = saler_name;
    }

    public Integer getOrder_confirmed() {
        return order_confirmed;
    }

    public Integer getPlaced_orderNumber() {
        return placed_orderNumber;
    }

    public void setPlaced_orderNumber(Integer placed_orderNumber) {
        this.placed_orderNumber = placed_orderNumber;
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

    public Date getOrder_placed() {
        return order_placed;
    }

    public void setOrder_placed(Date order_placed) {
        this.order_placed = order_placed;
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

    public Integer getOrderForRelatedUser() {
        return orderForRelatedUser;
    }

    public void setOrderForRelatedUser(Integer orderForRelatedUser) {
        this.orderForRelatedUser = orderForRelatedUser;
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

    public Integer getDisplay_price() {
        return display_price;
    }

    public void setDisplay_price(Integer display_price) {
        this.display_price = display_price;
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

    public void setOrder_confirmed(Integer order_confirmed) {
        this.order_confirmed = order_confirmed;
    }
}
