package com.guohang.library.util;

import android.view.Gravity;
import android.widget.Toast;

import com.guohang.library.base.BaseApp;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ToastUtil {
    private static Toast toast = null;
    public static void show(CharSequence text) {
        show(text , Toast.LENGTH_SHORT);
    }

    public static void show(int resId) {
        show(BaseApp.mInstance.getText(resId) , Toast.LENGTH_SHORT);
    }

    public static void show(int resId , int duration) {
        show(BaseApp.mInstance.getText(resId) , duration);
    }

    public static void show(CharSequence text , int duration) {
        if (null == toast) {
            toast = Toast.makeText(BaseApp.mInstance, text, duration);
            toast.setGravity(Gravity.CENTER , 0 , 0);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
