package com.cstor.orc.myrecorditem.bean;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class ImagesItem {


    public ImagesItem(String imgurl) {
        this.imgurl = imgurl;
    }

    public ImagesItem() {
    }

    private String imgurl;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
