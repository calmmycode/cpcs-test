package com.calmmycode.testapp.ui.main_activity.tickers_list

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.calmmycode.testapp.R
import com.calmmycode.testapp.model.repository.LocalRepository
import com.calmmycode.testapp.ui.main_activity.MainActivityViewModel
import com.calmmycode.testapp.util.viewModelProvider
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [TickerListFragment.OnListFragmentInteractionListener] interface.
 */
class TickerListFragment : DaggerFragment(R.layout.fragment_tickers_list) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val recyclerViewAdapter = TickerListRecyclerViewAdapter(arrayListOf())
    private lateinit var viewModel: TickerListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            recyclerViewAdapter.setListener(context)
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                this.adapter = recyclerViewAdapter
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }
        }
        viewModel = viewModelProvider(viewModelFactory)
    }

    override fun onStart() {
        super.onStart()
        viewModel.newData.observe(viewLifecycleOwner, Observer {
            recyclerViewAdapter.updateData(it)
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.newData.removeObservers(viewLifecycleOwner)
    }

    override fun onDetach() {
        super.onDetach()
        recyclerViewAdapter.setListener(null)
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(pair: String)
    }
}
