package seu.qz.qzserver.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 移动app客户对象类：
 *      用户id，用户昵称，用户密码，用户余额，用户注册姓名，用户注册身份证号，用户注册日期，用户权限等级，用户拥有的仪器编号（商家则为仪器id，客户则为0）
 *      用户持有的未完成的订单数，用户持有的已完成的订单数
 */
public class AppCustomer implements Serializable {

    private Integer user_id;

    private String user_nickName;
    private String user_password;
    private Integer user_balance;

    private String user_registerName;
    private String user_identityId;
    private Date register_time;

    //普通用户等级为1，商家等级为2
    private Integer authority_level;
    private boolean isMale;
    private String related_device_id;

    private String phoneNumber;
    private String email;

    private Integer numberForProvideOrders;
    private Integer numberForFinishedOrders;


    public AppCustomer() {
        super();
    }

    public AppCustomer(Integer user_id, String user_nickName, String user_password, Integer user_balance,
                       String user_registerName, String user_identityId, Date register_time, Integer authority_level,
                       String related_device_id, String phoneNumber, String email, Integer numberForProvideOrders,
                       Integer numberForFinishedOrders) {
        this.user_id = user_id;
        this.user_nickName = user_nickName;
        this.user_password = user_password;
        this.user_balance = user_balance;
        this.user_registerName = user_registerName;
        this.user_identityId = user_identityId;
        this.register_time = register_time;
        this.authority_level = authority_level;
        this.related_device_id = related_device_id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.numberForProvideOrders = numberForProvideOrders;
        this.numberForFinishedOrders = numberForFinishedOrders;
    }

    @Override
    public String toString() {
        return "AppCustomer{" +
                "user_id=" + user_id +
                ", user_nickName='" + user_nickName + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_balance=" + user_balance +
                ", user_registerName='" + user_registerName + '\'' +
                ", user_identityId='" + user_identityId + '\'' +
                ", register_time=" + register_time +
                ", authority_level=" + authority_level +
                ", isMale=" + isMale +
                ", related_device_id='" + related_device_id + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", numberForProvideOrders=" + numberForProvideOrders +
                ", numberForFinishedOrders=" + numberForFinishedOrders +
                '}';
    }

    public Integer getNumberForProvideOrders() {

        return numberForProvideOrders;
    }

    public void setNumberForProvideOrders(Integer numberForProvideOrders) {
        this.numberForProvideOrders = numberForProvideOrders;
    }

    public Integer getNumberForFinishedOrders() {
        return numberForFinishedOrders;
    }

    public void setNumberForFinishedOrders(Integer numberForFinishedOrders) {
        this.numberForFinishedOrders = numberForFinishedOrders;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRelated_device_id() {
        return related_device_id;
    }

    public void setRelated_device_id(String related_device_id) {
        this.related_device_id = related_device_id;
    }

    public Integer getAuthority_level() {
        return authority_level;
    }

    public void setAuthority_level(Integer authority_level) {
        this.authority_level = authority_level;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_nickName() {
        return user_nickName;
    }

    public void setUser_nickName(String user_nickName) {
        this.user_nickName = user_nickName;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public Integer getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(Integer user_balance) {
        this.user_balance = user_balance;
    }

    public String getUser_registerName() {
        return user_registerName;
    }

    public void setUser_registerName(String user_registerName) {
        this.user_registerName = user_registerName;
    }

    public String getUser_identityId() {
        return user_identityId;
    }

    public void setUser_identityId(String user_identityId) {
        this.user_identityId = user_identityId;
    }

    public Date getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}
