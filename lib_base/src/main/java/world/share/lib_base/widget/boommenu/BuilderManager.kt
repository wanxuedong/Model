package world.share.lib_base.widget.boommenu

import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton
import world.share.lib_base.R

/**
 * Created by Weiping Huang at 23:44 on 16/11/21
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */
object BuilderManager {
    private val imageResources = intArrayOf(
        R.drawable.bat,
        R.drawable.bear,
        R.drawable.bee,
        R.drawable.butterfly,
        R.drawable.cat,
        R.drawable.deer,
        R.drawable.dolphin,
        R.drawable.eagle,
        R.drawable.horse,
        R.drawable.elephant,
        R.drawable.owl,
        R.drawable.peacock,
        R.drawable.pig,
        R.drawable.rat,
        R.drawable.snake,
    )
    private var imageResourceIndex = 0
    val imageResource: Int
        get() {
            if (imageResourceIndex >= imageResources.size) imageResourceIndex = 0
            return imageResources[imageResourceIndex++]
        }
    val textInsideCircleButtonBuilder: TextInsideCircleButton.Builder
        get() = TextInsideCircleButton.Builder()
            .normalImageRes(imageResource)
            .normalTextRes(R.string.app_name)


}