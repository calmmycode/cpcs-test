package com.calmmycode.testapp.constants

class ConstantsAPI {
    companion object {
        private val api_domain by lazyOf("https://api-pub.bitfinex.com")
        private val api_version by lazyOf("v2")
        val BASE_URL by lazyOf("$api_domain/$api_version/")

        const val GET_ALL_TRAIDING_PAIRS = "conf/pub:list:pair:exchange"
        const val GET_INITIAL_TRADING_DATA = "tickers"

        const val PARAM_SYMBOLS = "symbols"

    }
}