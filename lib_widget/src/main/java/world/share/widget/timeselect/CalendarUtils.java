package world.share.widget.timeselect;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 关于日期处理的工具
 */

public class CalendarUtils {

    private static Calendar calendar = Calendar.getInstance();
    private static long time = System.currentTimeMillis();

    //获取当前的年月
    public static String getCurentData() {
        calendar.setTimeInMillis(time);
        return "" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH);
    }

    //获取当前的年
    public static int getCurentYear() {
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.YEAR);
    }

    //获取当前的月
    public static int getCurentMonth() {
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH) + 1;
    }

    //获取当前的日
    public static int getCurentDay() {
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DATE);
    }

    //获取根据年，月所在的天数
    public static int getDayByMonth(int year, int month) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxDate = calendar.get(Calendar.DATE);
        return maxDate;
    }

    //获取当前时间
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获取当前时间
    public static String getCurrent() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    //获取当前的天数
    public static String getCurrentDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
