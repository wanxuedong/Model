package world.share.baseutils;

import android.text.TextUtils;

import com.tencent.mmkv.MMKV;

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：Mmkv存储方案
 */
public class MvHelper {

    private static MMKV mv = MMKV.defaultMMKV();

    /**
     * 存储字符串
     *
     * @param key   键
     * @param value 值
     **/
    public static void encode(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            mv.encode(key, value);
        }
    }

    /**
     * 存储数字
     *
     * @param key   键
     * @param value 值
     **/
    public static void encode(String key, int value) {
        if (!TextUtils.isEmpty(key)) {
            mv.encode(key, value);
        }
    }

    /**
     * 存储boolean
     *
     * @param key   键
     * @param value 值
     **/
    public static void encode(String key, Boolean value) {
        if (!TextUtils.isEmpty(key)) {
            mv.encode(key, value);
        }
    }

    /**
     * 获取字符串
     *
     * @param key 键
     **/
    public static String decodeString(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalStateException();
        }
        return mv.decodeString(key);
    }

    /**
     * 获取数字
     *
     * @param key 键
     **/
    public static int decodeInt(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalStateException();
        }
        return mv.decodeInt(key);
    }

    /**
     * 获取Boolean
     *
     * @param key 键
     **/
    public static Boolean decodeBoolean(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalStateException();
        }
        return mv.decodeBool(key);
    }

}
