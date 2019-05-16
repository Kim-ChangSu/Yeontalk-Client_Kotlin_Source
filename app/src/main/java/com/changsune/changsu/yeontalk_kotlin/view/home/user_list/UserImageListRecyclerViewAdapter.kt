package com.changsune.changsu.yeontalk_kotlin.view.home.user_list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

import com.changsune.changsu.yeontalk_kotlin.R
import com.changsune.changsu.yeontalk_kotlin.model.user.User
import com.changsune.changsu.yeontalk_kotlin.util.image_loader.loadImage
import kotlinx.android.synthetic.main.layout_user_image_list_item.view.*


class UserImageListRecyclerViewAdapter(val context: Context, val list: ArrayList<User>) : RecyclerView.Adapter<UserImageListRecyclerViewAdapter.ViewHolder>() {


    fun updateUserImage(newUsers: List<User>) {
        list.clear()
        list.addAll(newUsers)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var imageView = view.imageview_user_image

        fun bind(context: Context, user: User) {
            val imageWidth = context.resources.displayMetrics.widthPixels / 3
            val layoutParams = RelativeLayout.LayoutParams(imageWidth, imageWidth)
            imageView!!.layoutParams = layoutParams
            imageView.loadImage(user.userProfileImage)

            itemView.setOnClickListener {  }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_user_image_list_item, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position])
    }


    override fun getItemCount(): Int = list.size

    companion object {
        private val TAG = UserImageListRecyclerViewAdapter::class.java.simpleName
    }

}