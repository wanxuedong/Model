package world.share.widget.timeselect;

/**
 * 日期选择临时存储类
 *
 * @author mac*/

public class TimeHolder {

    int startYear = CalendarUtils.getCurentYear();
    int startMonth = CalendarUtils.getCurentMonth();
    int startDay = 1;
    int startHour = 1;
    int startMinute = 1;
    int endYear = CalendarUtils.getCurentYear();
    int endMonth = CalendarUtils.getCurentMonth();
    int endDay = Integer.parseInt(CalendarUtils.getCurrentDay());
    int endHour = 0;
    int endMinute = 0;

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public String getHeadTime() {
        return "" + startYear + startMonth + startDay + startHour + startMinute;
    }

    public String getEndTime() {
        return "" + endYear + endMonth + endDay + endHour + endMinute;
    }

    public boolean isHeadNone() {
        if (startYear == 0 && startMonth == 0 && startDay == 0 && startHour == 0 && startMinute == 0) {
            return true;
        }
        return false;
    }

    public boolean isEndNone() {
        if (endYear == 0 && endMonth == 0 && endDay == 0 && endHour == 0 && endMinute == 0) {
            return true;
        }
        return false;
    }

    public void reset() {
        int startYear = 0;
        int startMonth = 0;
        int startDay = 0;
        int startHour = 0;
        int startMinute = 0;
        int endYear = 0;
        int endMonth = 0;
        int endDay = 0;
        int endHour = 0;
        int endMinute = 0;
    }

}
