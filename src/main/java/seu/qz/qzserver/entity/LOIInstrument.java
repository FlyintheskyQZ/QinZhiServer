package seu.qz.qzserver.entity;


import java.io.Serializable;
import java.util.Map;


/**
 * 氧指数仪对象类：
 *      仪器id，商家名称，商家地址，商家地址经度，商家地址纬度，商家联系电话，商家说明，仪器提供的订单，仪器已完成的订单，
 *      提供订单集合字符串，完成订单集合字符串，仪器持有者的id,该仪器为其持有者的第几个仪器
 */
public class LOIInstrument implements Serializable {

    private Integer device_id;

    private String factory_name;
    private String factory_address;
    private String factory_longitude;
    private String factory_latitude;
    private String factory_phoneNumber;

    private String extra_description;

    private Map<String, ProvideOrder> provide_orders;
    private Map<String, FinishedOrder> finished_orders;
    private String p_orders_string;
    private String f_orders_string;
    private Integer related_user_id;
    private Integer orderForRelatedSaler;

    public LOIInstrument() {
        super();
    }

    public LOIInstrument(Integer device_id, String factory_name, String factory_address, String factory_longitude,
                         String factory_latitude, String factory_phoneNumber, String extra_description,
                         Map<String, ProvideOrder> provide_orders, Map<String, FinishedOrder> finished_orders,
                         String p_orders_string, String f_orders_string, Integer related_user_id, Integer orderForRelatedSaler) {
        this.device_id = device_id;
        this.factory_name = factory_name;
        this.factory_address = factory_address;
        this.factory_longitude = factory_longitude;
        this.factory_latitude = factory_latitude;
        this.factory_phoneNumber = factory_phoneNumber;
        this.extra_description = extra_description;
        this.provide_orders = provide_orders;
        this.finished_orders = finished_orders;
        this.p_orders_string = p_orders_string;
        this.f_orders_string = f_orders_string;
        this.related_user_id = related_user_id;
        this.orderForRelatedSaler = orderForRelatedSaler;
    }

    @Override
    public String toString() {
        return "LOIInstrument{" +
                "device_id=" + device_id +
                ", factory_name='" + factory_name + '\'' +
                '}';
    }

    public Integer getOrderForRelatedSaler() {
        return orderForRelatedSaler;
    }

    public void setOrderForRelatedSaler(Integer orderForRelatedSaler) {
        this.orderForRelatedSaler = orderForRelatedSaler;
    }

    public String getP_orders_string() {
        return p_orders_string;
    }

    public void setP_orders_string(String p_orders_string) {
        this.p_orders_string = p_orders_string;
    }

    public String getF_orders_string() {
        return f_orders_string;
    }

    public void setF_orders_string(String f_orders_string) {
        this.f_orders_string = f_orders_string;
    }

    public void setFactory_phoneNumber(String factory_phoneNumber) {
        this.factory_phoneNumber = factory_phoneNumber;
    }

    public Integer getRelated_user_id() {
        return related_user_id;
    }

    public void setRelated_user_id(Integer related_user_id) {
        this.related_user_id = related_user_id;
    }

    public String getFactory_phoneNumber() {
        return factory_phoneNumber;
    }

    public void setFactory_phonenumber(String factory_phonenumber) {
        this.factory_phoneNumber = factory_phonenumber;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public String getFactory_name() {
        return factory_name;
    }

    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }

    public String getFactory_address() {
        return factory_address;
    }

    public void setFactory_address(String factory_address) {
        this.factory_address = factory_address;
    }

    public String getFactory_longitude() {
        return factory_longitude;
    }

    public void setFactory_longitude(String factory_longitude) {
        this.factory_longitude = factory_longitude;
    }

    public String getFactory_latitude() {
        return factory_latitude;
    }

    public void setFactory_latitude(String factory_latitude) {
        this.factory_latitude = factory_latitude;
    }

    public String getExtra_description() {
        return extra_description;
    }

    public void setExtra_description(String extra_description) {
        this.extra_description = extra_description;
    }

    public Map<String, ProvideOrder> getProvide_orders() {
        return provide_orders;
    }

    public void setProvide_orders(Map<String, ProvideOrder> provide_orders) {
        this.provide_orders = provide_orders;
    }

    public Map<String, FinishedOrder> getFinished_orders() {
        return finished_orders;
    }

    public void setFinished_orders(Map<String, FinishedOrder> finished_orders) {
        this.finished_orders = finished_orders;
    }
}
