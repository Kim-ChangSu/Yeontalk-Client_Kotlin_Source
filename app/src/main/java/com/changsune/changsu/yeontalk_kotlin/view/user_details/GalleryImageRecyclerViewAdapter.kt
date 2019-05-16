package com.changsune.changsu.yeontalk_kotlin.view.user_details

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout

import com.changsune.changsu.yeontalk_kotlin.R

import com.changsune.changsu.yeontalk_kotlin.util.image_loader.loadImage
import kotlinx.android.synthetic.main.layout_gallery_image_list_user_details_item.view.*

class GalleryImageRecyclerViewAdapter(var context: Context, var list: ArrayList<String>,
                                      private val mRecyclerView: RecyclerView) : RecyclerView.Adapter<GalleryImageRecyclerViewAdapter.ViewHolder>() {


    fun updateImages(newImages: ArrayList<String>) {
        list.clear()
        list.addAll(newImages)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var imageView = itemView.imageview_user_gallery_images_item_id

        fun bind(context: Context, model: String, recyclerView: RecyclerView) {

            recyclerView.post {

                val width_recyclerview = recyclerView.width
                val layoutParams = RelativeLayout.LayoutParams(width_recyclerview * 1 / 5, width_recyclerview * 1 / 5)
                imageView!!.layoutParams = layoutParams
                imageView.loadImage(model)

            }

            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.layout_gallery_image_list_user_details_item, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position], mRecyclerView)
    }


    override fun getItemCount(): Int = list.size


    companion object {

        private val TAG = GalleryImageRecyclerViewAdapter::class.java.simpleName
    }

}