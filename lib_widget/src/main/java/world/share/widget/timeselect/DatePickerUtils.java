package world.share.widget.timeselect;

import android.app.Activity;
import android.graphics.Color;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;

/**
 * 辅助DateTimePicker，简化代码
 *
 * @author mac*/

public class DatePickerUtils {

    public static DateTimePicker produce(Activity activity) {
        DateTimePicker dateTimePicker = new DatePicker(activity);
        //时间选择框开始结束范围
        dateTimePicker.setDateRangeStart(CalendarUtils.getCurentYear(), CalendarUtils.getCurentMonth(), 1);
        dateTimePicker.setDateRangeEnd(CalendarUtils.getCurentYear() + 50, 12, 31);
        dateTimePicker.setTopLineColor(Color.argb(255, 153, 153, 153));
        dateTimePicker.setLabelTextColor(Color.argb(255, 102, 102, 102));
        dateTimePicker.setDividerColor(Color.argb(255, 153, 153, 153));
        dateTimePicker.setTextColor(Color.argb(255, 102, 102, 102));
        dateTimePicker.setCancelTextColor(Color.argb(255, 102, 102, 102));
        dateTimePicker.setSubmitTextColor(Color.argb(255, 102, 102, 102));
        dateTimePicker.setSelectedItem(CalendarUtils.getCurentYear(), CalendarUtils.getCurentMonth(), CalendarUtils.getCurentDay(), 0, 0);
        dateTimePicker.setCancelText("取消");
        dateTimePicker.setSubmitText("确定");
        return dateTimePicker;
    }

}
