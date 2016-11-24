package com.cstor.orc.myorcslist.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cstor.orc.R;
import com.cstor.orc.http.Recordlist;
import com.cstor.orc.imageloads.MyImageLoader;
import com.cstor.orc.myorcslist.bean.OrcListsBean;
import com.cstor.orc.myrecorditem.activity.MyRecordActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OrcListsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private List<OrcListsBean.ListBean> mDatas=new ArrayList<OrcListsBean.ListBean>();

    private  MyOrcItemAdapter mMyOrcItemAdapter;
    private Context mContext;


    private  ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orc_lists);
        mContext=this;


        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);


        mMyOrcItemAdapter=new MyOrcItemAdapter();
        mRecyclerView=(RecyclerView)findViewById(R.id.id_recyclerview);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMyOrcItemAdapter);
        mMyOrcItemAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent=new Intent(mContext, MyRecordActivity.class);
                intent.putExtra("bills",  mDatas.get(position).getBill());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        getRecordlist();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    void getRecordlist(){
         Recordlist.getRecordlist(new Recordlist.SimpleCallBack() {
             @Override
             public void Succ(String result) {
                 mDatas.clear();
                 try{
                     Gson gson = new Gson();
                     OrcListsBean orcListsBean= gson.fromJson(result,OrcListsBean.class);
                     if(orcListsBean.getCode()==0){
                         mDatas.addAll(orcListsBean.getList());
                         mMyOrcItemAdapter.notifyDataSetChanged();
                     }
                 }catch (Exception e){
                     e.printStackTrace();
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

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }

    class MyOrcItemAdapter extends  RecyclerView.Adapter<MyHolder>{
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(mContext).inflate(R.layout.item_orc,parent,false);
            MyHolder myHolder=new MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, int position) {
            holder.tv_mOidMet.setText("运单号 : "+" "+mDatas.get(position).getBill());
            holder.tv_mSenderMet.setText("寄件人 :"+" "+mDatas.get(position).getSenderName());
            holder.tv_mReceiverMet.setText("收件人 : "+" "+mDatas.get(position).getRecieveName());
            MyImageLoader.GlideImage(mContext,mDatas.get(position).getGoodImg(),(holder.iv_goods));
            if(mOnItemClickLitener!=null){
                holder.ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(view,pos);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    class MyHolder extends RecyclerView.ViewHolder{

        ImageView  iv_goods;
        TextView tv_mOidMet,tv_mSenderMet,tv_mReceiverMet;

        LinearLayout ll_item;
        public MyHolder(View itemView) {
            super(itemView);
            iv_goods=(ImageView)itemView.findViewById(R.id.iv_goods);
            tv_mOidMet=(TextView)itemView.findViewById(R.id.tv_mOidMet);
            tv_mSenderMet=(TextView)itemView.findViewById(R.id.tv_mSenderMet);
            tv_mReceiverMet=(TextView)itemView.findViewById(R.id.tv_mReceiverMet);
            ll_item=(LinearLayout)itemView.findViewById(R.id.ll_item);
        }
    }
}
