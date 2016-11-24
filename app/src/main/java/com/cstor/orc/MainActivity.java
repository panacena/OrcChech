package com.cstor.orc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cstor.orc.common.LoadingDialog;
import com.cstor.orc.http.ImageFileUpload;
import com.cstor.orc.http.TecentHttpUtil;
import com.cstor.orc.myorcslist.activity.OrcListsActivity;
import com.cstor.orc.utils.BitMapUtils;
import com.cstor.orc.utils.StringUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG="zkk---";
    /**
     * 扫描二维码跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    /**
     * 扫描身份证跳转Activity RequestCode
     */
    public static  final int  IMAGE_PICKER= 101;

    /**
     * 拍摄货物Activity RequestCode
     */
    public static  final int IMAGE_GOODS_PICKER = 102;


    /**
     * 拍摄单子跳转Activity RequestCode
     */
    public static  final int IMAGE_ORDERS_PICKER = 103;

    /**
     * 扫描二维码
     */
    private ImageView iv_scan;

    /**
     * 扫描身份证
     */
    private  ImageView mCameraPidIv;

    /**
     * 运单号
     */
    private EditText mOidMet;


    /**
     * 姓名
     */
    private EditText mSenderMet;

    /**
     * 性别
     */
    private EditText mSexMet;
    /**
     * 民族
     */
    private EditText mNationMet;
    /**
     * 出生日期
     */
    private EditText mBirthdayMet;

    /**
     * 住址
     */
    private EditText mBirthplaceMet;

    /**
     * 身份证号
     */
    private EditText mPidMet;


    /**
     * 发件人电话
     */
    private EditText mTelMet;


    /**
     * 收件人
     */
    private  EditText mReceiverMet;


    /**
     * 收件人电话
     */
    private  EditText mReceiverTelMet;


    /**
     * 收件人地址
     */
    private  EditText et_mReceiverAddr;

    /**
     * 头像
     */
    private  ImageView mAvatarIv;


    /**
     * 拍摄货物
     */
    private  ImageView mCameraGoodsIv;


    /**
     * 拍摄货运单
     */
    private  ImageView mCameraOrderIv;

    private Button mSubmitBtn;

    private Context mContext;


    private TextView toolbar_right;
    Map<Integer,ImageItem> imagesall=new HashMap<Integer,ImageItem>();

    DecimalFormat df = new DecimalFormat("#.0");

    private LoadingDialog mLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_order);
        init();
    }

    void init(){
        mContext=this;
        mLoadingDialog=new LoadingDialog(this);
     //   mLoadingDialog.setCanceledOnTouchOutside(true);
        iv_scan=(ImageView)findViewById(R.id.iv_scan);
        iv_scan.setOnClickListener(this);
        mCameraPidIv=(ImageView)findViewById(R.id.mCameraPidIv);
        mCameraPidIv.setOnClickListener(this);
        mAvatarIv=(ImageView)findViewById(R.id.mAvatarIv);


        mCameraGoodsIv=(ImageView)findViewById(R.id.mCameraGoodsIv);
        mCameraOrderIv=(ImageView)findViewById(R.id.mCameraOrderIv);
        mCameraGoodsIv.setOnClickListener(this);
        mCameraOrderIv.setOnClickListener(this);


        mOidMet=(EditText)findViewById(R.id.mOidMet);
        mSenderMet=(EditText)findViewById(R.id.mSenderMet);
        mSexMet=(EditText)findViewById(R.id.mSexMet);
        mNationMet=(EditText)findViewById(R.id.mNationMet);
        mBirthdayMet=(EditText)findViewById(R.id.mBirthdayMet);
        mBirthplaceMet=(EditText)findViewById(R.id.mBirthplaceMet);
        mPidMet=(EditText)findViewById(R.id.mPidMet);


        mTelMet=(EditText)findViewById(R.id.mTelMet);
        mReceiverMet=(EditText)findViewById(R.id.mReceiverMet);
        mReceiverTelMet=(EditText)findViewById(R.id.mReceiverTelMet);
        et_mReceiverAddr=(EditText)findViewById(R.id.et_mReceiverAddr);


        mSubmitBtn=(Button)findViewById(R.id.mSubmitBtn);
        mSubmitBtn.setOnClickListener(this);

        toolbar_right=(TextView)findViewById(R.id.toolbar_right);
        toolbar_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_scan:
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.mCameraPidIv:
                Intent intent2 = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent2, IMAGE_PICKER);
                break;
            case R.id.mSubmitBtn:
                checkInfo();
                break;
            case R.id.mCameraGoodsIv:
                Intent intent3 = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent3, IMAGE_GOODS_PICKER);
                break;
            case R.id.mCameraOrderIv:
                Intent intent4 = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent4, IMAGE_ORDERS_PICKER);
                break;
            case R.id.toolbar_right:
                Intent intent5 = new Intent(mContext, OrcListsActivity.class);
                startActivity(intent5);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    mOidMet.setText(result);
                   // Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }else  if (data != null && requestCode == IMAGE_PICKER) {
                mLoadingDialog.setText("身份证识别中....");
                mLoadingDialog.show();

                if(data.hasExtra(ImagePicker.EXTRA_RESULT_ITEMS)){
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    imagesall.put(0,images.get(0));
                    mCameraPidIv.setSelected(true);
                    if (images != null && images.size() == 1) {
                        Log.d(TAG,images.get(0)+"---");
                        Bitmap bitmaps = BitMapUtils.zoomBitMapFromPath(this, images.get(0).path);
                        TecentHttpUtil.uploadIdCard(BitMapUtils.bitmaptoString(bitmaps), "0", new TecentHttpUtil.SimpleCallBack() {
                            @Override
                            public void Succ(String result) {
                                orcCheckSuccess(result);
                            }
                            @Override
                            public void error() {
                                orcCheckError();
                            }
                        });
                    }
                }
            } else if(requestCode == IMAGE_GOODS_PICKER){
                if(data != null&&data.hasExtra(ImagePicker.EXTRA_RESULT_ITEMS)) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    imagesall.put(1, images.get(0));
                    mCameraGoodsIv.setSelected(true);
                }

          }else if(requestCode == IMAGE_ORDERS_PICKER){
            if(data != null&&data.hasExtra(ImagePicker.EXTRA_RESULT_ITEMS)) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                imagesall.put(2,images.get(0));
                mCameraOrderIv.setSelected(true);
            }
          }else {
           //       Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
         }
    }


    void  orcCheckSuccess(String result){
        mLoadingDialog.dismiss();
        try {
            JSONObject jsonObject=new JSONObject(result);
            Log.d(TAG,jsonObject.getString("errorcode")+"-------"+
                    jsonObject.getString("errormsg"));
           /* if(!"0".equals(jsonObject.getString("errorcode"))||
                    jsonObject.getInt("errorcode")==0){
                Toast.makeText(this, jsonObject.getString("errormsg"), Toast.LENGTH_SHORT).show();
                return;
            }*/
            mSenderMet.setText(jsonObject.getString("name"));
            mSexMet.setText(jsonObject.getString("sex"));
            mNationMet.setText(jsonObject.getString("nation"));
            mBirthdayMet.setText(jsonObject.getString("birth"));
            mBirthplaceMet.setText(jsonObject.getString("address"));
            mPidMet.setText(jsonObject.getString("id"));


          //  Glide.with(mContext).load(BitMapUtils.Base64toBitmap(jsonObject.getString("frontimage"))).into(mAvatarIv);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "识别失败，请重新上传", Toast.LENGTH_SHORT).show();
        }
    }


    void  orcCheckError(){
        mLoadingDialog.dismiss();
        Toast.makeText(this, "识别失败，请检查您的网络", Toast.LENGTH_SHORT).show();
    }


    /**
     * 检查各个信息是否填写完毕
     */
    void checkInfo(){
        if(imagesall.size()<3){
            Toast.makeText(this, "请上传3张必要的照片", Toast.LENGTH_SHORT).show();
            return;
        }
        String mOidMets=mOidMet.getText().toString();//运单号
        String mSenderMets=mSenderMet.getText().toString();//姓名
        String mSexMets=mSexMet.getText().toString();//性别
        String mNationMets=mNationMet.getText().toString();//民族
        String mBirthdayMets=mBirthdayMet.getText().toString();//出生日期
        String mBirthplaceMets=mBirthplaceMet.getText().toString();//住址
        String mPidMets=mPidMet.getText().toString();//身份证号
        String mTelMets=mTelMet.getText().toString();//发件人电话
        String mReceiverMets=mReceiverMet.getText().toString();//收件人
        String mReceiverTelMets=mReceiverTelMet.getText().toString();//收件人电话
        String mReceiverAddr=et_mReceiverAddr.getText().toString();//收件人地址


        if(StringUtils.isNull(mOidMets)||StringUtils.isNull(mSenderMets)||StringUtils.isNull(mSexMets)||
                StringUtils.isNull(mNationMets)||StringUtils.isNull(mBirthdayMets)||StringUtils.isNull(mBirthplaceMets)||
                StringUtils.isNull(mPidMets)||StringUtils.isNull(mTelMets)||StringUtils.isNull(mReceiverMets)||
                StringUtils.isNull(mReceiverTelMets)||StringUtils.isNull(mReceiverAddr)){
            Toast.makeText(this, "请确认所有信息填写完整!", Toast.LENGTH_SHORT).show();
            return;
        }
        updateImages(mOidMets,mSenderMets,mSexMets,mNationMets,mBirthdayMets
                     ,mBirthplaceMets,mPidMets,mTelMets,mReceiverMets,mReceiverTelMets,mReceiverAddr);
    }


    /**
     * 上传完毕后将各个值复原
     */
    void deleteInfo(){
        mOidMet.setText("");//运单号
        mSenderMet.setText("");//姓名
        mSexMet.setText("");//性别
        mNationMet.setText("");//民族
        mBirthdayMet.setText("");//出生日期
        mBirthplaceMet.setText("");//住址
        mPidMet.setText("");//身份证号
        mTelMet.setText("");//发件人电话
        mReceiverMet.setText("");//收件人
        mReceiverTelMet.setText("");//收件人电话
        et_mReceiverAddr.setText("");
        imagesall.clear();
        mCameraPidIv.setSelected(false);
        mCameraGoodsIv.setSelected(false);
        mCameraOrderIv.setSelected(false);
    }


    /**
     * 上传图片和运单号等基本信息
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
     *@param mReceiverAddr 收件人地址
     */
    void updateImages( String mOidMets,String mSenderMets,String mSexMets,String mNationMets,
                       String mBirthdayMets,String mBirthplaceMets,String mPidMets,String mTelMets
                  ,String mReceiverMets, String mReceiverTelMets,String mReceiverAddr){
        Log.d(TAG,"updateImages--"+imagesall.size());
        ImageFileUpload.upImage(mOidMets,mSenderMets,mSexMets,mNationMets,mBirthdayMets
                ,mBirthplaceMets,mPidMets,mTelMets,mReceiverMets,mReceiverTelMets,mReceiverAddr,imagesall, new ImageFileUpload.SimpleCallBack() {
            @Override
            public void Succ(String result) {
                Log.d(TAG,"Succ--"+result);
                try{
                    JSONObject jsonObject=new JSONObject(result);
                    if(jsonObject.getInt("code")==0){
                        Toast.makeText(mContext, "上传完毕!", Toast.LENGTH_SHORT).show();
                        deleteInfo();
                    }else{
                        Toast.makeText(mContext, "上传失败!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();;
                    Toast.makeText(mContext, "上传失败!", Toast.LENGTH_SHORT).show();
                }

                mLoadingDialog.dismiss();
            }

            @Override
            public void error() {
                Log.d(TAG,"error--");
                Toast.makeText(mContext, "上传失败!", Toast.LENGTH_SHORT).show();
                mLoadingDialog.dismiss();
            }

            @Override
            public void onStarted() {
                Log.d(TAG,"onStarted--");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                double r = (double)current/ (double) total;

                Log.d(TAG,"total--"+total+"current---"+current
                +"----------");
                mLoadingDialog.setText ( "正在上传  "+ df.format(r*100)+"%");
                mLoadingDialog.show();
            }
        });
    }
}
