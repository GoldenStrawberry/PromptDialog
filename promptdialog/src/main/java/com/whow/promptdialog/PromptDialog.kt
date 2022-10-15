package com.whow.promptdialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.time.format.TextStyle

class PromptDialog private constructor() : DialogFragment(), PromptDialogInterface {

    private var mController: PromptController? = null

    companion object {
        private const val TITLE_TEXT_SIZE = 18f
        private const val MESSAGE_TEXT_SIZE = 15f
        private const val BUTTON_TEXT_SIZE = 16f
    }

    class Builder(context: Context) {

        private var mContext: Context
        private var mCancelable: Boolean = true
        private var mTitle: CharSequence? = null
        private var mTitleColor: Int? = null
        private var mTitleSize: Float? = null

        private var mMessage: CharSequence? = null
        private var mMessageColor: Int? = null
        private var mMessageSize: Float? = null

        private var mPositiveButtonText: CharSequence? = null
        private var mPositiveButtonColor: Int? = null
        private var mPositiveButtonSize: Float? = null

        private var mNegativeButtonText: CharSequence? = null
        private var mNegativeButtonColor: Int? = null
        private var mNegativeButtonSize: Float? = null

        private var mButtonVisible: Pair<Int, Boolean>? = null
        var mPositiveListener: PromptDialogInterface.OnClickListener? = null
        var mNegativeListener: PromptDialogInterface.OnClickListener? = null

        private var BUTTON_POSITIVE_TEXT_COLOR: Int
        private var BUTTON_NEGATIVE_TEXT_COLOR: Int

        init {
            mContext = context
            BUTTON_POSITIVE_TEXT_COLOR = mContext.resources.getColor(R.color.color_btn_confirm, null)
            BUTTON_NEGATIVE_TEXT_COLOR = mContext.resources.getColor(R.color.color_btn_cancel, null)
        }

        fun setCancelable(cancelable: Boolean) {
            mCancelable = cancelable
        }

        fun setTitle(
            title: CharSequence,
            @ColorInt textColor: Int = Color.BLACK,
            textSize: Float = TITLE_TEXT_SIZE
        ) {
            this.mTitle = title
            this.mTitleColor = textColor
            this.mTitleSize = textSize
        }

        fun setTitle(
            @StringRes titleId: Int,
            @ColorInt textColor: Int = Color.BLACK,
            textSize: Float = TITLE_TEXT_SIZE
        ) {
            this.setTitle(mContext.getString(titleId), textColor, textSize)
        }

        fun setMessage(
            message: CharSequence,
            @ColorInt textColor: Int = Color.BLACK,
            textSize: Float = MESSAGE_TEXT_SIZE
        ) {
            this.mMessage = message
            this.mMessageColor = textColor
            this.mMessageSize = textSize
        }

        fun setMessage(
            @StringRes messageId: Int,
            @ColorInt textColor: Int = Color.BLACK,
            textSize: Float = MESSAGE_TEXT_SIZE
        ) {
            setMessage(mContext.getString(messageId), textColor, textSize)
        }

        fun setPositiveButton(
            positiveText: CharSequence,
            @ColorInt textColor: Int = BUTTON_POSITIVE_TEXT_COLOR,
            textSize: Float = BUTTON_TEXT_SIZE
        ) {
            this.mPositiveButtonText = positiveText
            this.mPositiveButtonColor = textColor
            this.mPositiveButtonSize = textSize
        }

        fun setPositiveButton(
            @StringRes positiveTextId: Int,
            @ColorInt textColor: Int = BUTTON_POSITIVE_TEXT_COLOR,
            textSize: Float = BUTTON_TEXT_SIZE
        ) {
            setPositiveButton(mContext.getString(positiveTextId), textColor, textSize)
        }

        fun setPositiveButton(
            @StringRes textId: Int,
            @ColorInt textColor: Int = BUTTON_POSITIVE_TEXT_COLOR,
            textSize: Float = BUTTON_TEXT_SIZE,
            listener: PromptDialogInterface.OnClickListener
        ) {
            setPositiveButton(textId, textColor, textSize)
            setClickListener(PromptDialogInterface.BUTTON_POSITIVE, listener)
        }

        fun setPositiveButton(
            positiveText: CharSequence,
            @ColorInt textColor: Int = BUTTON_POSITIVE_TEXT_COLOR,
            textSize: Float = BUTTON_TEXT_SIZE,
            listener: PromptDialogInterface.OnClickListener
        ) {
            setPositiveButton(positiveText, textColor, textSize)
            setClickListener(PromptDialogInterface.BUTTON_POSITIVE, listener)
        }

        fun setNegativeButton(
            negativeText: CharSequence,
            @ColorInt textColor: Int = BUTTON_NEGATIVE_TEXT_COLOR,
            textSize: Float = BUTTON_TEXT_SIZE
        ) {
            this.mNegativeButtonText = negativeText
            this.mNegativeButtonColor = textColor
            this.mNegativeButtonSize = textSize
        }

        fun setNegativeButton(
            @StringRes negativeTextId: Int,
            @ColorInt textColor: Int = BUTTON_NEGATIVE_TEXT_COLOR,
            textSize: Float = BUTTON_TEXT_SIZE
        ) {
            setNegativeButton(mContext.getString(negativeTextId), textColor, textSize)
        }

        fun setNegativeButton(
            @StringRes textId: Int,
            @ColorInt textColor: Int = BUTTON_NEGATIVE_TEXT_COLOR,
            textSize: Float = BUTTON_TEXT_SIZE,
            listener: PromptDialogInterface.OnClickListener
        ) {
            setNegativeButton(textId, textColor, textSize)
            setClickListener(PromptDialogInterface.BUTTON_NEGATIVE, listener)
        }

        fun setNegativeButton(
            negativeText: CharSequence,
            @ColorInt textColor: Int = BUTTON_NEGATIVE_TEXT_COLOR,
            textSize: Float = BUTTON_TEXT_SIZE,
            listener: PromptDialogInterface.OnClickListener
        ) {
            setNegativeButton(negativeText, textColor, textSize)
            setClickListener(PromptDialogInterface.BUTTON_NEGATIVE, listener)
        }

        private fun setClickListener(which: Int, listener: PromptDialogInterface.OnClickListener) {
            if (which == PromptDialogInterface.BUTTON_POSITIVE) {
                this.mPositiveListener = listener
            } else {
                this.mNegativeListener = listener
            }
        }

        private fun setButtonVisible(which: Int, visible: Boolean) {
            this.mButtonVisible = Pair(which, visible)
        }

        private fun create(): PromptDialog {
            val promptDialog = PromptDialog()
            promptDialog.isCancelable = mCancelable
            promptDialog.setController(initController(promptDialog))
            return promptDialog
        }

        fun show(tag: String? = null): PromptDialog {
            val dialog = create()
            val fragmentManager = (mContext as FragmentActivity).supportFragmentManager
            dialog.show(fragmentManager, tag)
            return dialog
        }

        private fun initController(promptInterface: PromptDialogInterface): PromptController {
            return PromptController(mContext, promptInterface).apply {
                mTitle = this@Builder.mTitle
                mTitleColor = this@Builder.mTitleColor
                mTitleSize = this@Builder.mTitleSize

                mMessage = this@Builder.mMessage
                mMessageColor = this@Builder.mMessageColor
                mMessageSize = this@Builder.mMessageSize

                mPositiveButtonText = this@Builder.mPositiveButtonText
                mPositiveButtonColor = this@Builder.mPositiveButtonColor
                mPositiveButtonSize = this@Builder.mPositiveButtonSize

                mNegativeButtonText = this@Builder.mNegativeButtonText
                mNegativeButtonColor = this@Builder.mNegativeButtonColor
                mNegativeButtonSize = this@Builder.mNegativeButtonSize

                mNegativeListener = this@Builder.mNegativeListener
                mPositiveListener = this@Builder.mPositiveListener
                mButtonVisible = this@Builder.mButtonVisible
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mController?.selectDialogView()
    }

    override fun onStart() {
        super.onStart()
        // 使DialogFragment背景透明
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun setController(controller: PromptController) {
        mController = controller
    }

    override fun dismissDialog() {
        super.dismiss()
    }
}