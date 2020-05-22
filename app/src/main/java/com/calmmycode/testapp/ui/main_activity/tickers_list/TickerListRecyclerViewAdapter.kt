package com.calmmycode.testapp.ui.main_activity.tickers_list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.calmmycode.testapp.databinding.FragmentTickersListItemBinding
import com.calmmycode.testapp.model.data.TickerItemData


import com.calmmycode.testapp.ui.main_activity.tickers_list.TickerListFragment.OnListFragmentInteractionListener

class TickerListRecyclerViewAdapter(
    private val mValues: ArrayList<TickerItemData>,
    private var mListener: OnListFragmentInteractionListener? = null
) : RecyclerView.Adapter<TickerListRecyclerViewAdapter.ViewHolder>() {

    lateinit var diffUtilCallback: TickerListDiffUtilCallback

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            mListener?.onListFragmentInteraction(v.tag.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentTickersListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        with(holder.mBinding) {
            root.tag = item.pair_name
            val lastIndex = item.pair_name.lastIndexOf("USD")
            if (lastIndex > 0) {
                textViewPairName.text = item.pair_name.substring(0, lastIndex).plus("-USD")
            } else {
                textViewPairName.text = item.pair_name
            }

            textViewPlaceholderBid.text = item.bid_bid.toString()
            textViewPlaceholderAsk.text = item.ask.toString()
            textViewPlaceholderAvg.text = item.avg.toString()
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun updateData(newData : List<TickerItemData>){
        if (!::diffUtilCallback.isInitialized) {
            diffUtilCallback = TickerListDiffUtilCallback()
        }
        diffUtilCallback.update(mValues, newData)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback, true)
        mValues.clear()
        mValues.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val mBinding: FragmentTickersListItemBinding) :
        RecyclerView.ViewHolder(mBinding.root){
        init {
            mBinding.root.setOnClickListener(mOnClickListener)
        }
    }

    fun setListener(mListener: OnListFragmentInteractionListener?){
        this.mListener = mListener
    }
}
