package world.share.widget

import android.text.Editable
import android.text.TextWatcher
import io.reactivex.subjects.PublishSubject

/**
 * @author wan
 * 创建日期：2021/12/1
 * 描述：TextWatcher统一封装，减少代码
 */
class EditTextMonitor(private val mPublishSubject: PublishSubject<String>) : TextWatcher {
    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {}

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {}

    override fun afterTextChanged(s: Editable) {
        mPublishSubject.onNext(s.toString())
    }
}