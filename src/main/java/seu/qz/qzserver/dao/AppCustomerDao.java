package seu.qz.qzserver.dao;


import org.springframework.stereotype.Component;
import seu.qz.qzserver.entity.AppCustomer;
import seu.qz.qzserver.mybatismapper.AppCustomerMapper;

import java.util.List;


@Component
public class AppCustomerDao {

    public AppCustomer getAppCustomerById(Integer id, AppCustomerMapper mapper){
        AppCustomer customer = null;
        customer = mapper.getCustomerById(id);
        return customer;
    }

    public AppCustomer getAppCustomerByNickname(String name, AppCustomerMapper mapper){
        AppCustomer customer = null;
        customer = mapper.getCustomerByName(name);
        return customer;
    }

    public AppCustomer getAppCustomerByRegisterName(String user_registerName, AppCustomerMapper mapper){
        AppCustomer customer = null;
        customer = mapper.getCustomerByRegisterName(user_registerName);
        return customer;
    }

    public AppCustomer getAppCustomerByPhoneNumber(String phoneNumber, AppCustomerMapper mapper){
        AppCustomer customer = null;
        customer = mapper.getCustomerByPhoneNumber(phoneNumber);
        return customer;
    }

    public Boolean addCustomer(AppCustomer customer, AppCustomerMapper mapper) {
        int returnValue = 0;
        returnValue = mapper.addCustomer(customer);
        return returnValue != 0;
    }

    public AppCustomer getAppCustomerByEmail(String email, AppCustomerMapper mapper) {
        AppCustomer customer = null;
        customer = mapper.getCustomerByEmail(email);
        return customer;
    }

    public boolean updateAppCustomer(AppCustomer customer, AppCustomerMapper mapper) {
        return mapper.updateCustomer(customer) > 0 ? true : false;
    }

    public List<AppCustomer> getAppCustomersByEmail(String email, AppCustomerMapper mapper) {
        return mapper.getCustomersByEmail(email);
    }

    public List<AppCustomer> getAppCustomersByPhoneNumber(String phoneNumber, AppCustomerMapper mapper) {
        return mapper.getAppCustomersByPhoneNumber(phoneNumber);
    }
}
