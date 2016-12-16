package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.GalleryImage;
import ru.baltclinic.lliepmah.util.ImageUtils;


public class GalleryViewHolder extends DefaultViewHolder<GalleryImage> {


    private final Context mContext;

    @BindView(R.id.li_gallery_iv_image)
    ImageView mIvGallery;


    private GalleryViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(GalleryImage model) {
        if (model != null) {
            ImageUtils.loadImageCrop(mContext, model.getImageUrl(), mIvGallery);
        }
    }

    public static class Builder implements DefaultViewHolder.Builder<GalleryImage> {

        public Builder() {
        }

        @Override
        public DefaultViewHolder<GalleryImage> build(Context context) {
            View view = View.inflate(context, R.layout.li_gallery, null);
            return new GalleryViewHolder(view);
        }
    }

}
