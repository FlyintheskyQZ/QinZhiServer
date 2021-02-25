package seu.qz.qzserver.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import seu.qz.qzserver.entity.ExperimentResult;
import seu.qz.qzserver.entity.FinishedOrder;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;


/**
 * 利用Itext，依据FinishedOrder和ExperimentResult创建PDF实验报告
 */
public class PDFCreate {

    public static void createPDF(FinishedOrder order, ExperimentResult result){

        PdfReader reader = null;
        AcroFields s = null;
        PdfStamper ps = null;
        ByteArrayOutputStream bos = null;

        //模板文件
        String templatePath = "D:/GraduationDesign/References/pdf/LOI_template.pdf";
        try {
            String file = templatePath;
            //字体文件
            String font = "D:/GraduationDesign/References/fonts/Microsoft-YaHei.ttf";
            reader = new PdfReader(file);
            bos = new ByteArrayOutputStream();
            ps = new PdfStamper(reader, bos);
            s = ps.getAcroFields();
            //使用中文字体 使用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体 Adobe 宋体 std L
            BaseFont bfChinese = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //设置编码格式
            s.addSubstitutionFont(bfChinese);
            s = ps.getAcroFields();
            String o_cons_string = result.getO_cons_string();
            String b_times_string = result.getB_times_string();
            String b_lengths_string = result.getB_lengths_string();
            String b_judge_string = result.getB_judge_string();
            String[] cons = o_cons_string.split(";");
            String[] times = b_times_string.split(";");
            String[] lengths = b_lengths_string.split(";");
            String[] results = b_judge_string.split(";");
            int total_num = cons.length;
            int part1_num = result.getPart1_length();
            //获取PDF模板文件的待填文本域，根据域名填充对应的实验结果数据
            Iterator<String> it = s.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                String value = null;
                switch (name){
                    case "material_name":
                        value = order.getMaterialName();
                        break;
                    case "material_type":
                        switch (order.getMaterialType()){
                            case 1:
                                value = "Ⅰ";
                                break;
                            case 2:
                                value = "Ⅱ";
                                break;
                            case 3:
                                value = "Ⅲ";
                                break;
                            case 4:
                                value = "Ⅳ";
                                break;
                            case 5:
                                value = "Ⅴ";
                                break;
                            default:break;
                        }
                        break;
                    case "light_way":
                        switch (result.getLight_way()){
                            case 0:
                                value = "顶面点燃法";
                                break;
                            case 1:
                                value = "扩散点燃法";
                            default:break;
                        }
                        break;
                    case "state_adjust":
                        value = "常规";
                        break;
                    case "step_length":
                        value = String.valueOf(result.getStep_value()) + "%";
                        break;
                    case "loi_value":
                        value = String.valueOf(result.getFinal_LOI());
                        break;
                    case "error":
                        value = String.valueOf(result.getVariance());
                        break;
                    case "date":
                        value = DateFormatTransUtils.getStringShort(order.getRentTime_end());
                        break;
                    case "factory_name":
                        value = order.getFactory_name();
                        break;
                    case "order_id":
                        value = result.getFactory_result_id().toString();
                        break;
                    case "k_value":
                        value = String.valueOf(result.getCalculate_k());
                        break;
                    case "isProper":
                        if(result.isStep_isSuitable()){
                            value = "合适";
                        }else {
                            value = "不合适";
                        }
                        break;
                    case "operater_name":
                        value = order.getOperator_name();
                        break;
                    case "extra_explanation":
                        value = order.getExtra_explanation();
                        break;
                    default:break;
                }
                //part1部分填入cons的[0,part1_num-1]
                if(name.startsWith("part1")){
                    char last_2 = name.charAt(name.length() - 2);
                    int temp_order;
                    if(last_2 == '1'){
                        temp_order = Integer.parseInt(name.substring(name.length() - 2)) - 1;
                    }else {
                        temp_order = Integer.parseInt(name.substring(name.length() - 1)) - 1;
                    }
                    if(name.startsWith("part1_loi")){
                        value = temp_order < part1_num ? cons[temp_order] : "";
                    }else if(name.startsWith("part1_time")){
                        value = temp_order < part1_num ? times[temp_order] : "";
                    }else if(name.startsWith("part1_length")){
                        value = temp_order < part1_num ? lengths[temp_order] : "";
                    }else if(name.startsWith("part1_result")){
                        value = temp_order < part1_num ? results[temp_order] : "";
                    }
                    //part2部分填入：
                    //区间1填入：cons[part1_num, total_num - 6]
                    //区间2填入：cons[total_num - 5, total_num - 1]
                }else if(name.startsWith("part2")){
                    char last_2 = name.charAt(name.length() - 2);
                    int temp_order;
                    if(last_2 == '1'){
                        temp_order = Integer.parseInt(name.substring(name.length() - 2)) - 1;
                    }else {
                        temp_order = Integer.parseInt(name.substring(name.length() - 1)) - 1;
                    }
                    if(name.startsWith("part2_loi")){
                        value = temp_order > 4 ? cons[total_num - 5 + temp_order - 5] :
                                (temp_order < (total_num - part1_num -5) ? cons[part1_num + temp_order] : "");
                    }else if(name.startsWith("part2_time")){
                        value = temp_order > 4 ? times[total_num - 5 + temp_order - 5] :
                                (temp_order < (total_num - part1_num -5) ? times[part1_num + temp_order] : "");
                    }else if(name.startsWith("part2_length")){
                        value = temp_order > 4 ? lengths[total_num - 5 + temp_order - 5] :
                                (temp_order < (total_num - part1_num -5) ? lengths[part1_num + temp_order] : "");
                    }else if(name.startsWith("part2_result")){
                        value = temp_order > 4 ? results[total_num - 5 + temp_order - 5] :
                                (temp_order < (total_num - part1_num -5) ? results[part1_num + temp_order] : "");
                    }
                }

                s.setField(name,value);
            }

            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            ps.setFormFlattening(true);
            ps.close();
            String savePath = "D:/GraduationDesign/DownloadPDF/" + order.getOrder_id() + ".pdf";
            FileOutputStream fos = new FileOutputStream(savePath);

            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
