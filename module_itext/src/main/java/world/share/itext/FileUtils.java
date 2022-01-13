package world.share.itext;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by k2 on 30/6/17.
 */

public class FileUtils {

    private static final String extensions[] = new String[]{"avi", "3gp", "mp4", "mp3", "jpeg", "jpg",
            "gif", "png",
            "pdf", "docx", "doc", "xls", "xlsx", "csv", "ppt", "pptx",
            "txt", "zip", "rar"};


    public static void openFile(Context context, File url) throws ActivityNotFoundException,
            IOException {
        // Create URI
        //Uri uri = Uri.fromFile(url);

        if (url.exists()) {
            //TODO you want to use this method then create file provider in androidmanifest.xml with fileprovider name

            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", url);

            String urlString = url.toString().toLowerCase();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            /**
             * Security
             */
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            // Check what kind of file you are trying to open, by comparing the url with extensions.
            // When the if condition is matched, plugin sets the correct intent (mime) type,
            // so Android knew what application to use to open the file
            if (urlString.toLowerCase().toLowerCase().contains(".doc")
                    || urlString.toLowerCase().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (urlString.toLowerCase().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (urlString.toLowerCase().contains(".ppt")
                    || urlString.toLowerCase().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (urlString.toLowerCase().contains(".xls")
                    || urlString.toLowerCase().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (urlString.toLowerCase().contains(".zip")
                    || urlString.toLowerCase().contains(".rar")) {
                // ZIP file
                intent.setDataAndType(uri, "application/trap");
            } else if (urlString.toLowerCase().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (urlString.toLowerCase().contains(".wav")
                    || urlString.toLowerCase().contains(".mp3")) {
                // WAV/MP3 audio file
                intent.setDataAndType(uri, "audio/*");
            } else if (urlString.toLowerCase().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (urlString.toLowerCase().contains(".jpg")
                    || urlString.toLowerCase().contains(".jpeg")
                    || urlString.toLowerCase().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (urlString.toLowerCase().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (urlString.toLowerCase().contains(".3gp")
                    || urlString.toLowerCase().contains(".mpg")
                    || urlString.toLowerCase().contains(".mpeg")
                    || urlString.toLowerCase().contains(".mpe")
                    || urlString.toLowerCase().contains(".mp4")
                    || urlString.toLowerCase().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                // if you want you can also define the intent type for any other file

                // additionally use else clause below, to manage other unknown extensions
                // in this case, Android will show all applications installed on the device
                // so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "File doesn't exists", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get Path of App which contains Files
     *
     * @return path of root dir
     */
    public static String getAppPath(Context context) {
        String pathName;
        File dir = new File(android.os.Environment.getExternalStorageDirectory()
                + File.separator
                + context.getResources().getString(R.string.app_name)
                + File.separator);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath() + File.separator;
    }

    /***
     * Copy File
     *
     * @param src
     * @param dst
     * @throws IOException
     */
    public static void copy(File src, File dst) {
        InputStream in;
        OutputStream out;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);

            String tempExt = FileUtils.getExtension(dst.getPath());

            if (tempExt.equals("jpeg") || tempExt.equals("jpg") || tempExt.equals("gif")
                    || tempExt.equals("png")) {
                if (out != null) {

                    Bitmap bit = BitmapFactory.decodeFile(src.getPath());

                    if (bit.getWidth() > 700) {
                        if (bit.getHeight() > 700) {
                            bit = Bitmap.createScaledBitmap(bit, 700, 700, true);
                        } else {
                            bit = Bitmap.createScaledBitmap(bit, 700, bit.getHeight(), true);
                        }
                    } else {
                        if (bit.getHeight() > 700) {
                            bit = Bitmap.createScaledBitmap(bit, bit.getWidth(), 700, true);
                        } else {
                            bit = Bitmap.createScaledBitmap(bit, bit.getWidth(), bit.getHeight(), true);
                        }
                    }

                    bit.compress(Bitmap.CompressFormat.JPEG, 90, out);
                }
            } else {

                // Transfer bytes from in to out
                byte[] buf = new byte[1024 * 4];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }

            in.close();
            out.close();

        } catch (FileNotFoundException e) {
            Log.e("Compressing :", e.getLocalizedMessage());
        } catch (IOException e) {
            Log.e("Compressing IOE:", e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("Compressing Other:", e.getLocalizedMessage());
        }
    }

    /***
     * Move File
     *
     * @param src
     * @param dst
     * @throws IOException
     */
    public static void move(File src, File dst) {
        InputStream in;
        OutputStream out;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);

            String tempExt = FileUtils.getExtension(dst.getPath());

            if (tempExt.equals("jpeg") || tempExt.equals("jpg") || tempExt.equals("gif")
                    || tempExt.equals("png")) {
                if (out != null) {

                    Bitmap bit = BitmapFactory.decodeFile(src.getPath());

                    if (bit.getWidth() > 700 || bit.getHeight() > 700) {
                        bit = Bitmap.createScaledBitmap(bit, 700, 700, true);
                    }
                    bit.compress(Bitmap.CompressFormat.JPEG, 90, out);
                }
            } else {

                // Transfer bytes from in to out
                byte[] buf = new byte[1024 * 4];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }

            in.close();
            out.close();

            /**
             * Delete File from Source folder...
             */
            if (src.delete()) {
            }

        }  catch (FileNotFoundException e) {
            Log.e("Compressing :", e.getLocalizedMessage());
        } catch (IOException e) {
            Log.e("Compressing IOE:", e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("Compressing Other:", e.getLocalizedMessage());
        }
    }

    /**
     * Is Valid Extension
     *
     * @param ext
     * @return
     */
    public static boolean isValidExtension(String ext) {
        return Arrays.asList(extensions).contains(ext);

    }

    /**
     * Return Extension of given path without dot(.)
     *
     * @param path
     * @return
     */
    public static String getExtension(String path) {
        return path.contains(".") ? path.substring(path.lastIndexOf(".") + 1).toLowerCase() : "";
    }
}
