package com.changsune.changsu.yeontalk_kotlin.util.dialogs_manager.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.changsune.changsu.yeontalk_kotlin.R
import com.changsune.changsu.yeontalk_kotlin.view.intro.IntroActivity

class ServerErrorInIntroDialogFragment : DialogFragment() {

    var dd = MutableLiveData<Boolean>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alertDialogBuilder = AlertDialog.Builder(activity)

        alertDialogBuilder.setMessage(R.string.server_error_in_intro_dialog_message)

        alertDialogBuilder.setNegativeButton(R.string.server_error_in_intro_dialog_negative_button_caption) { dialog, which ->
            dd.value = true
        }

        alertDialogBuilder.setPositiveButton(R.string.server_error_in_intro_dialog_positive_button_caption) { dialog, which ->
            System.exit(0)
        }

        return alertDialogBuilder.create()

    }

    companion object {

        fun newInstance(): ServerErrorInIntroDialogFragment {

            val serverErrorInIntroDialogFragment = ServerErrorInIntroDialogFragment()
            serverErrorInIntroDialogFragment.isCancelable = false
            return serverErrorInIntroDialogFragment
        }
    }
}