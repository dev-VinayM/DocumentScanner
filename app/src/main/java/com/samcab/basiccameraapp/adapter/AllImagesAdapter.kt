package com.samcab.basiccameraapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.samcab.basiccameraapp.R
import com.samcab.basiccameraapp.listener.ViewItemClickListener
import com.samcab.basiccameraapp.utility.AppUtils
import java.io.File


class AllImagesAdapter(private val fileList: Array<File>,
                       private val viewItemClickListener: ViewItemClickListener) :
    RecyclerView.Adapter<AllImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_all_images_item, viewGroup, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivImageItem.setOnClickListener {
            viewItemClickListener.onItemClickClicked(position)
        }
        holder.ivImageItem.setImageBitmap(fileList[position].absolutePath.let { AppUtils.getBitmap(it) })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivImageItem : ImageView = view.findViewById(R.id.iv_imageItem)
    }
}