package com.cstor.orc.http;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/11/22 0022.
 */
public class RecordItem {

    public  static  String TAG="zkk";


    public static void getRecordItem(String bills,final SimpleCallBack callback) {
        RequestParams params = new RequestParams("http://app.mypm25.cn/Express/detail");
        params.addBodyParameter("bill",bills);
        x.http().get(params, new  Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callback.Succ(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                callback.error();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

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
