package world.share.logger;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import world.share.baseutils.BaseUtil;
import world.share.baseutils.threadutil.LifeRunnable;
import world.share.baseutils.threadutil.ThreadX;

import static world.share.logger.LogConfig.DELETE_INTERVER;
import static world.share.logger.LogConfig.NORMAL_LOG_NAME;


/**
 * 将Log信息写入本地文件中的工具类,用户不需要主动调用，配合LogUtil使用即可
 *
 * @author mac
 */

public class LogToFile extends BaseUtil {

    /**
     * 设置log文件的名称，由年月日组成
     **/
    private static DateFormat formatterFile = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 设置每一行log信息的前置信息，由年月日，时分秒组成
     **/
    private static DateFormat formatterLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 外面调用该类即可将日志写入本地文件
     **/
    public static boolean write(String title, String value, String folderName) {
        if (isNullOrEmpty(title) || isNullOrEmpty(value)) {
            return false;
        }
        String time = formatterLog.format(new Date());
        byte[] content = ("[" + time + " : " + title + "]" + value + "\r\n").getBytes(Charset.forName("UTF-8"));
        writeLog(content, folderName, formatterFile.format(new Date()) + ".txt");
        return true;
    }

    /**
     * 开启线程将日志信息写入本地文件(在频繁调用日志的时候，会开启大量线程，是一个优化点)
     **/
    private static void writeLog(final byte[] content, final String folderName, final String fileName) {
        ThreadX.getInstance().execute(new LifeRunnable() {
            @Override
            public void running() {
                createFile(folderName, fileName);
                File file = new File(folderName, fileName);
                try {
                    if (!file.exists()) {
                        boolean isCreate = file.createNewFile();
                        if (!isCreate) {
                            LogUtil.e("LogToFile", "Method writeLog create File fail!");
                        }
                    }
                    RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
                    long fileLength = randomFile.length();
                    randomFile.seek(fileLength);
                    randomFile.write(content);
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建log本地文件，并且会检查log文件创建时间是否已经超出设置时间，超时删除
     **/
    private static void createFile(String filePath, String fileName) {
        DateFormat formatterCopy = new SimpleDateFormat("yyyy-MM-dd");
        String appLogTime = SpUtil.get("appLogTime", "");
        if (appLogTime.equals("")) {
            SpUtil.put("appLogTime", formatterCopy.format(new Date()));
        } else {
            int timeDifference = DateUtils.getTimeDifference(formatterCopy.format(new Date()), appLogTime);
            if (timeDifference > DELETE_INTERVER) {
                if (NORMAL_LOG_NAME.equals(filePath)) {
                    LogFileUtil.delFolder(filePath);
                    SpUtil.put("appLogTime", formatterCopy.format(new Date()));
                } else {
                    LogFileUtil.delFolder(filePath);
                    SpUtil.put("appLogTime", formatterCopy.format(new Date()));
                }
            }

            //如果父路径不存在
            File file = new File(filePath);
            if (!file.exists()) {
                boolean isCreate = file.mkdirs();//创建父路径
                if (!isCreate) {
                    LogUtil.e("LogToFile", "Method createFile create FileFolder fail!");
                }
            }

            FileOutputStream fos;//FileOutputStream会自动调用底层的close()方法，不用关闭
            BufferedWriter bw = null;
            try {
                fos = new FileOutputStream(filePath + fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
                bw = new BufferedWriter(new OutputStreamWriter(fos, Charset.forName("UTF-8")));

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();//关闭缓冲流
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public static boolean isNullOrEmpty(String content) {
        if (content == null || content.length() == 0) {
            return true;
        }
        return false;
    }

}
