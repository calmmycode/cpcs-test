package com.calmmycode.testapp.api.rest

import com.calmmycode.testapp.constants.ConstantsAPI
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET(ConstantsAPI.GET_ALL_TRAIDING_PAIRS)
    fun getTradingPairs(): Single<Response<ResponseBody>>

    @GET(ConstantsAPI.GET_INITIAL_TRADING_DATA)
    fun getInitialTradingData(@Query(ConstantsAPI.PARAM_SYMBOLS) symbols: String): Single<Response<ResponseBody>>
}