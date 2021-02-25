package seu.qz.qzserver.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringAdjustUtils {


    /**
     * 用于将original_orders中的cut截取掉
     * @param original_orders：形如“a;b;c"
     * @param cut：形如"a"
     * @return  "b;c"
     */
    public static String interceptOrderStrings(String original_orders, String cut){
        if(original_orders == null || original_orders.isEmpty() || cut == null || cut.isEmpty()){
            return original_orders;
        }
        String[] orders = original_orders.split(";");
        if(orders.length == 1 && orders[0].equals(cut)){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < orders.length; i++){
            if(!orders[i].equals(cut)){
                builder.append(orders[i] + ";");
            }
        }
        String new_orders = builder.toString();
        new_orders = new_orders.substring(0, new_orders.length() - 1);
        return new_orders;
    }


    /**
     * 给original_orders添加add,并进行排序
     * @param original_orders：形如"a;b;c"
     * @param add：形如"d"
     * @return"a;b;c;d"
     */
    public static String appendOrderStrings(String original_orders, String add){
        if(add == null || add.isEmpty()){
            return original_orders;
        }
        original_orders = original_orders.trim();
        if(original_orders == null || original_orders.isEmpty()){
            return add;
        }
        String[] orders = original_orders.split(";");
        List<String> order_list = new ArrayList<>();
        order_list.add(add);
        CommonUtils.arraysTransToList(order_list, orders);
        OrderComparatorUtils.adjustOrderStrings(order_list);
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < order_list.size(); i++){
            builder.append(order_list.get(i) + ";");
        }
        String new_orders = builder.toString();
        new_orders = new_orders.substring(0, new_orders.length() - 1);
        return new_orders;


    }
}
