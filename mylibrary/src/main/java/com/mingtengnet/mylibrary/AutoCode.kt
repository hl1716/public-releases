package com.mingtengnet.mylibrary

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_auto_code.view.*

class AutoCode : LinearLayout, TextWatcher {
    private var textViews = arrayListOf<TextView>()
    private var curFocus: TextView? = null
    private var inputData: String = ""
    private lateinit var onComplete: (Boolean) -> Unit

    fun setOnComplete(onComplete: (Boolean) -> Unit) {
        this.onComplete = onComplete
    }

    constructor(context: Context?) : super(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_auto_code, this)
        textViews.add(view.tv_1)
        textViews.add(view.tv_2)
        textViews.add(view.tv_3)
        textViews.add(view.tv_4)
        textViews.add(view.tv_5)
        textViews.add(view.tv_6)
        curFocus = view.tv_1
        tvSetFocus(0)
        view.et_code.addTextChangedListener(this)
    }

    fun getText(): String {
        return et_code.text.toString()
    }

    override fun afterTextChanged(p0: Editable?) {
        if (this::onComplete.isLateinit) {
            onComplete(inputData.length == textViews.size)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, start: Int, end: Int, count: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s != null && !TextUtils.isEmpty(s.toString())) {
            //有验证码的情况
            inputData = s.toString()

            //如果是最后一位验证码，焦点在最后一个，否者在下一位
            if (inputData.length == textViews.size) {
                tvSetFocus(textViews.size - 1)
            } else {
                tvSetFocus(inputData.length)
            }

            //给textView设置数据
            for (i in inputData.indices) {
                textViews[i].text = inputData.substring(i, i + 1)
            }
            for (i in inputData.length until textViews.size) {
                textViews[i].setText("")
            }
        } else {
            //一位验证码都没有的情况
            tvSetFocus(0)
            for (i in 0 until textViews.size) {
                textViews[i].text = ""
            }
        }
    }

    private fun tvSetFocus(i: Int) {
        val view = textViews[i]
        for (v in textViews) {
            v.setBackgroundResource(R.drawable.auto_code_bg_false)
        }
        view.setBackgroundResource(R.drawable.auto_code_bg_true)
    }
}