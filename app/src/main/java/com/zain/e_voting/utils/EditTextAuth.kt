package com.zain.e_voting.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.zain.e_voting.R

class EditTextAuth : AppCompatEditText {
    private lateinit var editTextBackground: Drawable
    private lateinit var errorBackground: Drawable
    private var isError = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        background = editTextBackground
        background = if (isError) errorBackground else editTextBackground
    }

    private fun init() {
        editTextBackground = ContextCompat.getDrawable(context, R.drawable.bg_edit_text) as Drawable
        errorBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_edit_text_error) as Drawable


        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                isError = s.toString().isEmpty()


            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })

    }
}