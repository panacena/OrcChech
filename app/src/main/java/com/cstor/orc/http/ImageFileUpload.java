package com.cstor.orc.http;

import android.util.Log;

import com.lzy.imagepicker.bean.ImageItem;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016/11/22 0022.
 */
public class ImageFileUpload {

    public  static  String TAG="zkk";
    static String   BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成


    /**
     *
     * @param mOidMets 运单号
     * @param mSenderMets 姓名
     * @param mSexMets 性别
     * @param mNationMets 民族
     * @param mBirthdayMets 出生日期
     * @param mBirthplaceMets 住址
     * @param mPidMets 身份证号
     * @param mTelMets  发件人电话
     * @param mReceiverMets  收件人
     * @param mReceiverTelMets 收件人电话
     * @param mReceiverAddr 收件人地址
     */
    public static void upImage(String mOidMets,String mSenderMets,String mSexMets,String mNationMets,
                               String mBirthdayMets,String mBirthplaceMets,String mPidMets,String mTelMets
            ,String mReceiverMets, String mReceiverTelMets,String mReceiverAddr,Map<Integer,ImageItem> images, final SimpleCallBack callback) {
        RequestParams params = new RequestParams("http://app.mypm25.cn/Express/save");
                //"http://192.168.0.51:8080/Express/save");

        for (int i = 0; i < images.size(); i++) {
            params.addBodyParameter("images" + i, new File(images.get(i).path));
        }
        params.addBodyParameter("bill",mOidMets);
        params.addBodyParameter("senderName",mSenderMets);
        params.addBodyParameter("sex",mSexMets);
        params.addBodyParameter("nation",mNationMets);
        params.addBodyParameter("birthday",mBirthdayMets);
        params.addBodyParameter("addr",mBirthplaceMets);
        params.addBodyParameter("idcard",mPidMets);
        params.addBodyParameter("senderPhone",mTelMets);
        params.addBodyParameter("recieveName",mReceiverMets);
        params.addBodyParameter("recievePhone",mReceiverTelMets);
        params.addBodyParameter("revieveAddr",mReceiverAddr);

        params.addParameter("Content-Type", "multipart/form-data;boundary="+BOUNDARY);
      //  params.addBodyParameter("images0", new File(images.get(0).path));
        x.http().post(params, new Callback.ProgressCallback<String>() {
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
                Log.d(TAG,"onLoading--"+current+"---total"+total);
                callback.onLoading(total,current,isDownloading);
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
