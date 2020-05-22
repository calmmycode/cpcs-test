package com.calmmycode.testapp.ui.main_activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.calmmycode.testapp.R
import com.calmmycode.testapp.api.rest.API
import com.calmmycode.testapp.model.repository.LocalRepository
import com.calmmycode.testapp.model.repository.db.ticker_history.TickerHistory
import com.calmmycode.testapp.ui.main_activity.tickers_list.TickerListFragment
import com.calmmycode.testapp.ui.main_activity.tickers_list.TickerListFragmentDirections
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

class MainActivity : DaggerAppCompatActivity(), TickerListFragment.OnListFragmentInteractionListener {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var api: API

    @Inject
    lateinit var repository: LocalRepository

    private lateinit var navController: NavController

    val listWebSocketChannels = LinkedHashMap<WebSocket, ArrayList<Long>>()
    val listChannelIdPair = LinkedHashMap<Long, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val okHttpClient = OkHttpClient()
        val request = Request.Builder().url("wss://api-pub.bitfinex.com/ws/2").build()

        compositeDisposable.add(
            api.getTradingPairs().subscribeOn(Schedulers.io())
                .map {
                    val listOfPairs = ArrayList<String>()
                    if (it.code() == 200) {
                        it.body()?.let {
                            val response = JSONArray(it.string())
                            val arrayOfAllPossiblePairs = response.getJSONArray(0)
                            val countOfAllPossiblePairs = arrayOfAllPossiblePairs.length()


                            for (i: Int in 0 until countOfAllPossiblePairs) {
                                val pair = arrayOfAllPossiblePairs.getString(i)
                                if (pair.endsWith("USD")){
                                    listOfPairs.add(pair)
                                }
                            }
                        }
                    }
                    listOfPairs
                }
                .subscribeBy(
                onError = {},
                onSuccess = {
                    val listOfPairsCount = it.size

                    for (pos: Int in 0 until listOfPairsCount) {
                        if (pos.rem(30) == 0) {
                            listWebSocketChannels.put(
                                okHttpClient.newWebSocket(
                                    request,
                                    BitFinexWebSocket()
                                ), ArrayList()
                            )
                        }
                        val symbol = it.get(pos)
                        val s1 = JSONObject()
                        s1.put("event", "subscribe")
                        s1.put("channel", "ticker")
                        s1.put("symbol", "t".plus(symbol))
                        val s2 = s1.toString()

                        val detailIterator: MutableIterator<WebSocket> =
                            listWebSocketChannels.keys.iterator()
                        for (webSocket in detailIterator) {
                            if (!detailIterator.hasNext()) {
                                webSocket.send(s2)
                            }
                        }
                    }
                }
            ))
    }

    inner class BitFinexWebSocket() : WebSocketListener() {

        override fun onMessage(webSocket: WebSocket, text: String) {
            val json = JSONTokener(text).nextValue()
            if (json is JSONObject) {
                if (json.getString("event") == "subscribed" && json.getString("channel") == "ticker") {
                    val channelId = json.getLong("chanId")
                    val detailIterator: MutableIterator<WebSocket> =
                        listWebSocketChannels.keys.iterator()
                    for (socket in detailIterator) {
                        if (socket == webSocket) {
                            listWebSocketChannels.get(socket)?.add(channelId)
                            listChannelIdPair.put(channelId, json.getString("pair"))
                            break
                        }
                    }
                }
            } else if (json is JSONArray) {
                //[84792,[0.00677,70676.93,0.0069272,30568.680000000004,-0.0009746,-0.1425,0.0058668,61889.416639,0.0068414,0.0058668]]
                val channelId = json.getLong(0)
                val pair = listChannelIdPair.get(channelId)
                val secondObj = json.get(1)
                if (secondObj is JSONArray) {
                    val bid = secondObj.getDouble(0)
                    val ask = secondObj.getDouble(2)
                    val last_price = secondObj.getDouble(6)
                    val volume = secondObj.getDouble(7)

                    val time = System.currentTimeMillis()
                    compositeDisposable.add(
                        repository.insertItemHistory(TickerHistory(null, pair.toString(), bid, ask, time, volume, last_price)).subscribe()
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onListFragmentInteraction(pair: String) {
        val action = TickerListFragmentDirections.actionTickersFragmentToTickerDetailsFragment(pair)
        navController.navigate(action)
    }
}
