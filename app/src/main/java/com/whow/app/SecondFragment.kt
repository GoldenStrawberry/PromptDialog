package com.whow.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.whow.app.databinding.FragmentSecondBinding
import com.whow.promptdialog.PromptDialog

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

        secondBinding.tvDialogSingleChoice.setOnClickListener {
            showDialog(ChoiceAdapter.CHOICE_TYPE_SINGLE)
        }

        secondBinding.tvDialogMultiChoice.setOnClickListener {
            showDialog(ChoiceAdapter.CHOICE_TYPE_MULTI)
        }
    }

    private fun showDialog(dialogType: Int) {
        val list = mutableListOf<ChoiceBean>()
        for (index in 1..1000) {
            list.add(ChoiceBean("账户余额 = $index"))
        }
        val choiceAdapter = ChoiceAdapter(dialogType)
        choiceAdapter.submitList(list)
        val promptDialog = PromptDialog.Builder(requireContext()).apply {
            setTitle("提示")
            setNegativeButton("取消")
            setPositiveButton("确定")
            setDialogAdapter(choiceAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        }.show()
    }
}