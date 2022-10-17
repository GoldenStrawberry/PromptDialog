package com.whow.promptdialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class PromptDialog private constructor() : DialogFragment(), PromptDialogInterface {

    private var mController: DialogController? = null

    companion object {
        private const val TITLE_TEXT_SIZE = 18f
        private const val MESSAGE_TEXT_SIZE = 15f
        private const val BUTTON_TEXT_SIZE = 16f

        private const val DIALOG_STYLE_MESSAGE = 0
        private const val DIALOG_STYLE_LIST = 1

        const val CHOICE_TYPE_SINGLE = 0
        const val CHOICE_TYPE_MULTI = 1
    }

    class Builder(context: Context) {

        private var dialogFlag: Int = 0
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

        private var mItems: List<String>? = null
        var mClickItemListener: PromptDialogInterface.OnClickItemListener? = null
        var mDialogAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

        init {
            mContext = context
            BUTTON_POSITIVE_TEXT_COLOR =
                mContext.resources.getColor(R.color.color_btn_confirm, null)
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
            dialogFlag = DIALOG_STYLE_MESSAGE
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

        fun <T: RecyclerView.ViewHolder>setDialogAdapter(adapter: RecyclerView.Adapter<T>) {
            dialogFlag = DIALOG_STYLE_LIST
            mDialogAdapter = adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
        }

        fun setChoiceItems(
            items: List<String>,
            choiceType: Int = CHOICE_TYPE_SINGLE,
            defaultSelectID: Int? = null,
            checkMarkDrawable: Drawable? = null,
            itemDrawableLeft: Drawable? = null,
            itemDrawableRight: Drawable? = null,
            listener: PromptDialogInterface.OnClickItemListener? = null
        ) {
            val list = mutableListOf<ChoiceBean>()
            var selectedIndex = -1
            if (defaultSelectID != null) {
                selectedIndex = defaultSelectID
            }
            for ((index, value) in items.withIndex()) {
                val choiceBean = ChoiceBean(value, false)
                choiceBean.checkMarkDrawable = checkMarkDrawable
                choiceBean.drawableLeft = itemDrawableLeft
                choiceBean.drawableRight = itemDrawableRight
                if (index == selectedIndex) {
                    choiceBean.checkStatus = true
                }
                list.add(choiceBean)
            }
            val choiceAdapter = ChoiceAdapter(choiceType)
            choiceAdapter.setOnclickListener(listener)
            choiceAdapter.submitList(list)
            setDialogAdapter(choiceAdapter)
        }

        private fun create(): PromptDialog {
            val promptDialog = PromptDialog()
            promptDialog.isCancelable = mCancelable
            val dialogController = if (dialogFlag == DIALOG_STYLE_LIST) {
                getListDialogController(promptDialog)
            } else {
                getTextDialogController(promptDialog)
            }
            promptDialog.setController(dialogController)
            return promptDialog
        }

        fun show(tag: String? = null): PromptDialog {
            val dialog = create()
            val fragmentManager = (mContext as FragmentActivity).supportFragmentManager
            dialog.show(fragmentManager, tag)
            return dialog
        }

        private fun getListDialogController(promptInterface: PromptDialogInterface): ListDialogController {
            return ListDialogController(
                mContext,
                promptInterface
            ).apply {
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

                mAdapter = this@Builder.mDialogAdapter
            }
        }

        private fun getTextDialogController(promptInterface: PromptDialogInterface): TextDialogController {
            return TextDialogController(mContext, promptInterface).apply {
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
        return mController?.getDialogView()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            // 使DialogFragment背景透明
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // 让底部虚拟按键消失
            addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
    }

    private fun setController(controller: DialogController) {
        mController = controller
    }

    override fun dismissDialog() {
        super.dismiss()
    }
}