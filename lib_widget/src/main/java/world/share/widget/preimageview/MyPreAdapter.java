package world.share.widget.preimageview;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class MyPreAdapter implements PreImageLoader {
    @Override
    public void showView(Context context, ImageView img, PreImageHolder imgUrl) {
        Glide.with(context).load(imgUrl.path).into(img);
    }
}
