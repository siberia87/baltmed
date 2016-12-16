package ru.baltclinic.lliepmah.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ru.baltclinic.lliepmah.view.custom.CircleTransform;


public class ImageUtils {

    private static final int FADE_DURATION = 1000;

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .crossFade(FADE_DURATION)
                .into(imageView);
    }

    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transform(new CircleTransform(context))
                .crossFade(FADE_DURATION)
                .into(imageView);
    }

    public static void loadImageCrop(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade(FADE_DURATION)
                .into(imageView);
    }
}
