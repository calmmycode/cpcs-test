package com.calmmycode.testapp.ui.main_activity.ticker_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.calmmycode.testapp.databinding.FragmentTickerDetailItemBinding
import com.calmmycode.testapp.model.data.TickerDetailData

class TickerDetailsRecyclerViewAdapter :
    PagedListAdapter<TickerDetailData, TickerDetailsRecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentTickerDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(val binding: FragmentTickerDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<TickerDetailData>() {
            override fun areItemsTheSame(
                oldTicker: TickerDetailData,
                newTicker: TickerDetailData
            ) = oldTicker.id == newTicker.id

            override fun areContentsTheSame(
                oldTicker: TickerDetailData,
                newTicker: TickerDetailData
            ) = oldTicker == newTicker
        }
    }
}