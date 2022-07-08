package com.example.nousdigitaltestbyhamza.ui.adupters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nousdigitaltestbyhamza.R
import com.example.nousdigitaltestbyhamza.databinding.RowPostBinding
import com.example.nousdigitaltestbyhamza.model.Post

class PostAdupter(val list: List<Post> ,private val onItemClicked: (Post,Int) -> Unit) : RecyclerView.Adapter<PostAdupter.ViewHolder>() {
    inner class ViewHolder(val binding : RowPostBinding): RecyclerView.ViewHolder(binding.root){
        fun bind()=binding.apply {
           Glide.with(binding.postImage).asBitmap()
               .load(list[adapterPosition].postImageUrl)
               .apply ( RequestOptions().error(R.drawable.ic_baseline_broken_image_24).placeholder(R.drawable.ic_baseline_image_24) )
               .into(binding.postImage)

            binding.postImage.setOnClickListener {
                onItemClicked(list[adapterPosition],adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowPostBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
       return list.size
    }
}