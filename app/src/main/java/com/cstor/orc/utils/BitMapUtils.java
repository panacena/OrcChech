package com.cstor.orc.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import id.zelory.compressor.Compressor;


public class BitMapUtils {


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            return "";
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                return "";
            }
        }
        return result;
    }


    public static String bitmaptoString(Bitmap bitmap) {


        // 将Bitmap转换成字符串

        String string = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bStream);

        byte[] bytes = bStream.toByteArray();
        Base64Encoder base=new Base64Encoder();
        string = base.encode(bytes); //

        //  string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;

    }


    /**
     * 从SD卡上获取图片。如果不存在则返回null</br>
     * @return 代表图片的Bitmap对象
     */
    public static Bitmap getBitmapFromSDCard(String url) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));
            if (inputStream != null && inputStream.available() > 0) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap zoomBitMapFromPath(Context context, String path) {
        Bitmap bitmap = new Compressor.Builder(context)
                .setMaxWidth(1920)
                .setMaxHeight(1080)
                .setQuality(100)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(context.getCacheDir().getAbsolutePath())
                .build()
                .compressToBitmap(new File(path));
        return bitmap;
    }


    /**
     *
     * @param string
     * @return
     */
    public static Bitmap Base64toBitmap(String string){
        //将字符串转换成Bitmap类型
        byte[] bytes = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
