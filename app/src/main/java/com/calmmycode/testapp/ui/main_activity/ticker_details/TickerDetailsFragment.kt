package com.calmmycode.testapp.ui.main_activity.ticker_details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.calmmycode.testapp.R
import com.calmmycode.testapp.model.repository.LocalRepository
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

private const val ARG_TICKER_PAIR = "pair_name"

class TickerDetailsFragment : DaggerFragment(R.layout.fragment_ticker_details) {

    @Inject
    lateinit var repository: LocalRepository

    private lateinit var param_pair: String
    private val recyclerViewAdapter = TickerDetailsRecyclerViewAdapter()
    private lateinit var viewModel : TickerDetaisViewModel

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param_pair = it.getString(ARG_TICKER_PAIR, "")
        }
        val factory = TickerDetaisViewModel.Factory(repository, param_pair)
        viewModel = ViewModelProvider(this, factory).get(TickerDetaisViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                this.adapter = recyclerViewAdapter
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        disposable.add(viewModel.concertList.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(recyclerViewAdapter::submitList))
    }


    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TickerDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            TickerDetailsFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(ARG_TICKER_PAIR, param1)
                }
            }
    }
}
