package com.sam.nimbletask.repository


import com.sam.nimbletask.network.RestApi
import io.reactivex.Observable
import org.json.JSONObject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SurveysRepository (var restApi: RestApi){

    fun getSurveys(page : Int,surveysPerPage : Int): Observable<JSONObject> {
        return restApi.getSurveys(page,surveysPerPage)
            .debounce(400, TimeUnit.MILLISECONDS)
            .map {
                Timber.d("Mapping CurrencyRates to UIData... ${it}")
                it
            }
            .onErrorReturn { null }
    }
}
