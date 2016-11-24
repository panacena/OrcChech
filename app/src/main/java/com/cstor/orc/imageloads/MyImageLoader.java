package com.cstor.orc.imageloads;


import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cstor.orc.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;


public class MyImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(new File(path))
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .into(imageView);
    }

    public  static void GlideImage(Context context, String urls, ImageView imageView){
        Glide.with(context)
                .load(urls)
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .into(imageView);
    }


    @Override
    public void clearMemoryCache() {
    }

}