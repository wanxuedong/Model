package world.share.home.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import kotlin.jvm.JvmOverloads
import java.io.IOException
import java.io.InputStream

/**
 * @author wan
 * 创建日期：2021/11/08
 * 描述：上下连续滚动展示图片控件
 */
class BigView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), GestureDetector.OnGestureListener, View.OnTouchListener {
    private val mScroll: Scroller

    //手势
    private val mGestureDetector: GestureDetector
    private val mOptions: BitmapFactory.Options
    private var mImageHeight = 0
    private var mImageWidht = 0

    //手机屏幕宽高
    private var mViewHeight = 0
    private var mViewWidth = 0

    //图片缩放因子
    private var mScale = 0f
    private val mRect: Rect
    private var mBitmap: Bitmap? = null

    //bitmap解码器
    private var mDecoder: BitmapRegionDecoder? = null
    fun setImage(inputStream: InputStream?) {

        //只将大图的边框加载到内存
        mOptions.inJustDecodeBounds = true
        //将图片边宽加载到options
        BitmapFactory.decodeStream(inputStream, null, mOptions)
        mImageHeight = mOptions.outHeight
        mImageWidht = mOptions.outWidth
        //设置内存可复用
        mOptions.inMutable = true
        //设置图片编码格式
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565
        mOptions.inJustDecodeBounds = false
        try {
            //解码图片
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //requestLayout方法会导致View的onMeasure、onLayout、onDraw方法被调用；
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mViewHeight = measuredHeight
        mViewWidth = measuredWidth
        mScale = mViewWidth / mImageWidht.toFloat()
        //等比例缩放后图片所占区域
        mRect.top = 0
        mRect.left = 0
        mRect.right = mImageWidht
        mRect.bottom = (mViewHeight / mScale).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mDecoder == null) return
        //内存复用，分配空间
        mOptions.inBitmap = mBitmap
        //指定解码区域
        mBitmap = mDecoder!!.decodeRegion(mRect, mOptions)
        val matrix = Matrix()
        //将图片缩放到手机屏幕内
        matrix.preScale(mScale, mScale)
        canvas.drawBitmap(mBitmap!!, matrix, null)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        //将触摸时间分发给手势
        return mGestureDetector.onTouchEvent(motionEvent)
    }

    override fun onDown(motionEvent: MotionEvent): Boolean {
        //当按下屏幕时，如果正在滑动，则强制停止滑动
        if (!mScroll.isFinished) {
            mScroll.forceFinished(true)
        }
        return true
    }

    override fun onScroll(
        motionEvent: MotionEvent,
        motionEvent1: MotionEvent,
        v: Float,
        v1: Float
    ): Boolean {
        //滑动时rect偏移量计算
        mRect.offset(0, v1.toInt())
        if (mRect.bottom > mImageHeight) {
            mRect.bottom = mImageHeight
            mRect.top = mImageHeight - (mViewHeight / mScale).toInt()
        }
        if (mRect.top < 0) {
            mRect.top = 0
            mRect.bottom = (mViewHeight / mScale).toInt()
        }
        //调用ondraw方法
        invalidate()
        return false
    }

    override fun onFling(
        motionEvent: MotionEvent,
        motionEvent1: MotionEvent,
        v: Float,
        v1: Float
    ): Boolean {
        mScroll.fling(
            0,
            mRect.top,
            0,
            (-v1).toInt(),
            0,
            0,
            0,
            mImageHeight - (mViewHeight / mScale).toInt()
        )
        return false
    }

    //处理滚动结果
    override fun computeScroll() {
        if (mScroll.isFinished) return
        if (mScroll.computeScrollOffset()) {
            mRect.top = mScroll.currY
            mRect.bottom = mScroll.currY + (mViewHeight / mScale).toInt()
            //调用ondraw方法
            invalidate()
        }
        super.computeScroll()
    }

    override fun onShowPress(motionEvent: MotionEvent) {}
    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onLongPress(motionEvent: MotionEvent) {}

    init {
        mOptions = BitmapFactory.Options() //内存复用
        mGestureDetector = GestureDetector(context, this)
        mScroll = Scroller(context)
        mRect = Rect()
        setOnTouchListener(this)
    }
}