package seu.qz.qzserver.util;

import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateFormatTransUtils {

    public Calendar calendar;
    public static final int TYPE_ORIGINAL = 0;
    public static final int TYPE_CHINESE = 1;
    public static final int TYPE_SHORT = 2;
    private static SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat ChineseFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");

    public DateFormatTransUtils() {
        this.calendar = Calendar.getInstance();
    }

    /**
     * 适用于类似”2020-11-3-12-15-23“，代表2020年11月3日12点15分23秒
     * @param time
     * @return
     */
    public Date CustomizeDateFromString(String time){
        String[] times = time.split("-");
        int[] temps = new int[6];
        for(int i = 0 ; i < 6; i++){
            if(i < times.length) {
                temps[i] = Integer.parseInt(times[i]);
            }else {
                temps[i] = 0;
            }
        }
        calendar.set(temps[0], temps[1], temps[2], temps[3], temps[4], temps[5]);
        return calendar.getTime();
    }

    public static Date formDateByString(String time, int Type){
        if(time == null || time.isEmpty()){
            return new Date(System.currentTimeMillis());
        }
        Date date = null;
        try{
            switch (Type){
                case TYPE_ORIGINAL:
                    date = originalDateFormat.parse(time);
                    break;
                case TYPE_CHINESE:
                    date = ChineseFormat.parse(time);
                    break;
                case TYPE_SHORT:
                    date = shortDateFormat.parse(time);
                default:break;
            }
            return date;
        }catch (Exception e){
            e.printStackTrace();
            return new Date(System.currentTimeMillis());
        }
    }

    public static String getStringShort(Date date){
        return shortDateFormat.format(date);
    }

    public static String getStringChinese(Date date){
        return ChineseFormat.format(date);
    }
}
