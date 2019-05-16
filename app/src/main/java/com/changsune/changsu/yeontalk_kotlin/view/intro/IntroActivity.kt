package com.changsune.changsu.yeontalk_kotlin.view.intro

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.changsune.changsu.yeontalk_kotlin.R
import com.changsune.changsu.yeontalk_kotlin.util.dialogs_manager.DialogsManager
import com.changsune.changsu.yeontalk_kotlin.util.dialogs_manager.dialogs.ServerErrorInIntroDialogFragment
import com.changsune.changsu.yeontalk_kotlin.view.home.HomeActivity
import com.changsune.changsu.yeontalk_kotlin.viewmodel.intro.IntroViewModel

class IntroActivity : AppCompatActivity() {

    val TAG = "IntroActivity"

    lateinit var viewModel: IntroViewModel

    private val mDialogsManager =  DialogsManager(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        viewModel = ViewModelProviders.of(this).get(IntroViewModel::class.java)
        viewModel.refresh()

        observeViewModel()

    }

    private fun observeViewModel() {

        viewModel.resultOfConfirmRegistration.observe(this, Observer { result ->
            result?.let {
                if(it.equals("Registered")) {
                    val intent = Intent(this, HomeActivity::class.java)
                    this.startActivity(intent)
                    finish()
                } else if (it.equals("Blocked")) {
                    Log.e(TAG, "Blocked")

                } else {
//                    RegistrationIndexActivity.start(this@IndexActivity)
//                    finish()
                    Log.e(TAG, "Register")
                }
            }
        })

        viewModel.confirmRegistrationError.observe(this, Observer { isError ->
            isError?.let {
                if(it) {
                    mDialogsManager.showDialogWithId(ServerErrorInIntroDialogFragment.newInstance(), "")
                }
            }
        })
    }

}
