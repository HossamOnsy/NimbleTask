package com.sam.nimbletask.utils

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar


class CenterTitleToolbar : Toolbar {
    private var titleTextView: AppCompatTextView? = null


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setTitle(title: CharSequence) {
        if (!TextUtils.isEmpty(title)) {
            if (titleTextView == null) {
                val context = context
                titleTextView = AppCompatTextView(context)
                titleTextView!!.setSingleLine()
                titleTextView!!.ellipsize = TextUtils.TruncateAt.END
                val lp = generateDefaultLayoutParams()
                lp.gravity = Gravity.CENTER
                titleTextView!!.layoutParams = lp
            }
            if (titleTextView!!.parent !== this) {
                addSystemView(titleTextView!!)
            }
        } else if (titleTextView != null && titleTextView!!.parent === this) {
            removeView(titleTextView)
        }
        if (titleTextView != null) {
            titleTextView!!.text = title
        }
    }

    private fun addSystemView(v: View) {
        val vlp = v.layoutParams
        val lp: Toolbar.LayoutParams
        if (vlp == null) {
            lp = generateDefaultLayoutParams()
        } else if (!checkLayoutParams(vlp)) {
            lp = generateLayoutParams(vlp)
        } else {
            lp = vlp as Toolbar.LayoutParams
        }
        addView(v, lp)
    }

}