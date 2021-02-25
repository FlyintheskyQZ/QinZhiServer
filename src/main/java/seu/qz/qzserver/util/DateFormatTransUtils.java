package seu.qz.qzserver.util;

import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateFormatTransUtils {

    public Calendar calendar;
    private static SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
    private static SimpleDateFormat ChineseFormat = new SimpleDateFormat("yyyy年MM月dd日hh时mm分");

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

    public static String getStringShort(Date date){
        return shortDateFormat.format(date);
    }

    public static String getStringChinese(Date date){
        return ChineseFormat.format(date);
    }
}
