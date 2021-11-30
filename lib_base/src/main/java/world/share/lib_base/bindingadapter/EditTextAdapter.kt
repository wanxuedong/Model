package world.share.lib_base.bindingadapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.KeyboardUtils
import world.share.lib_base.mvvm.command.BindingCommand

object EditTextAdapter {
    /**
     * EditText重新获取焦点的事件绑定
     */
    @JvmStatic
    @BindingAdapter(value = ["requestFocus"], requireAll = false)
    fun requestFocusCommand(editText: EditText, needRequestFocus: Boolean) {
        if (needRequestFocus) {
            editText.setSelection(editText.text.length)
            editText.requestFocus()
            editText.isFocusableInTouchMode = needRequestFocus
            KeyboardUtils.showSoftInput(editText)
        }else{
            KeyboardUtils.hideSoftInput(editText)
            editText.clearFocus()
        }

    }

    /**
     * EditText输入文字改变的监听
     */
    @JvmStatic
    @BindingAdapter(value = ["textChanged"], requireAll = false)
    fun addTextChangedListener(editText: EditText, textChanged: BindingCommand<String?>?) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(text: CharSequence, i: Int, i1: Int, i2: Int) {
                textChanged?.execute(text.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }
}