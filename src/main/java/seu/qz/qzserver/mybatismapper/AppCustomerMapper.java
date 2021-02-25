package seu.qz.qzserver.mybatismapper;

import org.apache.ibatis.annotations.Mapper;
import seu.qz.qzserver.entity.AppCustomer;

import java.util.List;


public interface AppCustomerMapper {

    public AppCustomer getCustomerById(Integer id);

    public AppCustomer getCustomerByName(String name);

    public int addCustomer(AppCustomer customer);

    public int deleteCustomerById(Integer id);

    public int updateCustomer(AppCustomer customer);

    AppCustomer getCustomerByRegisterName(String user_registerName);

    AppCustomer getCustomerByPhoneNumber(String phoneNumber);

    AppCustomer getCustomerByEmail(String email);

    List<AppCustomer> getCustomersByEmail(String email);

    List<AppCustomer> getAppCustomersByPhoneNumber(String phoneNumber);
}
