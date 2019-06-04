package org.atmarkcafe.otocon.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtils {
    public static void show(String url, ImageView imageView, int pageHolder, int errorHolder){

        if(imageView == null){
            return;
        }

        RequestOptions requestOptions = new RequestOptions();

        if(pageHolder > 0){
            requestOptions.placeholder(pageHolder);
        }

        if(errorHolder > 0){
            requestOptions.error(errorHolder);
        }

        Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }

    public static void show(String url, ImageView imageView){
        show(url, imageView, 0, 0);
    }
}
