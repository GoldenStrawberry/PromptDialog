package com.whow.promptdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View

sealed class DialogController(context: Context) {
    protected val mInflater: LayoutInflater
    protected val mContext: Context

    init {
        mContext = context
        mInflater = LayoutInflater.from(context)
    }

    abstract fun getDialogView(): View
}
