package com.whow.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.whow.app.databinding.FragmentSecondBinding
import com.whow.promptdialog.PromptDialogInterface
import com.whow.promptdialog.view.PromptDialog

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
            showDialog(PromptDialog.CHOICE_TYPE_SINGLE)
        }

        secondBinding.tvDialogMultiChoice.setOnClickListener {
            showDialog(PromptDialog.CHOICE_TYPE_MULTI)
        }
    }


    private fun showDialog(dialogType: Int) {
        val list = mutableListOf<String>()
        for (index in 1..1000) {
            list.add("账户余额 = $index")
        }
        val drawable = ContextCompat.getDrawable(
            requireContext(),
            com.whow.promptdialog.R.drawable.single_choice_selector
        )
        val promptDialog = PromptDialog.Builder(requireContext()).apply {
            setTitle("提示")
            setNegativeButton("取消")
            setPositiveButton("确定")
            setChoiceItems(
                items = list,
                choiceType = dialogType,
                defaultSelectID = 0,
                checkMarkDrawable = drawable,
                listener = object : PromptDialogInterface.OnClickItemListener {
                    override fun onClickItem(choicePosition: Set<Int>) {
                        Toast.makeText(context, "选中的条目 = $choicePosition", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }.show()
    }
}