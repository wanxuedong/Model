package world.share.widget.preimageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import world.share.widget.R;

/**
 * 图片预览Activity
 * 使用方法：
 * 使用时直接传入图片的item集合即可，需要设置图片加载器，继承PreImageLoader即可，不设置则无法使用
 * 图片和默认选中第几张，通过intent传递CACHE_IMAGE，PRE_DEFAULT_CHOSE即可
 * 2018/12/17      wan
 **/

public class PreImageActivity extends Activity implements View.OnClickListener {

    private RelativeLayout preImgPagerHeadHolder;
    private PreViewPager viewPager;
    private TextView preImageDownload;           //下载按钮
    private ImageView preImgPagerBack;           //返回按钮
    private TextView preImgPagerCount;          //显示当前翻到的页面
    private PreImageAdapter preImageAdapter;    //图片适配器
    private List<PreImageHolder> imageList;      //存放图片的集合
    private static final String CACHE_IMAGE = "pre_image_show";   //intent利用此值传递图片数据
    private static final String PRE_DEFAULT_CHOSE = "pre_default_show";  //intent利用此值传递默认选中第几张图片
    private int currentPosition = 0;       //当前展示的是第几张图片

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_image);
        initViews();
        initData();
        initListener();
    }

    private void initViews() {
        preImgPagerHeadHolder = findViewById(R.id.pre_img_pager_head_holder);
        viewPager = findViewById(R.id.pre_img_pager_show);
        preImgPagerBack = findViewById(R.id.pre_img_pager_back);
        preImgPagerCount = findViewById(R.id.pre_img_pager_count);
        preImageDownload = findViewById(R.id.pre_image_download);
//        preImageDownload.setTypeface(iconfont);
    }

    private void initListener() {
        preImgPagerBack.setOnClickListener(this);
        preImageDownload.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                currentPosition = i;
                preImgPagerCount.setText(i + 1 + "/" + imageList.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        if (preImageAdapter != null) {
            preImageAdapter.setOnImageClick(new PreImageAdapter.OnImageClick() {
                @Override
                public void click(int position) {     //当图片被点击时，隐藏或者展示头部
                    if (preImgPagerHeadHolder.getVisibility() == View.VISIBLE) {
                        preImgPagerHeadHolder.setAnimation(AnimationUtils.loadAnimation(PreImageActivity.this, R.anim.pre_image_top_out));
                        preImgPagerHeadHolder.setVisibility(View.GONE);
                    } else {
                        preImgPagerHeadHolder.setVisibility(View.VISIBLE);
                        preImgPagerHeadHolder.setAnimation(AnimationUtils.loadAnimation(PreImageActivity.this, R.anim.pre_image_top_in));
                    }
                }
            });
        }
    }

    private void initData() {
        imageList = new ArrayList<>();
        imageList = (ArrayList<PreImageHolder>) getIntent().getSerializableExtra(CACHE_IMAGE);
        //默认选中的图片
        int currentShow = getIntent().getIntExtra(PRE_DEFAULT_CHOSE, 1);
        if (currentShow < 1) {
            currentShow = 1;
        }
        if (currentShow > imageList.size()) {
            currentShow = imageList.size();
        }
        //图片加载器
        PreImageLoader preImageLoader = PreImageConfige.getInstance().getPreImageLoader();
        if (imageList != null && imageList.size() > 0) {
            preImageAdapter = new PreImageAdapter(imageList);
            if (preImageLoader != null) {
                setImageLoader(preImageLoader);
            }
            viewPager.setAdapter(preImageAdapter);
            viewPager.setCurrentItem(currentShow - 1);
            preImgPagerCount.setText(currentShow + "/" + imageList.size());
        } else {
            preImgPagerCount.setText("未传入图片");
        }
    }

    private void setImageLoader(PreImageLoader preImageLoader) {
        if (preImageAdapter != null) {
            preImageAdapter.setImageLoader(preImageLoader);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pre_img_pager_back) {      //点击返回
            finish();
        } else if (id == R.id.pre_image_download) {      //下载图片
//            ToastUtil.show("正在下载...");
            preImageDownload.setEnabled(false);
//                Glide.with(PreImageActivity.this).load(imageList.get(currentPosition).getPath()).asBitmap().skipMemoryCache(true) // 不使用内存缓存
//                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        if (resource != null) {
//                            saveImageToGallery(PreImageActivity.this, resource);
//                        }
//                        preImageDownload.setEnabled(true);
//                    }
//                });
        }
    }

    /**
     * 保存图片到指定路径
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "qrcode";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

//            把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
//                ToastUtil.show("保存成功!");
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            Log.d("IOException", e.getMessage() == null ? "" : e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}
