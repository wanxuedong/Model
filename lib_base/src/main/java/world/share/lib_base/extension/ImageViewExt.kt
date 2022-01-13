package world.share.lib_base.extension

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import world.share.lib_base.R
import world.share.lib_base.utils.AppUtil

/**
 * @author wan
 * 创建日期：2021/11/24
 * 描述：ImageView扩展
 */
fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).apply(
        RequestOptions().diskCacheStrategy(
            DiskCacheStrategy.RESOURCE
        ).skipMemoryCache(true).dontAnimate().placeholder(R.drawable.ic_placeholder)
    ).thumbnail(0.6f)
        .into(this)
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun ImageView.loadUrl(
    url: String?,
    placeHolderRes: Drawable,
    errorHolder: Drawable? = AppUtil.app?.getDrawable(R.drawable.ic_error_placeholder)
) {
    Glide.with(this)
        .load(url)
        .apply(
            RequestOptions().placeholder(placeHolderRes).error(errorHolder)
        )
        .into(this)
}

fun ImageView.loadBlurImageRes(@DrawableRes imgRes: Int, radius: Int = 25, scale: Int = 1) {
    Glide.with(this).load(imgRes).dontAnimate()
        .apply(
            RequestOptions.bitmapTransform(BlurTransformation(radius, scale))
                .placeholder(R.drawable.ic_placeholder)
        ).into(this)
}

fun ImageView.loadBlurImage(url: String?) {
    Glide.with(this).load(url).dontAnimate()
        .apply(RequestOptions.bitmapTransform(BlurTransformation())).into(this)
}

fun ImageView.loadImage(uri: String?, requestListener: RequestListener<Drawable?>) {
    Glide.with(this).load(uri).listener(requestListener).into(this)
}

fun ImageView.loadImage(uri: String?, width: Int, height: Int) {
    val multi = MultiTransformation(CropTransformation(width, height))
    Glide.with(this).load(uri).apply(RequestOptions.bitmapTransform(multi).dontAnimate()).into(this)
}

fun ImageView.loadImageCenterCrop(uri: String?, @DrawableRes holder: Int? = null) {
    Glide.with(this).load(uri)
        .apply(RequestOptions().dontAnimate().dontTransform().centerCrop().apply {
            if (holder != null) {
                this.placeholder(holder)
            }
        }).into(this)
}

fun ImageView.loadGif(
    uri: String?,
    requestListener: RequestListener<GifDrawable?>? = null,
    centerCrop: Boolean? = null,
    @DrawableRes holder: Int? = null
) {
    var requestOptions = RequestOptions().dontTransform()
    if (centerCrop != null) {
        requestOptions = requestOptions.centerCrop()
    }
    if (holder != null) {
        requestOptions = requestOptions.placeholder(holder)
    }
    if (requestListener != null) {
        Glide.with(this).asGif().load(uri).apply(requestOptions).listener(requestListener)
            .into(this)
    } else {
        Glide.with(this).asGif().load(uri).apply(requestOptions).into(this)
    }
}

fun ImageView.loadCircleImage(url: String?, @DrawableRes holder: Int? = null) {
    if (url.isNullOrBlank()) {
        if (holder != null) {
            setImageResource(holder)
        }
    } else if (holder == null) {
        Glide.with(this).load(url).apply(RequestOptions().circleCrop()).into(this)
    } else {
        Glide.with(this).load(url).apply(RequestOptions().placeholder(holder).circleCrop())
            .into(this)
    }
}

fun ImageView.loadCircleImageRes(resId: Int, @DrawableRes holder: Int? = null) {
    if (resId == 0) {
        if (holder != null) {
            setImageResource(holder)
        }
    } else if (holder == null) {
        Glide.with(this).load(resId).apply(RequestOptions().circleCrop()).into(this)
    } else {
        Glide.with(this).load(resId).apply(RequestOptions().placeholder(holder).circleCrop())
            .into(this)
    }
}

fun ImageView.loadRoundImage(url: String?, radius: Int, @DrawableRes holder: Int? = null) {
    if (url.isNullOrBlank() && holder != null) {
        setImageResource(holder)
    } else if (holder == null) {
        Glide.with(this).load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius, 0)))
            .into(this)
    } else {
        Glide.with(this).load(url).apply(
            RequestOptions.bitmapTransform(RoundedCornersTransformation(radius, 0))
                .placeholder(holder)
        )
            .into(this)
    }
}