package seu.qz.qzserver;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seu.qz.qzserver.entity.ExperimentResult;
import seu.qz.qzserver.entity.FinishedOrder;
import seu.qz.qzserver.entity.LOIInstrument;
import seu.qz.qzserver.service.ExperimentResultService;
import seu.qz.qzserver.service.FinishedOrderService;
import seu.qz.qzserver.test.ChildClass;
import seu.qz.qzserver.test.ParentClass;
import seu.qz.qzserver.util.DateFormatTransUtils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class UtilsTest {

    @Autowired
    public DateFormatTransUtils utils;

    @Autowired
    public FinishedOrderService finishedOrderService;

    @Autowired
    public ExperimentResultService experimentResultService;


    @Test
    public void testDateFormat(){
       System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)(utils.CustomizeDateFromString("2012-12-23-15-23-14"))));
    }

    @Test
    public void testParentInterface(){
        String x = "15" + null;
        System.out.println(x);
    }

    @Test
    public void testDateOutput(){

//        PdfReader reader;
//        ByteArrayOutputStream bos;
//        PdfStamper stamper;
//        try {
//            String templatePath = "D:/GraduationDesign/References/pdf/LOI_template.pdf";
//            reader = new PdfReader(templatePath);// 读取pdf模板
//            bos = new ByteArrayOutputStream();
//            stamper = new PdfStamper(reader, bos);
//            AcroFields form = stamper.getAcroFields();
//            String o_cons_string = result.getO_cons_string();
//            String b_times_string = result.getB_times_string();
//            String b_lengths_string = result.getB_lengths_string();
//            String b_judge_string = result.getB_judge_string();
//            String[] cons = o_cons_string.split(";");
//            String[] times = b_times_string.split(";");
//            String[] lengths = b_lengths_string.split(";");
//            String[] results = b_judge_string.split(";");
//            int total_num = cons.length;
//            int part1_num = result.getPart1_length();
//            java.util.Iterator<String> it = form.getFields().keySet().iterator();
//            while (it.hasNext()) {
//                String name = it.next();
//                String value = null;
//                switch (name){
//                    case "material_name":
//                        value = order.getMaterialName();
//                        break;
//                    case "material_type":
//                        switch (order.getMaterialType()){
//                            case 1:
//                                value = "Ⅰ";
//                                break;
//                            case 2:
//                                value = "Ⅱ";
//                                break;
//                            case 3:
//                                value = "Ⅲ";
//                                break;
//                            case 4:
//                                value = "Ⅳ";
//                                break;
//                            case 5:
//                                value = "Ⅴ";
//                                break;
//                            default:break;
//                        }
//                        break;
//                    case "light_way":
//                        switch (result.getLight_way()){
//                            case 0:
//                                value = "顶面点燃法";
//                                break;
//                            case 1:
//                                value = "扩散点燃法";
//                            default:break;
//                        }
//                        break;
//                    case "state_adjust":
//                        value = "常规";
//                        break;
//                    case "step_length":
//                        value = String.valueOf(result.getStep_value()) + "%";
//                        break;
//                    case "loi_value":
//                        value = String.valueOf(result.getFinal_LOI());
//                        break;
//                    case "error":
//                        value = String.valueOf(result.getVariance());
//                        break;
//                    case "date":
//                        value = DateFormatTransUtils.getStringShort(order.getRentTime_end());
//                        break;
//                    case "factory_name":
//                        value = order.getFactory_name();
//                        break;
//                    case "order_id":
//                        value = result.getFactory_result_id().toString();
//                        break;
//                    case "k_value":
//                        value = String.valueOf(result.getCalculate_k());
//                        break;
//                    case "isProper":
//                        if(result.isStep_isSuitable()){
//                            value = "合适";
//                        }else {
//                            value = "不合适";
//                        }
//                        break;
//                    case "operater_name":
//                        value = order.getOperator_name();
//                        break;
//                    case "extra_explanation":
//                        value = order.getExtra_explanation();
//                        break;
//                    default:break;
//                }
//                //part1部分填入cons的[0,part1_num-1]
//                if(name.startsWith("part1")){
//                    char last_2 = name.charAt(name.length() - 2);
//                    int temp_order;
//                    if(last_2 == '1'){
//                        temp_order = Integer.parseInt(name.substring(name.length() - 2)) - 1;
//                    }else {
//                        temp_order = Integer.parseInt(name.substring(name.length() - 1)) - 1;
//                    }
//                    if(name.startsWith("part1_loi")){
//                        value = temp_order < part1_num ? cons[temp_order] : "";
//                    }else if(name.startsWith("part1_time")){
//                        value = temp_order < part1_num ? times[temp_order] : "";
//                    }else if(name.startsWith("part1_length")){
//                        value = temp_order < part1_num ? lengths[temp_order] : "";
//                    }else if(name.startsWith("part1_result")){
//                        value = temp_order < part1_num ? results[temp_order] : "";
//                    }
//                    //part2部分填入：
//                    //区间1填入：cons[part1_num, total_num - 6]
//                    //区间2填入：cons[total_num - 5, total_num - 1]
//                }else if(name.startsWith("part2")){
//                    char last_2 = name.charAt(name.length() - 2);
//                    int temp_order;
//                    if(last_2 == '1'){
//                        temp_order = Integer.parseInt(name.substring(name.length() - 2)) - 1;
//                    }else {
//                        temp_order = Integer.parseInt(name.substring(name.length() - 1)) - 1;
//                    }
//                    if(name.startsWith("part2_loi")){
//                        value = temp_order > 4 ? cons[total_num - 5 + temp_order - 5] :
//                                (temp_order < (total_num - part1_num -5) ? cons[part1_num + temp_order] : "");
//                    }else if(name.startsWith("part2_time")){
//                        value = temp_order > 4 ? times[total_num - 5 + temp_order - 5] :
//                                (temp_order < (total_num - part1_num -5) ? times[part1_num + temp_order] : "");
//                    }else if(name.startsWith("part2_length")){
//                        value = temp_order > 4 ? lengths[total_num - 5 + temp_order - 5] :
//                                (temp_order < (total_num - part1_num -5) ? lengths[part1_num + temp_order] : "");
//                    }else if(name.startsWith("part2_result")){
//                        value = temp_order > 4 ? results[total_num - 5 + temp_order - 5] :
//                                (temp_order < (total_num - part1_num -5) ? results[part1_num + temp_order] : "");
//                    }
//                }
//
//                form.setField(name,value);
//            }
//            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
//            stamper.close();
//
//            Document doc = new Document();
//            FileOutputStream fos = new FileOutputStream("D:/GraduationDesign/DownloadPDF/" + order.getOrder_id() + ".pdf");
//            PdfCopy copy = new PdfCopy(doc, fos);
//            doc.open();
//            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
//            copy.addPage(importPage);
//            doc.close();
//
//        } catch (IOException e) {
//            System.out.println(e);
//        } catch (DocumentException e) {
//            System.out.println(e);
//        }


    }


}
