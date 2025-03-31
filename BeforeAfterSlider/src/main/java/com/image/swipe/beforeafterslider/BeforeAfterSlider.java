package com.image.swipe.beforeafterslider;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.image.swipe.beforeafterslider.DrawableProcessor.ClipDrawableProcessorTask;


public class BeforeAfterSlider extends RelativeLayout implements ClipDrawableProcessorTask.OnAfterImageLoaded {

    private ImageView beforeImageView;
    private ImageView afterImageView;
    private SeekBar seekBar;

    public BeforeAfterSlider(Context context) {
        super(context);
        init(context, null);
    }

    public BeforeAfterSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.slider_layout, this);
        beforeImageView = findViewById(R.id.before_image_view_id);
        afterImageView = findViewById(R.id.after_image_view_id);
        seekBar = findViewById(R.id.seekbar_id);

        if (attrs != null) {
            android.content.res.TypedArray attr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BeforeAfterSlider, 0, 0);
            try {
                Drawable thumbDrawable = attr.getDrawable(R.styleable.BeforeAfterSlider_slider_thumb);
                Drawable beforeImage = attr.getDrawable(R.styleable.BeforeAfterSlider_before_image);
                Drawable afterImage = attr.getDrawable(R.styleable.BeforeAfterSlider_after_image);

                setSliderThumb(thumbDrawable);
                setBeforeImage(beforeImage);
                setAfterImage(afterImage);
            } finally {
                attr.recycle();
            }
        }
    }

    /**
     * Set original image
     */
    public BeforeAfterSlider setBeforeImage(String imageUri) {
        loadImage(beforeImageView, imageUri);
        return this;
    }

    public BeforeAfterSlider setBeforeImage(Drawable imgDrawable) {
        beforeImageView.setImageDrawable(imgDrawable);
        return this;
    }

    /**
     * Set changed image
     */
    public void setAfterImage(String imageUri) {
        new ClipDrawableProcessorTask<>(afterImageView, seekBar, getContext(), this).execute(imageUri);
    }

    public void setAfterImage(Drawable imageDrawable) {
        new ClipDrawableProcessorTask<>(afterImageView, seekBar, getContext(), this).execute(imageDrawable);
    }

    /**
     * Set slider thumb
     */
    public void setSliderThumb(Drawable thumb) {
        if (thumb != null) {
            seekBar.setThumb(thumb);
        }
    }

    /**
     * Fired up after second image loading is finished
     */
    @Override
    public void onLoadedFinished(boolean loadedSuccess) {
        seekBar.setVisibility(loadedSuccess ? View.VISIBLE : View.GONE);
    }

    /**
     * Utility method to load images using Glide
     */
    private void loadImage(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
    }
}
