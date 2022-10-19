package com.whow.promptdialog.controller

import android.content.Context
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whow.promptdialog.PromptDialogInterface
import com.whow.promptdialog.R

class ListDialogController(
    context: Context,
    promptInterface: PromptDialogInterface
) : TextDialogController(context, promptInterface) {

    var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

    private var mRecyclerView: RecyclerView? = null

    override fun getDialogView(): View {
        val rootView = super.getDialogView()
        initRecyclerView(rootView)
        return rootView
    }

    private fun initRecyclerView(rootView: View) {
        val mContainerScrollView =
            rootView.findViewById<NestedScrollView>(R.id.scrollView_container)
        mRecyclerView = rootView.findViewById<RecyclerView>(R.id.rcy_list)
        mContainerScrollView.visibility = View.GONE
        mRecyclerView?.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(mContext).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            mAdapter?.let {
                adapter = it
            }
        }
    }

    override fun setButtonClickListener() {
        positiveButton?.setOnClickListener {
            mDialogInterface.dismissDialog()

            mPositiveListener?.onClick(mDialogInterface, PromptDialogInterface.BUTTON_POSITIVE)
        }
        negativeButton?.setOnClickListener {
            mDialogInterface.dismissDialog()
        }
    }
}