package com.tsystem.ui.activity.fullscreen;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.tsystem.R;
import com.tsystem.databinding.ActivityFullBinding;
import com.tsystem.ui.base.BaseActivity;
import com.tsystem.utils.GlideApp;

import java.io.File;

import javax.inject.Inject;

public class FullActivity extends BaseActivity implements FullActivityContract {

    @Inject
    FullActivityPresenter presenter;

    ActivityFullBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_full);
        getActivityComponent().inject(this);
        presenter.setView(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.fullImage.setTransitionName("SELECTED_IMAGE");
        }

        setUpAlbumArt();


    }

    public void setUpAlbumArt() {
        if (getIntent().getExtras().getString("IMAGE_URL") != null && !getIntent().getExtras().getString("IMAGE_URL").equalsIgnoreCase("null")) {
            binding.fullImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            supportPostponeEnterTransition();
            if (getIntent().getExtras().getBoolean("NET_ACCESS"))
                GlideApp.with(this)
                        .asBitmap()
                        .placeholder(R.drawable.ic_loading)
                        .load(getIntent().getExtras().get("IMAGE_URL")) // Uri of the picture
                        .dontAnimate()
                        .signature(new ObjectKey(getIntent().getExtras().get("IMAGE_URL")))
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                supportStartPostponedEnterTransition();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                supportStartPostponedEnterTransition();
                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.fullImage);
            else
                GlideApp.with(this)
                        .asBitmap()
                        .placeholder(R.drawable.ic_loading)
                        .load(new File(String.valueOf(getIntent().getExtras().get("IMAGE_URL")))) // Uri of the picture
                        .dontAnimate()
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                supportStartPostponedEnterTransition();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                supportStartPostponedEnterTransition();
                                return false;
                            }
                        })
                        .into(binding.fullImage);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.supportFinishAfterTransition();
    }
}
