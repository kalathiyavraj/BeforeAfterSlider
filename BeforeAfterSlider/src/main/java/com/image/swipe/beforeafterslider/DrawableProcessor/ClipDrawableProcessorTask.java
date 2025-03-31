package com.image.swipe.beforeafterslider.DrawableProcessor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.lang.ref.WeakReference;

public class ClipDrawableProcessorTask<T> extends AsyncTask<T, Void, ClipDrawable> {
    private final WeakReference<ImageView> imageRef;
    private final WeakReference<SeekBar> seekBarRef;
    private final Context context;
    private final OnAfterImageLoaded loadedFinishedListener;

    public ClipDrawableProcessorTask(ImageView imageView, SeekBar seekBar, Context context, OnAfterImageLoaded loadedFinishedListener) {
        this.imageRef = new WeakReference<>(imageView);
        this.seekBarRef = new WeakReference<>(seekBar);
        this.context = context;
        this.loadedFinishedListener = loadedFinishedListener;
    }

    @Override
    protected ClipDrawable doInBackground(T... args) {
        try {
            Bitmap theBitmap;
            if (args[0] instanceof String) {
                FutureTarget<Bitmap> futureTarget = Glide.with(context)
                        .asBitmap()
                        .load((String) args[0])
                        .submit();
                theBitmap = futureTarget.get();
            } else {
                theBitmap = ((BitmapDrawable) args[0]).getBitmap();
            }

            Bitmap tmpBitmap = getScaledBitmap(theBitmap);
            if (tmpBitmap != null) {
                theBitmap = tmpBitmap;
            }

            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), theBitmap);
            return new ClipDrawable(bitmapDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        try {
            ImageView imageView = imageRef.get();
            if (imageView == null) {
                return bitmap;
            }
            int imageWidth = imageView.getWidth();
            int imageHeight = imageView.getHeight();

            if (imageWidth > 0 && imageHeight > 0) {
                return Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ClipDrawable clipDrawable) {
        ImageView imageView = imageRef.get();
        SeekBar seekBar = seekBarRef.get();
        
        if (imageView != null && clipDrawable != null) {
            initSeekBar(clipDrawable);
            imageView.setImageDrawable(clipDrawable);
            
            if (clipDrawable.getLevel() != 0) {
                clipDrawable.setLevel(5000);
            } else if (seekBar != null) {
                clipDrawable.setLevel(seekBar.getProgress());
            }
            
            if (loadedFinishedListener != null) {
                loadedFinishedListener.onLoadedFinished(true);
            }
        } else if (loadedFinishedListener != null) {
            loadedFinishedListener.onLoadedFinished(false);
        }
    }

    private void initSeekBar(final ClipDrawable clipDrawable) {
        SeekBar seekBar = seekBarRef.get();
        if (seekBar != null) {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    clipDrawable.setLevel(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }
    }

    public interface OnAfterImageLoaded {
        void onLoadedFinished(boolean loadedSuccess);
    }
}
