package com.droibit.hello.firebase.view

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

class ProgressDialogFragment : DialogFragment() {

    companion object {

        private val ARG_MESSAGE = "ARG_MESSAGE"

        @JvmStatic
        fun newInstance(message: String) : ProgressDialogFragment {
            return ProgressDialogFragment().also {
                it.arguments = Bundle(1).apply {
                    putString(ARG_MESSAGE, message)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return ProgressDialog(context).apply {
            setMessage(arguments.getString(ARG_MESSAGE))
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setCanceledOnTouchOutside(false)
        }
    }
}