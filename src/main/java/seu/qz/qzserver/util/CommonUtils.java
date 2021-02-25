package seu.qz.qzserver.util;


import java.util.List;

public class CommonUtils {

    public static List<String> arraysTransToList(List<String> new_list, String[] list){
        if(list == null || list.length == 0){
            return null;
        }
        if(new_list == null){
            return null;
        }
        for(int i = 0; i < list.length; i++){
            new_list.add(list[i]);
        }
        return new_list;
    }


    
}
