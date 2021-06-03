package seu.qz.qzserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import seu.qz.qzserver.entity.*;
import seu.qz.qzserver.service.*;
import seu.qz.qzserver.util.DateFormatTransUtils;
import seu.qz.qzserver.util.PDFCreate;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class BrowserController {

    @Autowired
    public AppCustomerService appCustomerService;

    @Autowired
    public FinishedOrderService finishedOrderService;

    @Autowired
    public ProvideOrderService provideOrderService;

    @Autowired
    public ExperimentResultService experimentResultService;

    @Autowired
    public LOIInstrumentService instrumentService;


    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String serverMailAddress;

    @PostMapping("/loginFromBrowser")
    public String loginFromBrowser(@RequestParam("username") String username, @RequestParam("password") String password,
                                   Map<String, String> map, HttpSession session){
        String msg = null;
        if(StringUtils.isEmpty(username)){
            //账号为空
            msg = "账号不得为空!";
        }else if(StringUtils.isEmpty(password)){
            //密码为空
            msg = "密码不得为空!";
        }else {
            AppCustomer customer = appCustomerService.getCustomerByNickeame(username);
            if(customer == null){
                //数据库中无对应用户
            msg = "该用户不存在，请重新输入或先注册!";
            }else if(!customer.getUser_password().equals(password)){
                //密码错误
            msg = "用户密码错误!";
            }else {
                //成功登录
                session.setAttribute("loginUser", username);
                return "redirect:/user_main.html";
            }
        }
        map.put("msg", msg);
        return "page-login";
    }


    /**
     * 本控制器用于与服务器的MainActivity的orderFragment的RecyclerView内的Item进行对接，即展示订单界面的简要订单项
     * 由于AppCustomer分为user和saler（买家和卖家），故在展示各自相关订单时获取方式有所不同，订单的排序均是按照订单下单的顺序排序的，已provideorder在前，finishedorder在后
     * @return
     */
    @RequestMapping("/getBriefItemsFromBrowser")
    public String getBriefItemsFromBrowser(HttpSession session, Model model){
        String user_nickName = (String) session.getAttribute("loginUser");
        //获取按照ProvideOrder或者FinishedOrder的orderForRelatedUser属性排序后（大的排前）返回的集合
        List<BriefOrderItem> list_p = null;
        List<BriefOrderItem> list_f = null;
        //查询并判断当前customer是用户还是商户，查找对应的order集合，并进行排序（按照下单顺序，且ProvideOrder先于FinishedOrder）
        AppCustomer customer = appCustomerService.getCustomerByNickeame(user_nickName);
        System.out.println(customer.getUser_nickName());
        if(customer.getAuthority_level().intValue() == 1){
            list_p = provideOrderService.getBriefOrdersByUsernameInOrder(user_nickName);
            list_f = finishedOrderService.getBriefOrdersByUsernameInOrder(user_nickName);
        }else {
            list_p = provideOrderService.getBriefOrdersBySalerNameInOrder(customer.getUser_registerName());
            list_f = finishedOrderService.getBriefOrdersBySalernameInOrder(customer.getUser_registerName());
        }
        List<BriefOrderItem> returnList = new ArrayList<>();;
        if(list_p != null){
            for(int i = 0; i < list_p.size(); i++){
                returnList.add(list_p.get(i));
            }
        }
        if(list_f != null){
            for(int i = 0; i < list_f.size(); i++){
                returnList.add(list_f.get(i));
            }
        }
        System.out.println(returnList.size()+"!!!!!!!!!!!!!!!!");
        model.addAttribute("user_orders", returnList);
        return "table-data-table";
    }

    @RequestMapping("/getFinishedOrderFromBrowser/{id}")
    public String getFinishedOrderFromBrowser(@PathVariable("id") Integer id, Model model, HttpSession session){
        FinishedOrder finishedOrder = finishedOrderService.getOrderById(id);
        AppCustomer saler = appCustomerService.getCustomerById(finishedOrder.getSaler_id());
        AppCustomer user = appCustomerService.getCustomerById(finishedOrder.getUser_id());
        if(finishedOrder != null){
            model.addAttribute("order", finishedOrder);
            model.addAttribute("user", user);
            model.addAttribute("saler", saler);
            String loginName = (String) session.getAttribute("loginUser");
            if(loginName.equals(saler.getUser_nickName())){
                model.addAttribute("user_type", 2);
            }else {
                model.addAttribute("user_type", 1);
            }
            //1表示未被订购，2表示等待审核，3表示审核失败，4表示审核成功，5表示订单完成
            model.addAttribute("order_state", 5);
            return "page-invoice";
        }else{
            return "table-data-table";
        }
    }

    @RequestMapping("/getProvideOrderFromBrowser/{id}")
    public String getProvideOrderFromBrowser(@PathVariable("id") Integer id, Model model){
        ProvideOrder provideOrder = provideOrderService.getOrderById(id);
        AppCustomer saler = appCustomerService.getCustomerById(provideOrder.getSaler_id());
        AppCustomer user = appCustomerService.getCustomerById(provideOrder.getUser_id());
        if(provideOrder != null){
            model.addAttribute("order", provideOrder);
            model.addAttribute("user", user);
            model.addAttribute("saler", saler);
            //1表示未被订购，2表示等待审核，3表示审核失败，4表示审核成功，5表示订单完成

            model.addAttribute("order_state", provideOrder.getOrder_confirmed() + 1);
            return "page-invoice";
        }else{
            return "table-data-table";
        }
    }
    @ResponseBody
    @RequestMapping("/sendPDFToEmailFromBrowser")
    public String sendPDFToEmailFromBrowser(String orderId){
        if(orderId == null || orderId.isEmpty()){
            return "发送失败";
        }
        System.out.println(orderId);
        FinishedOrder order = finishedOrderService.getOrderById(Integer.parseInt(orderId));
        if(order == null){
            return "无该订单";
        }
        AppCustomer customer = appCustomerService.getCustomerById(order.getUser_id());
        File downloadFile = new File("D:/GraduationDesign/DownloadPDF", orderId + ".pdf");
        //如果PDF不存在，则创建
        if(!downloadFile.exists()){
            ExperimentResult result = experimentResultService.getResultById(order.getResult_id());
            PDFCreate.createPDF(order, result);
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setSubject("实验报告");
            helper.setFrom(serverMailAddress);
            helper.setTo(customer.getEmail());
            helper.setSentDate(new Date());
            helper.setText("实验报告结果pdf在附件中，请注意查收！");
            helper.addAttachment(DateFormatTransUtils.getStringChinese(order.getRentTime_end()) + "-" +
                    customer.getUser_nickName() + "-" + orderId + "-" + order.getMaterialName() + ".pdf", downloadFile);
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
            return "发送出错！";
        }
        return "发送成功！";
    }

    //对应客户端下载实验结果报告pdf文件的控制器
    @RequestMapping("/downloadPDFFromBrowser/{order_id}")
    public void downloadPDFFromBrowser(@PathVariable("order_id")String order_id, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        // 获取本地PDF文件
        File downloadFile = new File("D:/GraduationDesign/DownloadPDF", order_id + ".pdf");
        String fullPath = downloadFile.getAbsolutePath();
        ServletContext context = request.getServletContext();
        System.out.println("order_id="+order_id);
        System.out.println("下载路径:"+fullPath);
        //如果PDF不存在，则创建
        if(!downloadFile.exists()){
            FinishedOrder order = finishedOrderService.getOrderById(Integer.parseInt(order_id));
            ExperimentResult result = experimentResultService.getResultById(order.getResult_id());
            PDFCreate.createPDF(order, result);
        }
        // 获取文件的扩展类型信息
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // 若未找到则设置为任意二进制文件
            mimeType = "application/octet-stream";
        }

        // 在response中设置文件扩展类型
        response.setContentType(mimeType);
        //设置文件字节数
        //response.setContentLength((int) downloadFile.length());

        // 设置response的header
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);
        response.setHeader("Accept-Ranges", "bytes");
        // 解析断点续传相关信息
        long downloadSize = downloadFile.length();
        long fromPos = 0, toPos = 0;
        //根据请求的Range来判断是否已经下载一部分
        if (request.getHeader("Range") == null) {
            response.setHeader("Content-Length", downloadSize + "");
        } else {
            // 若客户端传来Range，说明之前下载了一部分，设置206状态(SC_PARTIAL_CONTENT)
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            String range = request.getHeader("Range");
            String bytes = range.replaceAll("bytes=", "");
            String[] ary = bytes.split("-");
            fromPos = Long.parseLong(ary[0]);
            if (ary.length == 2) {
                toPos = Long.parseLong(ary[1]);
            }
            int size;
            if (toPos > fromPos) {
                size = (int) (toPos - fromPos);
            } else {
                size = (int) (downloadSize - fromPos);
            }
            response.setHeader("Content-Length", size + "");
            downloadSize = size;
        }
        // Copy the stream to the response's output stream.
        RandomAccessFile in = null;
        OutputStream out = null;
        try {
            in = new RandomAccessFile(downloadFile, "rw");
            // 设置下载起始位置
            if (fromPos > 0) {
                in.seek(fromPos);
            }
            // 缓冲区大小
            int bufLen = (int) (downloadSize < 2048 ? downloadSize : 2048);
            byte[] buffer = new byte[bufLen];
            int num;
            int count = 0; // 当前写到客户端的大小
            out = response.getOutputStream();
            while ((num = in.read(buffer)) != -1) {
                out.write(buffer, 0, num);
                count += num;
                //处理最后一段，计算不满缓冲区的大小
                if (downloadSize - count < bufLen) {
                    bufLen = (int) (downloadSize-count);
                    if(bufLen==0){
                        break;
                    }
                    buffer = new byte[bufLen];
                }
                //放慢下载速度，调试暂停和继续按键
                Thread.sleep(50);
            }
            response.flushBuffer();
        } catch (Exception e) {
            System.out.println("数据被暂停或中断。");
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("数据被暂停或中断。");
                    e.printStackTrace();
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("数据被暂停或中断。");
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/registerFromBrowser", method = RequestMethod.POST)
    public String registerFromBrowser(AppCustomer customer){
        System.out.println(customer.getUser_nickName());
        customer.setRegister_time(new Date());
        customer.setUser_balance(0);
        customer.setNumberForFinishedOrders(0);
        customer.setNumberForProvideOrders(0);
        AlreadyExistCustomer existCustomer = appCustomerService.addCustomer(customer);
        if(existCustomer.isExisted()){
            return "redirect:/form-samples.html";
        }else{
            return "page-login";
        }
    }

    @RequestMapping(value = "/publishOrderFromBrowser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String publishOrderFromBrowser(ProvideOrder order, String startTime, String endTime) {
        if(order == null){
            return "redirect:/form-components.html";
        }
        Date startDate = DateFormatTransUtils.formDateByString(startTime, DateFormatTransUtils.TYPE_SHORT);
        Date endDate = DateFormatTransUtils.formDateByString(endTime, DateFormatTransUtils.TYPE_SHORT);
        order.setRentTime_begin(startDate);
        order.setRentTime_end(endDate);
        AppCustomer customer = appCustomerService.getCustomerById(order.getSaler_id());
        order.setOrderForRelatedSaler(customer.getNumberForProvideOrders() + 1);
        LOIInstrument instrument = instrumentService.getInstrumentById(order.getDevice_id());
        order.setDevice_orderForSaler(instrument.getOrderForRelatedSaler());
        order.setOrder_confirmed(new Integer(0));
        System.out.println(order.toString());
        if(provideOrderService.addNewProvideOrder(order)){
            System.out.println("Success");
            return "dashboard";
        }else {
            System.out.println("Failed");
            return "redirect:/form-components.html";
        }
    }

    @RequestMapping(value = "/registerDeviceFromBrowser", method = RequestMethod.POST)
    public String registerDeviceFromBrowser(LOIInstrument instrument){
        System.out.println(instrument.toString());
        if(instrument == null){
            return "redirect:/user_main.html";
        }
        AppCustomer saler = appCustomerService.getCustomerById(instrument.getRelated_user_id());
        instrument.setFactory_phoneNumber(saler.getPhoneNumber());
        if(instrumentService.addNewInstrument(instrument)){
            return "dashboard";
        }else {
            return "redirect:/user_main.html";
        }
    }
}
