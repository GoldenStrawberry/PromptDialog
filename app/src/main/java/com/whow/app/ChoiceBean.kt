package com.whow.app

import android.graphics.drawable.Drawable

data class ChoiceBean(val title: String, var checkStatus: Boolean = false) {
    var checkMarkDrawable: Drawable? = null
    var drawableRight: Drawable? = null
}
