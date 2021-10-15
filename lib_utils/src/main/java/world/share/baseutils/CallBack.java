package world.share.baseutils;

/**
 * 通用回调
 * @author mac
 */
public interface CallBack<T> {
    T call(T... t);
}
