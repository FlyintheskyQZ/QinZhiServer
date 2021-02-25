package seu.qz.qzserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import seu.qz.qzserver.entity.*;
import seu.qz.qzserver.service.AppCustomerService;
import seu.qz.qzserver.service.FinishedOrderService;
import seu.qz.qzserver.service.ProvideOrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AppCustomerController {

    @Autowired
    public AppCustomerService appCustomerService;




    /**
     * 客户端登录对应的控制器，若登陆成功则返回用户信息（json）并由客户端进行客户信息的更新，否则返回空，
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public AppCustomer loginFromAndroid(@RequestParam("username") String username, @RequestParam("password") String password){

        AppCustomer customer = appCustomerService.getCustomerByNickeame(username);

        System.out.println(username+" ;"+ password);

        if(customer == null){
            return new AppCustomer();
        }
        if(password.equals(customer.getUser_password()) ){
            System.out.println(customer.getRegister_time());
            System.out.println("Success");
            return customer;
        }
        return new AppCustomer();
    }

    /**
     * 客户端用户注册对应的控制器，客户端会对输入格式进行校正，服务端则是会通过用户名，真实姓名，注册邮箱，手机号码四项来判断是否已注册，若未注册，返回注册的customer的id
     * @param customer
     * @param 
     * @param
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String registerFromAndroid(@RequestBody AppCustomer customer){
        System.out.println(customer.getUser_nickName());
//        customer.setRegister_time(new Date(Long.parseLong(date_str)));
//        customer.setAuthority_level(Integer.parseInt(level_str));
//        customer.setUser_balance(0);
//        customer.setNumberForFinishedOrders(0);
//        customer.setNumberForProvideOrders(0);
        AlreadyExistCustomer existCustomer = appCustomerService.addCustomer(customer);
        if(existCustomer.isExisted()){
            return existCustomer.getReason();
        }else{
            return existCustomer.getCustomer().getUser_id().toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateCustomer(@RequestBody AppCustomer customer){
        System.out.println(customer.toString());
        if(customer == null){
            return "Failed";
        }
        //判断是否存在相同手机号和Email的用户记录，如存在则更新失败
        List<AppCustomer> customers_existed = appCustomerService.getCustomersByEmail(customer.getEmail());
        if(customers_existed.size() > 1){
            System.out.println("Email");
            return "Same Email";
        }
        customers_existed = appCustomerService.getCustomersByPhone(customer.getPhoneNumber());
        if(customers_existed.size() > 1){
            System.out.println("phoneNumber");
            return "Same phoneNumber";
        }
        if(appCustomerService.updateAppCustomer(customer)){
            System.out.println("Success");
            return "Success";
        }else {
            System.out.println("Failed_2");
            return "Failed";
        }
    }



    @ResponseBody
    @RequestMapping("/getAppCustomerById")
    public AppCustomer getAppCustomerById(String id){
        AppCustomer customer = appCustomerService.getCustomerById(Integer.valueOf(id));
        if(customer == null){
            return null;
        }else {
            return customer;
        }
    }




}
