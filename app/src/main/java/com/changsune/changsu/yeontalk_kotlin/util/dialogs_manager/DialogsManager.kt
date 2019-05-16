package com.changsune.changsu.yeontalk_kotlin.util.dialogs_manager

import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.text.TextUtils

@UiThread
class DialogsManager(private val mFragmentManager: FragmentManager) {

    companion object {

        val ARGUMENT_DIALOG_ID = "ARGUMENT_DIALOG_ID"
        private val DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG"

    }
    var currentlyShownDialog: DialogFragment? = null
        private set


    val currentlyShownDialogId: String?
        get() = if (currentlyShownDialog == null || currentlyShownDialog!!.arguments == null ||
                !currentlyShownDialog!!.arguments!!.containsKey(ARGUMENT_DIALOG_ID)) {
            ""
        } else {
            currentlyShownDialog!!.arguments!!.getString(ARGUMENT_DIALOG_ID)
        }

    init {
        // there might be some dialog already shown
        val fragmentWithDialogTag = mFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG)
        if (fragmentWithDialogTag != null && DialogFragment::class.java.isAssignableFrom(fragmentWithDialogTag.javaClass)) {
            currentlyShownDialog = fragmentWithDialogTag as DialogFragment?
        }
    }

    fun isDialogCurrentlyShown(id: String): Boolean {
        val shownDialogId = currentlyShownDialogId
        return !TextUtils.isEmpty(shownDialogId) && shownDialogId == id
    }

    fun dismissCurrentlyShownDialog() {
        if (currentlyShownDialog != null) {
            currentlyShownDialog!!.dismissAllowingStateLoss()
            currentlyShownDialog = null
        }
    }

    fun showDialogWithId(dialog: DialogFragment, id: String?) {
        dismissCurrentlyShownDialog()
        setId(dialog, id)
        showDialog(dialog)
    }

    private fun setId(dialog: DialogFragment, id: String?) {
        val args = if (dialog.arguments != null) dialog.arguments else Bundle(1)
        args!!.putString(ARGUMENT_DIALOG_ID, id)
        dialog.arguments = args
    }

    private fun showDialog(dialog: DialogFragment) {
        mFragmentManager.beginTransaction()
                .add(dialog, DIALOG_FRAGMENT_TAG)
                .commitAllowingStateLoss()
        currentlyShownDialog = dialog
    }

}

