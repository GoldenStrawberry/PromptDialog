package com.whow.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.whow.app.databinding.FragmentFirstBinding
import com.whow.promptdialog.PromptDialogInterface
import com.whow.promptdialog.view.PromptDialog

class FirstFragment : Fragment() {

    private lateinit var firstBinding: FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firstBinding = FragmentFirstBinding.inflate(layoutInflater)
        return firstBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstBinding.btnShowDialog.setOnClickListener {
            PromptDialog.Builder(requireContext()).apply {
                setTitle("提示",  textSize = 22f)
                setMessage("是否跳转到下个页面？", textSize = 18f)
                setPositiveButton(positiveText = "确定", textSize = 20f, listener = object : PromptDialogInterface.OnClickListener{
                    override fun onClick(dialog: PromptDialogInterface, which: Int) {
                        Toast.makeText(requireContext(), "点击了确定", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
                    }
                })
                setNegativeButton(negativeText = "取消", textSize = 20f, listener = object : PromptDialogInterface.OnClickListener{
                    override fun onClick(dialog: PromptDialogInterface, which: Int) {
                        Toast.makeText(requireContext(), "点击了取消", Toast.LENGTH_SHORT).show()
                    }
                })
                setCancelable(false)
            }.show("1")
        }
    }
}