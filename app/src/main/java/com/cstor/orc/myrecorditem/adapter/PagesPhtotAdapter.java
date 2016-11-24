package com.cstor.orc.myrecorditem.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cstor.orc.R;
import com.cstor.orc.imageloads.MyImageLoader;
import com.cstor.orc.myrecorditem.bean.ImagesItem;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class PagesPhtotAdapter  extends PagerAdapter {

        private List<ImagesItem> mBigPoiImageList;
        private int size2;
        private LayoutInflater mInflater;
        private List<View> views = new ArrayList<View>();
        private  Context mContext;

        public PagesPhtotAdapter(Context context,List<ImagesItem> arrayLists){
            mContext=context;
            mBigPoiImageList=arrayLists;
            mInflater = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            for (int i = 0; i < 3; i++){
                View localView = this.mInflater.inflate(R.layout.item_beauty_pop, null);
                this.views.add(localView);
            }
            size2 =views.size();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mBigPoiImageList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup paramViewGroup, int paramInt) {
            View localView = getViewAt(paramInt);
            PhotoView localPhotoView = (PhotoView)localView.findViewById(R.id.poiPhotoBigImage);
            if (paramViewGroup.indexOfChild(localView) >= 0){
                paramViewGroup.removeView(localView);
            }

            ImagesItem localAlbumModelItem = (ImagesItem)mBigPoiImageList.get(paramInt);
            localPhotoView.setZoomable(true);

            if(localAlbumModelItem.getImgurl()!=null&&!"".equals(localAlbumModelItem.getImgurl())){
                MyImageLoader.GlideImage(mContext,localAlbumModelItem.getImgurl(),localPhotoView);
                localView.setTag(Integer.valueOf(paramInt));
                paramViewGroup.addView(localView);
            }


            return localView;
        }

        @Override
        public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject){

            View localView = getViewAt(paramInt);
            if ((localView != null) && (paramViewGroup != null)){
                if (((Integer)localView.getTag()).intValue() == paramInt){
                    //    ((WebImageView)localView.findViewById(R.id.poiPhotoBigImage)).reset();;
                    paramViewGroup.removeView(localView);
                }
                System.gc();
            }
        }

        public View getViewAt(int paramInt){
            return (View)views.get(paramInt % this.size2);
        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }


}
