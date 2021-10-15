package world.share.baseutils.fileutils.filecopy;

/**
 * @author wan
 * 创建日期：2021/10/12
 * 描述：文件复制进度监听
 */

public interface CopyProgress {
    /**
     * @param max      文件整体大小
     * @param current  当前复制大小
     * @param progress 当前复制进度
     **/
    void progress(long max, long current, String progress);
}
