package com.cstor.orc.myrecorditem.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cstor.orc.R;
import com.cstor.orc.http.RecordItem;
import com.cstor.orc.imageloads.MyImageLoader;
import com.cstor.orc.myrecorditem.adapter.PagesPhtotAdapter;
import com.cstor.orc.myrecorditem.bean.ImagesItem;
import com.cstor.orc.myrecorditem.bean.MyRecordItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class MyRecordActivity extends AppCompatActivity {

    private Context mContext;

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

    private  String  bills;

    private ImageView iv_back2;

    private  ImageView iv_mCameraPidIv,iv_mCameraGoodsIv,iv_mCameraOrderIv;

    private PagesPhtotAdapter myPagesAdapter;

    private ViewPager popupPhotoPager;


    private PopupWindow bigImagePopup;


    List<ImagesItem> liveitemlists=new ArrayList<ImagesItem>();

    private TextView tv_toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_item);
        mContext = this;
        init();
        bills=getIntent().getExtras().getString("bills");
        getRecordItems(bills);


    }

    void init(){
        mOidMet=(EditText)findViewById(R.id.tv2_mOidMet);
        mSenderMet=(EditText)findViewById(R.id.mSenderMet);
        mSexMet=(EditText)findViewById(R.id.mSexMet);
        mNationMet=(EditText)findViewById(R.id.mNationMet);
        mBirthdayMet=(EditText)findViewById(R.id.mBirthdayMet);
        mBirthplaceMet=(EditText)findViewById(R.id.mBirthplaceMet);
        et_mReceiverAddr=(EditText)findViewById(R.id.et_mReceiverAddr);


        mPidMet=(EditText)findViewById(R.id.mPidMet);
        mTelMet=(EditText)findViewById(R.id.mTelMet);
        mReceiverTelMet=(EditText)findViewById(R.id.mReceiverTelMet);
        mReceiverMet=(EditText)findViewById(R.id.mReceiverMet);
        iv_back2=(ImageView) findViewById(R.id.iv_back2);

        iv_mCameraPidIv=(ImageView) findViewById(R.id.iv_mCameraPidIv);
        iv_mCameraGoodsIv=(ImageView) findViewById(R.id.iv_mCameraGoodsIv);
        iv_mCameraOrderIv=(ImageView) findViewById(R.id.iv_mCameraOrderIv);

        iv_mCameraPidIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBigPopup(0);
            }
        });
        iv_mCameraGoodsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBigPopup(1);
            }
        });
        iv_mCameraOrderIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBigPopup(2);
            }
        });


        iv_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_toolbar_title=(TextView)findViewById(R.id.tv_toolbar_title);
    }


    /**
     * 展示大图
     * @param paramInt
     */
    public void showBigPopup(int paramInt){

        View localView = null;
        if (bigImagePopup!=null){
            bigImagePopup.dismiss();
            bigImagePopup=null;
        }
        localView = LayoutInflater.from(mContext).inflate(R.layout.pop_beautypics, null);
        bigImagePopup = new PopupWindow(localView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT,false);
        bigImagePopup.setTouchable(true);
        localView.setClickable(true);
        bigImagePopup.setAnimationStyle(R.style.photosWindowAnimation);

        bigImagePopup.setBackgroundDrawable(new BitmapDrawable());
        bigImagePopup.setFocusable(true);
        bigImagePopup.setOutsideTouchable(true);

        RelativeLayout baImageView=(RelativeLayout)localView.findViewById(R.id.photoTopBackButtonLayout);
        baImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                bigImagePopup.dismiss();
                bigImagePopup=null;
            }
        });

        popupPhotoPager = ((ViewPager)localView.findViewById(R.id.poiPhotoPager));
        popupPhotoPager.setOffscreenPageLimit(1);
        localView.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg1 == KeyEvent.KEYCODE_BACK){
                    if(bigImagePopup != null) {
                        bigImagePopup.dismiss();
                        bigImagePopup=null;
                    }
                }
                return false;
            }
        });

        myPagesAdapter=new PagesPhtotAdapter(mContext,liveitemlists);
        popupPhotoPager.setAdapter(myPagesAdapter);
        popupPhotoPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        popupPhotoPager.setCurrentItem(paramInt, false);
        bigImagePopup.showAtLocation(tv_toolbar_title, 83, 0, 0);

    }


    void setSuccessInfo(MyRecordItem mMyRecordItem){
        mOidMet.setText(mMyRecordItem.getBillDetail().getBill());
        mSenderMet.setText(mMyRecordItem.getBillDetail().getSenderName());
        mSexMet.setText(mMyRecordItem.getBillDetail().getSex());
        mNationMet.setText(mMyRecordItem.getBillDetail().getNation());
        mBirthdayMet.setText(mMyRecordItem.getBillDetail().getBirthday());
        mBirthplaceMet.setText(mMyRecordItem.getBillDetail().getAddr());
        mPidMet.setText(mMyRecordItem.getBillDetail().getIdcard());
        mTelMet.setText(mMyRecordItem.getBillDetail().getSenderPhone());
        mReceiverTelMet.setText(mMyRecordItem.getBillDetail().getRecievePhone());
        mReceiverMet.setText(mMyRecordItem.getBillDetail().getRecieveName());
        et_mReceiverAddr.setText(mMyRecordItem.getBillDetail().getRevieveAddr());


        MyImageLoader.GlideImage(mContext,mMyRecordItem.getBillDetail().getIdImg(),iv_mCameraPidIv);
        MyImageLoader.GlideImage(mContext,mMyRecordItem.getBillDetail().getGoodImg(),iv_mCameraGoodsIv);
        MyImageLoader.GlideImage(mContext,mMyRecordItem.getBillDetail().getBillImg(),iv_mCameraOrderIv);
    }

    void getRecordItems(String bills){
        RecordItem.getRecordItem(bills, new RecordItem.SimpleCallBack() {
            @Override
            public void Succ(String result) {
                Gson gson = new Gson();
                MyRecordItem myRecordItem= gson.fromJson(result,MyRecordItem.class);
                if(myRecordItem.getCode()==0&&myRecordItem.getBillDetail()!=null){
                    liveitemlists.clear();
                    setSuccessInfo(myRecordItem);
                    ImagesItem imagesItem1=new ImagesItem(myRecordItem.getBillDetail().getIdImg());
                    liveitemlists.add(0,imagesItem1);

                    ImagesItem imagesItem2=new ImagesItem(myRecordItem.getBillDetail().getGoodImg());
                    liveitemlists.add(1,imagesItem2);


                    ImagesItem imagesItem3=new ImagesItem(myRecordItem.getBillDetail().getBillImg());
                    liveitemlists.add(2,imagesItem3);

                }else{
                    Toast.makeText(mContext, "查询失败,请检查网络!"  , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void error() {
                Toast.makeText(mContext, "查询失败,请检查网络!"  , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }
        });

    }




}