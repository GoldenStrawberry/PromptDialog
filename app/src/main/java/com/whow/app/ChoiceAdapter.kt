package com.whow.app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ChoiceAdapter(val choiceType: Int) : ListAdapter<ChoiceBean, ChoiceAdapter.ViewHolder>(DefaultDiffCallback()) {

    companion object {
        const val CHOICE_TYPE_SINGLE = 0
        const val CHOICE_TYPE_MULTI = 1
    }
    private var mClickListener: View.OnClickListener? = null

    private var lastIndex = -1
    private var currentIndex = -1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var checkView: CheckedTextView = view.findViewById<CheckedTextView>(R.id.checkView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        if (choiceType == CHOICE_TYPE_MULTI) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_multi_choice, parent, false)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_single_choice, parent, false)
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val choiceBean = getItem(position)
        holder.checkView.text = choiceBean.title
        if (choiceType == CHOICE_TYPE_MULTI) {
            holder.checkView.isChecked = choiceBean.checkStatus
        } else {
            if (lastIndex == position) {
                holder.checkView.isChecked = false
            }
            holder.checkView.isChecked = currentIndex == position
        }
        holder.checkView.setOnClickListener {
            choiceBean.checkStatus = !choiceBean.checkStatus
            lastIndex = currentIndex
            currentIndex = position
            notifyItemChanged(lastIndex)
            notifyItemChanged(currentIndex)
        }
    }

    fun setOnclickListener(listener: View.OnClickListener?) {
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