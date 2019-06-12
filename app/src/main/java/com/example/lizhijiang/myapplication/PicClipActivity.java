package com.example.lizhijiang.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PicClipActivity extends AppCompatActivity {

    private ImageView iv1;
    private ImageView iv2;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_clip);

        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        btn = findViewById(R.id.btn);

        // 在api23以上（包括api23），对sd卡进行读写操作，除了要在清单文件中写明读写sd卡权限，还要在代码中动态申请权限。
        verifyStoragePermissions(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clipPic();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        verifyStoragePermissions(this);
    }

    private void clipPic(){
        String sourcePath = Environment.getExternalStorageDirectory()+File.separator+"s1.jpg";
        String outPath = Environment.getExternalStorageDirectory()+File.separator+"out4_3.jpg";


        Bitmap sourceBp = getBitmap(sourcePath);
        if(sourceBp != null){
            //允许误差在10%
            float errorRange = 0.1f;

            //1:1
            int targetWRadio = 4;
            int targetHRadio = 3;

            int sWidth = sourceBp.getWidth();
            int sHeight = sourceBp.getHeight();

            float radioT = targetWRadio * 1.0f/targetHRadio;
            float radioS = sWidth * 1.0f/sHeight;
            if(Math.abs(radioS - radioT) <= errorRange){
                //直接返回out1
                Toast.makeText(AppApplication.getContext(),"直接返回",Toast.LENGTH_SHORT).show();
                return;
            }

            if(radioS > radioT){
                //计算出新的宽度
                float newWidth = targetWRadio * sHeight * 1.0f/targetHRadio;
                float x = (sWidth - newWidth)/2;
                Bitmap targetBp = clip(sourceBp, (int)x, 0,(int)newWidth,sHeight,true);
                save(targetBp, outPath, Bitmap.CompressFormat.JPEG, true);
            }else if(sWidth == sHeight){
                //直接返回out1
                Toast.makeText(AppApplication.getContext(),"直接返回 相等",Toast.LENGTH_SHORT).show();
            }else{
                //计算新的高度
                float newHeight = targetHRadio * sWidth * 1.0f/targetWRadio;
                float y = (sHeight - newHeight)/2;
                Bitmap targetBp = clip(sourceBp, 0, (int)y,sWidth,(int)newHeight,true);
                save(targetBp, outPath, Bitmap.CompressFormat.JPEG, true);
            }
        }
    }

    private void verifyStoragePermissions(final Activity activity) {
        if ( !selfPermissionGranted(activity) ) {
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            showMessageOKCancel(this, "请申请存储权限", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    verifyStoragePermissions(activity);
                }
            });
            return;
        }
    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancleListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", cancleListener)
                .create()
                .show();

    }

    public boolean selfPermissionGranted(Context context) {

        boolean ret = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ret = PermissionChecker.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED;
        }
        return ret;
    }


    /**
     * Return the clipped bitmap.
     *
     * @param src     The source of bitmap.
     * @param x       The x coordinate of the first pixel.
     * @param y       The y coordinate of the first pixel.
     * @param width   The width.
     * @param height  The height.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the clipped bitmap
     */
    public static Bitmap clip(final Bitmap src,
                              final int x,
                              final int y,
                              final int width,
                              final int height,
                              final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = Bitmap.createBitmap(src, x, y, width, height);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    private static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * Return bitmap.
     *
     * @param filePath The path of file.
     * @return bitmap
     */
    public static Bitmap getBitmap(final String filePath) {
        if (isSpace(filePath)) return null;
        return BitmapFactory.decodeFile(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Save the bitmap.
     *
     * @param src      The source of bitmap.
     * @param filePath The path of file.
     * @param format   The format of the image.
     * @param recycle  True to recycle the source of bitmap, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format,
                               final boolean recycle) {
        return save(src, getFileByPath(filePath), format, recycle);
    }

    /**
     * Save the bitmap.
     *
     * @param src     The source of bitmap.
     * @param file    The file.
     * @param format  The format of the image.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final File file,
                               final Bitmap.CompressFormat format,
                               final boolean recycle) {
        if (isEmptyBitmap(src) || !createFileByDeleteOldFile(file)) return false;
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 100, os);
            if (recycle && !src.isRecycled()) src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) return false;
        if (file.exists() && !file.delete()) return false;
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
}
