package com.calmmycode.testapp.ui.main_activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.calmmycode.testapp.R
import com.calmmycode.testapp.api.rest.API
import com.calmmycode.testapp.constants.ConstantsAPI
import com.calmmycode.testapp.model.repository.LocalRepository
import com.calmmycode.testapp.model.repository.db.ticker_history.TickerHistory
import com.calmmycode.testapp.ui.main_activity.tickers_list.TickerListFragment
import com.calmmycode.testapp.ui.main_activity.tickers_list.TickerListFragmentDirections
import com.calmmycode.testapp.util.viewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(R.layout.activity_main),
    TickerListFragment.OnListFragmentInteractionListener {

    private lateinit var navController: NavController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val mainActivityViewModel: MainActivityViewModel = viewModelProvider(viewModelFactory)
    }

    override fun onListFragmentInteraction(pair: String) {
        val action = TickerListFragmentDirections.actionTickersFragmentToTickerDetailsFragment(pair)
        navController.navigate(action)
    }
}
