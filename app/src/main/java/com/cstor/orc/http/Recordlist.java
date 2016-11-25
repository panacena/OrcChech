package com.cstor.orc.http;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/11/22 0022.
 */
public class Recordlist {

    public  static  String TAG="zkk";


    public static void getRecordlist(final SimpleCallBack callback) {
        RequestParams params = new RequestParams("http://192.168.0.51:8080/Express/recordlist");

        x.http().get(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onWaiting() {
                Log.d(TAG,"onWaiting--");
            }

            @Override
            public void onStarted() {
                Log.d(TAG,"onStarted--");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
            }

            @Override
            public void onSuccess(String result) {
                Log.d(TAG,"onLoading--");
                callback.Succ(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG,"onError--"+ex);
                callback.error();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.d(TAG,"onFinished--");
            }
        });
    }

    public interface SimpleCallBack {
        void Succ(String result);

        void error();

         void onStarted();

         void onLoading(long total, long current, boolean isDownloading);
    }
}
