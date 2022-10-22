package com.whow.promptdialog.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.whow.promptdialog.R
import com.whow.promptdialog.controller.LoadingController

class LoadingDialog private constructor(context: Context) : Dialog(context) {

    private lateinit var messageText: TextView
    private lateinit var loadingImage: ImageView
    private var loadingController: LoadingController? = null

    companion object {
        private const val MESSAGE_TEXT_SIZE = 20f
    }

    private fun rotationAnimation(imageView: ImageView) {
        ObjectAnimator.ofFloat(imageView, "rotation", 0f, 359f).apply {
            repeatCount = ValueAnimator.INFINITE
            duration = 2000
            interpolator = LinearInterpolator()
            start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading_layout)
        messageText = findViewById(R.id.tv_tips)
        loadingImage = findViewById(R.id.iv_loading)
        initView()
    }

    private fun initView() {
        loadingController?.apply {
            messageText.visibility = View.GONE
            mMessage?.let {
                messageText.visibility = View.VISIBLE
                messageText.text = it
            }
            mTextSize?.let { messageText.textSize = it }
            loadingDrawable?.let { loadingImage.setImageDrawable(it) }
        }
    }

    override fun onStart() {
        super.onStart()
        window?.apply {
            // 使DialogFragment背景透明
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    class Builder(context: Context) {
        private var mContext: Context
        private var mMessage: CharSequence? = null
        private var mTextColor: Int? = null
        private var mTextSize: Float? = null
        private var loadingDrawable: Drawable? = null
        private var mCancelable: Boolean = true

        init {
            mContext = context
        }
        fun setCancelable(cancelable: Boolean) {
            mCancelable = cancelable
        }

        fun setMessage(
            message: CharSequence,
            @ColorInt textColor: Int = Color.BLACK,
            textSize: Float = MESSAGE_TEXT_SIZE
        ) {
            mMessage = message
            mTextColor = textColor
            mTextSize = textSize
        }

        fun setLoadingIcon(@DrawableRes iconId: Int) {
            loadingDrawable = ContextCompat.getDrawable(mContext, iconId)
        }

        fun setLoadingIcon(icon: Drawable) {
            loadingDrawable = icon
        }

        private fun create(): LoadingDialog {
            val loadingController = LoadingController()
            val loadingDialog = LoadingDialog(mContext)
            loadingDialog.setCancelable(mCancelable)
            loadingController.let {
                it.mMessage = this@Builder.mMessage
                it.mTextColor = this@Builder.mTextColor
                it.mTextSize = this@Builder.mTextSize
                it.loadingDrawable = this@Builder.loadingDrawable
            }
            loadingDialog.loadingController = loadingController
            return loadingDialog
        }

        fun show(): LoadingDialog {
            val dialog = create()
            dialog.show()
            return dialog
        }
    }

    override fun show() {
        super.show()
        rotationAnimation(loadingImage)
    }
}