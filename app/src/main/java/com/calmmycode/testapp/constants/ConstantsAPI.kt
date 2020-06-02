package com.calmmycode.testapp.constants

class ConstantsAPI {
    companion object {
        private val API_DOMIAN by lazyOf("https://api-pub.bitfinex.com")
        const val API_VERSION = "2"
        val BASE_URL by lazyOf("$API_DOMIAN/v$API_VERSION/")

        const val API_SOCKET = "wss://api-pub.bitfinex.com/ws/$API_VERSION"

        const val GET_ALL_TRAIDING_PAIRS = "conf/pub:list:pair:exchange"
        const val GET_INITIAL_TRADING_DATA = "tickers"

        const val PARAM_SYMBOLS = "symbols"

        const val USD = "USD"

    }
}