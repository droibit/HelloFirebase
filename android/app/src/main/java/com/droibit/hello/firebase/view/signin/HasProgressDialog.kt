package com.droibit.hello.firebase.view.signin

import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.view.signin.HasProgressDialog.Companion.TAG_PROGRESS_DIALOG

interface HasProgressDialog {

    companion object {

        val TAG_PROGRESS_DIALOG = "TAG_PROGRESS_DIALOG"
    }

    fun FragmentActivity.showProgress()

    fun FragmentActivity.hideProgress()
}

class HasProgressDialogImpl : HasProgressDialog {

    override fun FragmentActivity.showProgress() {
        if (supportFragmentManager.findFragmentByTag(TAG_PROGRESS_DIALOG) == null) {
            val f = ProgressDialogFragment.newInstance(getString(R.string.sign_in_progress_sign_up))
            f.isCancelable = false
            f.show(supportFragmentManager, TAG_PROGRESS_DIALOG)
        }
    }

    override fun FragmentActivity.hideProgress() {
        val f = supportFragmentManager.findFragmentByTag(TAG_PROGRESS_DIALOG) as? DialogFragment
        f?.dismiss()
    }
}