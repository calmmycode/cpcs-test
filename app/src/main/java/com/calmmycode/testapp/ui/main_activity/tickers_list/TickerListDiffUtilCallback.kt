package com.calmmycode.testapp.ui.main_activity.tickers_list

import androidx.recyclerview.widget.DiffUtil
import com.calmmycode.testapp.model.data.TickerItemData

class TickerListDiffUtilCallback : DiffUtil.Callback(){

    var oldData = ArrayList<TickerItemData>()
    var newData = ArrayList<TickerItemData>()

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].pair_name == newData[newItemPosition].pair_name
    }

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].ask == newData[newItemPosition].ask &&
                oldData[oldItemPosition].bid_bid == newData[newItemPosition].bid_bid &&
                oldData[oldItemPosition].avg == newData[newItemPosition].avg
    }

    fun update(oldItems: List<TickerItemData>, newItems: List<TickerItemData>) {
        oldData.clear()
        oldData.addAll(oldItems)
        newData.clear()
        newData.addAll(newItems)
    }
}