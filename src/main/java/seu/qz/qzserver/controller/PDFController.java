package seu.qz.qzserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.qz.qzserver.entity.AppCustomer;
import seu.qz.qzserver.entity.ExperimentResult;
import seu.qz.qzserver.entity.FinishedOrder;
import seu.qz.qzserver.service.AppCustomerService;
import seu.qz.qzserver.service.ExperimentResultService;
import seu.qz.qzserver.service.FinishedOrderService;
import seu.qz.qzserver.util.DateFormatTransUtils;
import seu.qz.qzserver.util.PDFCreate;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@Controller
public class PDFController {

    @Autowired
    public FinishedOrderService finishedOrderService;

    @Autowired
    public ExperimentResultService experimentResultService;

    @Autowired
    public AppCustomerService appCustomerService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String serverMailAddress;


    @RequestMapping("/sendPDFToEmail")
    @ResponseBody
    public String sendPDFToEmail(String orderId){
        if(orderId == null || orderId.isEmpty()){
            return "Failed";
        }
        FinishedOrder order = finishedOrderService.getOrderById(Integer.parseInt(orderId));
        if(order == null){
            return "Failed";
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
            return "Failed";
        }
        return "Success";
    }

    //对应客户端下载实验结果报告pdf文件的控制器
    @RequestMapping("/downloadPDF")
    public void getDownload(String order_id, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
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

}
