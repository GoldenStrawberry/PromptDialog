package com.whow.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SingleChoiceAdapter() : ListAdapter<String, SingleChoiceAdapter.ViewHolder>(DefaultDiffCallback()) {

    private var mClickListener: View.OnClickListener? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var checkView: CheckedTextView = view.findViewById<CheckedTextView>(R.id.checkView)
        init {
            checkView.setOnClickListener {
                checkView.toggle()
                mClickListener?.onClick(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.dialog_singlechoice, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkView.text = getItem(position)
    }

    fun setOnclickListener(listener: View.OnClickListener?) {
        mClickListener = listener
    }
}

private class DefaultDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}