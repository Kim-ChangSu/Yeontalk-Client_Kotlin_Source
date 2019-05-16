package com.changsune.changsu.yeontalk_kotlin.view.home.user_list

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.changsune.changsu.yeontalk_kotlin.Constants
import com.changsune.changsu.yeontalk_kotlin.R
import com.changsune.changsu.yeontalk_kotlin.model.user.User
import com.changsune.changsu.yeontalk_kotlin.util.image_loader.loadImage
import com.changsune.changsu.yeontalk_kotlin.view.user_details.UserDetailsActivity

import kotlinx.android.synthetic.main.layout_user_list_item.view.*

class UserListRecyclerViewAdapter(var context: Context, var list: ArrayList<User>, var isFavorite: Boolean) : RecyclerView.Adapter<UserListRecyclerViewAdapter.ViewHolder>() {

    fun updateUsers(newUsers: List<User>) {
        list.clear()
        list.addAll(newUsers)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Todo Butterknife bindings

        private var imageView_image = view.imageview_userimage
        private var textview_nickname = view.textview_usernickname
        private  var textview_gender = view.textview_usergender
        private var textview_age = view.textview_user_age
        private var textview_introduction = view.textview_userintroduction

        fun bind(context: Context, user: User, isFavorite: Boolean) {

            textview_nickname.text = user.userNickname
            textview_gender.text = user.userGender

            if (user.userGender == "여자") {
                textview_gender.setTextColor(Color.parseColor("#ff69b4"))
            } else {
                textview_gender.setTextColor(Color.parseColor("#0000ff"))
            }

            textview_age.text = (2020 - Integer.valueOf(user.userBirthYear)).toString() + Constants.AGE_KOREAN

            if (user.userIntroduction != "" && user.userIntroduction != null) {
                textview_introduction.visibility = View.VISIBLE
                textview_introduction.text = Html.fromHtml(user.userIntroduction)
            } else {
                textview_introduction.visibility = View.GONE
            }

            if (user.userProfileImage != "") {
                imageView_image.loadImage(user.userProfileImage)
            } else {
                imageView_image.setImageResource(R.drawable.ic_person_black_24dp)
            }

            itemView.setOnClickListener {
                UserDetailsActivity.start(context, user.userId!!, isFavorite)


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_user_list_item, parent, false)
    )

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position], isFavorite)
    }

    companion object {
        private val TAG = UserListRecyclerViewAdapter::class.java.simpleName
    }

}