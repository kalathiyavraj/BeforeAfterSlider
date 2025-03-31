package com.image.swipe.beforeafterslider.Extensions;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ViewExtensions {

    public static void loadImage(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
    }

    public static void loadImage(ImageView imageView, Drawable imgDrawable) {
        imageView.setImageDrawable(imgDrawable);
    }

    public static void stayVisibleOrGone(View view, boolean stay) {
        view.setVisibility(stay ? View.VISIBLE : View.GONE);
    }
}