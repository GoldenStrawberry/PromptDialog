package com.whow.promptdialog

interface PromptDialogInterface {

    companion object {
        /** The identifier for the positive button. */
        const val BUTTON_POSITIVE: Int  = -1
        /** The identifier for the negative button. */
        const val BUTTON_NEGATIVE: Int = -2
    }

    fun dismissDialog()

    interface OnClickListener {
        /**
         * This method will be invoked when a button in the dialog is clicked.
         *
         * @param dialog the dialog that received the click
         * @param which the button that was clicked (ex.
         *              {@link PromptDialogInterface#BUTTON_POSITIVE}) or the position
         *              of the item clicked
         */
        fun onClick(dialog: PromptDialogInterface, which: Int)
    }
}