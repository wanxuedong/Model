package world.share.baseutils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.HashMap;

/**
 * 用于控制PopupWindow显示隐藏工具类
 * 注意点：不使用的时候务必使用remove去除引用
 **/

public class PopupUtil {

    private static HashMap<String, PopupHolder> holderHashMap = new HashMap<>();

    /**
     * 显示弹框,默认显示在父布局下方
     **/
    public static PopupHolder show(Context context, String tag, int layout, View presentView) {
        PopupHolder popupHolder;
        if (holderHashMap.get(tag) == null) {
            View popView = LayoutInflater.from(context).inflate(layout, null);
            PopupWindow popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setContentView(popView);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupHolder = new PopupHolder();
            popupHolder.setPopupWindow(popupWindow);
            popupHolder.setPopView(popView);
            popupHolder.setPresentView(presentView);
            holderHashMap.put(tag, popupHolder);
            popupWindow.showAsDropDown(presentView);
        } else {
            popupHolder = holderHashMap.get(tag);
            PopupWindow popupWindow = null;
            if (popupHolder != null){
                popupWindow = popupHolder.getPopupWindow();
            }
            if (popupWindow != null) {
                popupWindow.showAsDropDown(popupHolder.getPresentView());
            }
        }
        return popupHolder;
    }

    /**
     * 隐藏弹框
     **/
    public static void dismiss(String tag) {
        if (holderHashMap.get(tag) == null) {
            return;
        }
        PopupHolder popupHolder = holderHashMap.get(tag);
        if (popupHolder == null) {
            return;
        }
        PopupWindow popupWindow = popupHolder.getPopupWindow();
        if (popupWindow == null) {
            return;
        }
        popupWindow.dismiss();
    }

    /**
     * 移除弹框
     **/
    public static void remove(String tag) {
        if (holderHashMap.get(tag) == null) {
            return;
        }
        holderHashMap.remove(tag);
    }

    public static class PopupHolder {

        private PopupWindow popupWindow;
        private View popView;
        private View presentView;

        public PopupWindow getPopupWindow() {
            return popupWindow;
        }

        public void setPopupWindow(PopupWindow popupWindow) {
            this.popupWindow = popupWindow;
        }

        public View getPopView() {
            return popView;
        }

        public void setPopView(View popView) {
            this.popView = popView;
        }

        public View getPresentView() {
            return presentView;
        }

        public void setPresentView(View presentView) {
            this.presentView = presentView;
        }

        public void show() {
            popupWindow.showAsDropDown(presentView);
        }

        public void dismiss() {
            popupWindow.dismiss();
        }

    }

}
