package com.changsune.changsu.yeontalk_kotlin.view.user_details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.changsune.changsu.yeontalk_kotlin.Constants
import com.changsune.changsu.yeontalk_kotlin.R
import com.changsune.changsu.yeontalk_kotlin.util.image_loader.loadImage
import com.changsune.changsu.yeontalk_kotlin.view.home.HomeActivity
import com.changsune.changsu.yeontalk_kotlin.viewmodel.user_details.UserDetailsViewModel
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.layout_top_toolbar.*

class UserDetailsActivity : AppCompatActivity() {

    lateinit var viewModel: UserDetailsViewModel

    lateinit var userId: String

    lateinit var mGalleryImageRecyclerViewAdapter: GalleryImageRecyclerViewAdapter

    var isFavorite: Boolean = false

    companion object {

        val EXTRA_USER_ID = "EXTRA_USER_ID"
        val EXTRA_IS_FAVORITE = "EXTRA_IS_FAVORITE"

        fun start(context: Context, userId: String, isFavorite: Boolean) {
            val intent = Intent(context, UserDetailsActivity::class.java)
            intent.putExtra(EXTRA_USER_ID, userId)
            intent.putExtra(EXTRA_IS_FAVORITE, isFavorite)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        userId = intent.extras!!.getString(EXTRA_USER_ID)
        isFavorite = intent.extras!!.getBoolean(EXTRA_IS_FAVORITE)

        recyclerview_gallery_image_list_userdetails_id.post{
            val width_recyclerview = recyclerview_gallery_image_list_userdetails_id.getWidth()
            val params = recyclerview_gallery_image_list_userdetails_id.getLayoutParams()
            params.height = width_recyclerview * 1 / 5
            recyclerview_gallery_image_list_userdetails_id.setLayoutParams(params)
        }

        setUpButtons()

        recyclerview_gallery_image_list_userdetails_id.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            mGalleryImageRecyclerViewAdapter = GalleryImageRecyclerViewAdapter(context, arrayListOf(), this)
            adapter = mGalleryImageRecyclerViewAdapter
        }

        viewModel = ViewModelProviders.of(this).get(UserDetailsViewModel::class.java)

        viewModel.refresh(userId)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userDetails.observe(this, Observer { userDetails ->
            userDetails?.let {

                if (it.userProfileImage.equals("")) {
                    imageview_userdetails_user_image_id.setImageResource(R.drawable.ic_person_black_24dp)

                } else {
                    imageview_userdetails_user_image_id.loadImage(it.userProfileImage)
                }

                mGalleryImageRecyclerViewAdapter.updateImages(it.userImages!!)
                textview_userdetails_nickname_id.text = it.userNickName
                textview_userdetails_age_id.text = ((Constants.CURRENT_YEAR_PLUS_ONE?.minus((it.userBirthYear)!!.toInt())).toString() + Constants.AGE_KOREAN)
                textview_userdetails_gender_id.text = it.userGender
                textview_userdetails_login_time_id.text = it.userLoginTime
                textview_userdetails_nation_id.text = it.userNation
                textview_userdetails_region_id.text = it.userRegion
                textview_userdetails_introduction_id.text = it.userIntroduction
                textview_toolbar_title.text = it.userNickName
            }
        })

        viewModel.isFavorite.observe(this, Observer { isFavorite ->
            isFavorite?.let {
                if (it) {
                    button_insert_favorites_id.visibility = View.GONE
                    button_delete_favorites_id.visibility = View.VISIBLE
                } else {
                    button_insert_favorites_id.visibility = View.VISIBLE
                    button_delete_favorites_id.visibility = View.GONE
                }
            }
        })
    }

    private fun setUpButtons() {
        if (isFavorite) {
            button_insert_favorites_id.visibility = View.GONE
            button_delete_favorites_id.visibility = View.VISIBLE
        } else {
            button_insert_favorites_id.visibility = View.VISIBLE
            button_delete_favorites_id.visibility = View.GONE
        }

        button_insert_favorites_id.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                viewModel.onInsertFavoriteButtonClicked(userId)

            }

        })

        button_delete_favorites_id.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.onDeleteFavoriteButtonClicked(userId)
            }

        })
    }
}
