package com.whow.promptdialog

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ChoiceAdapter(private val choiceType: Int) : ListAdapter<ChoiceBean, ChoiceAdapter.ViewHolder>(DefaultDiffCallback()) {

    companion object {
        private const val CHOICE_TYPE_SINGLE = 0
        private const val CHOICE_TYPE_MULTI = 1
    }

    private var mClickListener: PromptDialogInterface.OnClickItemListener? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var checkView: CheckedTextView = view.findViewById<CheckedTextView>(R.id.checkView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = if (choiceType == CHOICE_TYPE_MULTI) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_multi_choice, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_single_choice, parent, false)
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val choiceBean = getItem(position)
        if (choiceBean.drawableLeft != null && choiceBean.drawableRight != null) {
            holder.checkView.setCompoundDrawablesWithIntrinsicBounds(choiceBean.drawableLeft, null, choiceBean.drawableRight,null)
        } else {
            choiceBean.drawableLeft?.let {
                holder.checkView.setCompoundDrawablesWithIntrinsicBounds(it, null, null,null)
            }
            choiceBean.drawableRight?.let {
                holder.checkView.setCompoundDrawablesWithIntrinsicBounds(null, null, it,null)
            }
        }
        holder.checkView.text = choiceBean.title
        holder.checkView.isChecked = choiceBean.checkStatus
        holder.checkView.setOnClickListener {
            choiceBean.checkStatus = !choiceBean.checkStatus
            notifyItemChanged(position)
            if (choiceType == CHOICE_TYPE_SINGLE) {
                val lastChoiceBean = currentList.filter {
                    it.checkStatus && it != choiceBean
                }.getOrNull(0)
                if (lastChoiceBean != null) {
                    lastChoiceBean.checkStatus = !lastChoiceBean.checkStatus
                    notifyItemChanged(currentList.indexOf(lastChoiceBean))
                }
                mClickListener?.onClickItem(setOf(position))
            } else {
                val selectValue: MutableSet<Int> = mutableSetOf()
                currentList.filter { it.checkStatus }.forEach {
                    if (it.checkStatus) {
                        selectValue.add(currentList.indexOf(it))
                    }
                }
                mClickListener?.onClickItem(selectValue)
            }
        }
    }

    fun setOnclickListener(listener: PromptDialogInterface.OnClickItemListener?) {
        mClickListener = listener
    }
}

private class DefaultDiffCallback : DiffUtil.ItemCallback<ChoiceBean>() {

    override fun areItemsTheSame(oldItem: ChoiceBean, newItem: ChoiceBean): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChoiceBean, newItem: ChoiceBean): Boolean {
        return oldItem.title == newItem.title && oldItem.checkStatus == newItem.checkStatus
    }
}