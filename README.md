# PromptDialog
PromptDialog是使用Kotlin编写一个对话框库。在开发项目中常常需要编写自定义Material风格的对话框，
为了避免重复'造轮子'，所以仿照AlertDialog编写了一个Material风格的对话框

# 使用
`
PromptDialog.Builder(requireContext()).apply {
    setTitle("提示",  textSize = 22f)
    setMessage("是否跳转到下个页面？", textSize = 18f)
    setPositiveButton(positiveText = "确定", textSize = 20f, listener = object : PromptDialogInterface.OnClickListener{
        override fun onClick(dialog: PromptDialogInterface, which: Int) {
            Toast.makeText(requireContext(), "点击了确定", Toast.LENGTH_SHORT).show()
        }
    })
    setNegativeButton(negativeText = "取消", textSize = 20f, listener = object : PromptDialogInterface.OnClickListener{
        override fun onClick(dialog: PromptDialogInterface, which: Int) {
            Toast.makeText(requireContext(), "点击了取消", Toast.LENGTH_SHORT).show()
        }
    })
    setCancelable(false)
}.show("1")
`

# 开发完善中...