package seu.qz.qzserver.util;

import seu.qz.qzserver.entity.BriefOrderItem;
import seu.qz.qzserver.entity.FinishedOrder;
import seu.qz.qzserver.entity.LOIInstrument;
import seu.qz.qzserver.entity.ProvideOrder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 用于对entity对象集合进行顺序调整的工具类
 */
public class OrderComparatorUtils {

    public static void adjustOrderFromProvideForUser(List<ProvideOrder> orders){
        Collections.sort(orders, new Comparator<ProvideOrder>() {
            //若返回值为1，则o1排在后面!!!!!!!!!!
            @Override
            public int compare(ProvideOrder o1, ProvideOrder o2) {
                if(o1.getOrderForRelatedUser() > o2.getOrderForRelatedUser()){
                    return -1;
                }else {
                    return 1;
                }
            }
        });
    }

    public static void adjustOrderFromProvideForSaler(List<ProvideOrder> orders){
        Collections.sort(orders, new Comparator<ProvideOrder>() {
            @Override
            public int compare(ProvideOrder o1, ProvideOrder o2) {
                if(o1.getOrderForRelatedSaler() > o2.getOrderForRelatedSaler()){
                    return -1;
                }else {
                    return 1;
                }
            }
        });
    }

    public static void adjustOrderFromFinishedForUser(List<FinishedOrder> orders) {
        Collections.sort(orders, new Comparator<FinishedOrder>() {
            @Override
            public int compare(FinishedOrder o1, FinishedOrder o2) {
                if(o1.getOrderForRelatedUser() > o2.getOrderForRelatedUser()){
                    return -1;
                }else {
                    return 1;
                }
            }
        });
    }

    public static void adjustOrderFromFinishedForSaler(List<FinishedOrder> orders) {
        Collections.sort(orders, new Comparator<FinishedOrder>() {
            @Override
            public int compare(FinishedOrder o1, FinishedOrder o2) {
                if(o1.getOrderForRelatedSaler() > o2.getOrderForRelatedSaler()){
                    return -1;
                }else {
                    return 1;
                }
            }
        });
    }

    public static void adjustInstrumentsByRelatedOrder(List<LOIInstrument> instruments) {
        Collections.sort(instruments, new Comparator<LOIInstrument>() {
            @Override
            public int compare(LOIInstrument o1, LOIInstrument o2) {
                if(o1.getOrderForRelatedSaler() > o2.getOrderForRelatedSaler()){
                    return 1;
                }else {
                    return -1;
                }
            }
        });
    }

    public static void adjustOrderStrings(List<String> orders) {
        Collections.sort(orders, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(Integer.parseInt(o1) > Integer.parseInt(o2)){
                    return 1;
                }else {
                    return -1;
                }
            }
        });
    }
}
