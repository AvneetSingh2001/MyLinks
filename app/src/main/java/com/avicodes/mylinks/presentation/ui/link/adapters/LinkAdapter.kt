package com.avicodes.mylinks.presentation.ui.link.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avicodes.mylinks.data.models.Link
import com.avicodes.mylinks.databinding.ItemLinkBinding
import com.bumptech.glide.Glide

class LinkAdapter(
    private val copyClickListener: (String) -> Unit
) :
    RecyclerView.Adapter<LinkAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val data = differ.currentList[position]

                tvItemTitle.text = data.title
                tvClickCount.text = data.total_clicks.toString()
                tvItemLink.text = data.web_link
                tvItemDate.text = data.created_at

                Glide.with(ivItemLink.context)
                    .load(data.original_image)
                    .into(ivItemLink)

                ivCopyLink.setOnClickListener {
                    copyClickListener(data.web_link)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    private var callback = object : DiffUtil.ItemCallback<Link>() {
        override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this, callback)

}