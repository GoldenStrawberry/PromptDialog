package com.whow.promptdialog

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

open class TextDialogController(context: Context, promptInterface: PromptDialogInterface) :
    DialogController(context) {

    protected val mDialogInterface: PromptDialogInterface
    var mTitle: CharSequence? = null
    var mTitleColor: Int? = null
    var mTitleSize: Float? = null

    var mMessage: CharSequence? = null
    var mMessageColor: Int? = null
    var mMessageSize: Float? = null

    private var positiveButton: TextView? = null
    var mPositiveButtonColor: Int? = null
    var mPositiveButtonSize: Float? = null
    var mPositiveButtonText: CharSequence? = null

    private var negativeButton: TextView? = null
    var mNegativeButtonColor: Int? = null
    var mNegativeButtonSize: Float? = null
    var mNegativeButtonText: CharSequence? = null

    var mButtonVisible: Pair<Int, Boolean>? = null
    var mPositiveListener: PromptDialogInterface.OnClickListener? = null
    var mNegativeListener: PromptDialogInterface.OnClickListener? = null

    private var lineView1: View? = null
    private var lineView2: View? = null

    init {
        mDialogInterface = promptInterface
    }

    override fun getDialogView(): View {
        return getMessageDialog()
    }

    private fun getMessageDialog(): View {
        val rootView = mInflater.inflate(R.layout.dialog_material_layout, null)
        val titleTextView = rootView.findViewById<TextView>(R.id.tv_title)
        positiveButton = rootView.findViewById<TextView>(R.id.tv_confirm)
        negativeButton = rootView.findViewById<TextView>(R.id.tv_cancel)
        lineView1 = rootView.findViewById<View>(R.id.line1)
        lineView2 = rootView.findViewById<View>(R.id.line2)

        rootView.findViewById<NestedScrollView>(R.id.scrollView_container).visibility = View.VISIBLE
        rootView.findViewById<RecyclerView>(R.id.rcy_list).visibility = View.GONE

        titleTextView.visibility = View.GONE
        titleTextView.apply {
            mTitle?.let {
                visibility = View.VISIBLE
                text = it
            }
            mTitleColor?.let { setTextColor(it) }
            mTitleSize?.let { textSize = it }
        }
        rootView.findViewById<TextView>(R.id.tv_message).apply {
            mMessage?.let { text = it }
            mMessageColor?.let { setTextColor(it) }
            mMessageSize?.let { textSize = it }
        }

        positiveButton?.visibility = View.GONE
        positiveButton?.apply {
            mPositiveButtonText?.let {
                visibility = View.VISIBLE
                text = it
            }
            mPositiveButtonColor?.let { setTextColor(it) }
            mPositiveButtonSize?.let { textSize = it }
        }

        negativeButton?.visibility = View.GONE
        negativeButton?.apply {
            mNegativeButtonText?.let {
                visibility = View.VISIBLE
                text = it
            }
            mNegativeButtonColor?.let { setTextColor(it) }
            mNegativeButtonSize?.let { textSize = it }
        }

        setButtonClickListener()
        mButtonVisible?.let { setButtonVisible(it) }
        lineViewVisible()

        return rootView
    }

    private fun setButtonClickListener() {
        positiveButton?.setOnClickListener {
            mDialogInterface.dismissDialog()
            mPositiveListener?.onClick(mDialogInterface, PromptDialogInterface.BUTTON_POSITIVE)
        }
        negativeButton?.setOnClickListener {
            mDialogInterface.dismissDialog()
            mNegativeListener?.onClick(mDialogInterface, PromptDialogInterface.BUTTON_NEGATIVE)
        }
    }

    private fun setButtonVisible(pair: Pair<Int, Boolean>) {
        if (pair.first == PromptDialogInterface.BUTTON_POSITIVE) {
            positiveButton?.let {
                if (pair.second) it.visibility = View.VISIBLE else it.visibility = View.GONE
            }
        } else if (pair.first == PromptDialogInterface.BUTTON_NEGATIVE) {
            negativeButton?.let {
                if (pair.second) it.visibility = View.VISIBLE else it.visibility = View.GONE
            }
        }
        lineViewVisible()
    }

    private fun lineViewVisible() {
        if (positiveButton != null && negativeButton != null) {
            if (positiveButton!!.isVisible && negativeButton!!.isVisible) {
                lineView1?.visibility = View.VISIBLE
                lineView2?.visibility = View.VISIBLE
            } else if (!positiveButton!!.isVisible || !negativeButton!!.isVisible) {
                lineView2?.visibility = View.GONE
            } else {
                lineView1?.visibility = View.GONE
            }
        }
    }
}