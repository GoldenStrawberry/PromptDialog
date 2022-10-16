package com.whow.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.whow.app.databinding.FragmentSecondBinding
import com.whow.promptdialog.PromptDialog
import kotlinx.coroutines.flow.flow

class SecondFragment : Fragment() {

    private lateinit var secondBinding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        secondBinding = FragmentSecondBinding.inflate(layoutInflater)
        return secondBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = mutableListOf<String>()
        for (index in 1..100) {
            list.add("账户余额 = $index")
        }
        secondBinding.tvDialogSingleChoice.setOnClickListener {
            val singleChoiceAdapter = SingleChoiceAdapter()
            singleChoiceAdapter.submitList(list)
            PromptDialog.Builder(requireContext()).apply {
                setTitle("提示")
                setNegativeButton("取消")
                setPositiveButton("确定")
                setDialogAdapter( singleChoiceAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
            }.show()
        }
    }
}