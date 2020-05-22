package com.calmmycode.testapp.ui.main_activity.tickers_list

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.recyclerview.widget.SimpleItemAnimator
import com.calmmycode.testapp.R
import com.calmmycode.testapp.model.repository.LocalRepository
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

    @Inject
    lateinit var repository: LocalRepository
    private val compositeDisposable = CompositeDisposable()
    private val recyclerViewAdapter = TickerListRecyclerViewAdapter(arrayListOf())

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
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(repository.getAllPairsWithTickets()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {},
                onComplete = {},
                onNext = { recyclerViewAdapter.updateData(it) }
            ))
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onDetach() {
        super.onDetach()
        recyclerViewAdapter.setListener(null)
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(pair: String)
    }
}
