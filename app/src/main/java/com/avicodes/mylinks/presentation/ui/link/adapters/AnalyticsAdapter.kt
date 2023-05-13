package com.avicodes.mylinks.presentation.ui.link.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avicodes.mylinks.data.models.Analytics
import com.avicodes.mylinks.databinding.ItemAnalyticsBinding
import com.bumptech.glide.Glide

class AnalyticsAdapter(private val analyticsList: List<Analytics>) :
    RecyclerView.Adapter<AnalyticsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAnalyticsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val data = analyticsList[position]

                Glide.with(icView.context)
                    .load(data.icon)
                    .into(icView)

                tvTitle.text = data.title
                tvSubtitle.text = data.value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAnalyticsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return analyticsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

}