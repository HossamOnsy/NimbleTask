package com.sam.nimbletask.repository


import com.sam.nimbletask.models.AccessTokenResponseModel
import com.sam.nimbletask.models.SurveyResponseModel
import com.sam.nimbletask.network.RestApi
import io.reactivex.Observable
import okhttp3.MediaType
import timber.log.Timber
import java.util.HashMap
import java.util.concurrent.TimeUnit
import okhttp3.RequestBody
import okhttp3.OkHttpClient
import okhttp3.Request
import android.os.AsyncTask.execute




class SurveysRepository (var restApi: RestApi){

    fun getSurveys(page : Int,surveysPerPage : Int,accessToken:String): Observable<List<SurveyResponseModel>> {
        var client = OkHttpClient()

        var mediaType = MediaType.parse("application/x-www-form-urlencoded")
        var body = RequestBody.create(
            mediaType,
            "access_token=${accessToken}&undefined="
        )
        var request = Request.Builder()
            .url("https://nimble-survey-api.herokuapp.com/surveys.json?page=1&per_page=2")
            .get()
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("cache-control", "no-cache")
            .addHeader("Postman-Token", "fb2d1375-3864-4c16-ba00-bb00f4f062c8")
            .build()



//        val response = client.newCall(request).enqueue(restApi.getSurveys(page,surveysPerPage,accessToken))


        return restApi.getSurveys(page,surveysPerPage,accessToken)
            .debounce(400, TimeUnit.MILLISECONDS)
            .map {
                Timber.d("Mapping CurrencyRates to UIData... ${it}")
                it
            }
            .onErrorReturn { null }
    }



    fun getTempSurveys(hm: HashMap<String, String>): Observable<List<SurveyResponseModel>> {
        return restApi.getTempSurveys(hm)
            .debounce(400, TimeUnit.MILLISECONDS)
            .map {
                Timber.d("Mapping CurrencyRates to UIData... ${it}")
                it
            }
            .onErrorReturn { null }
    }


    fun getAccessToken(): Observable<AccessTokenResponseModel> {
        return restApi.getToken(grant_type="password",username ="carlos@nimbl3.com",password ="antikera")
    }
}
