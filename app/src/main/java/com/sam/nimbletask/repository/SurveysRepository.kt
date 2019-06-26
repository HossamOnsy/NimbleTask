package com.sam.nimbletask.repository


import com.sam.nimbletask.BuildConfig.*
import com.sam.nimbletask.models.AccessTokenResponseModel
import com.sam.nimbletask.models.SurveyResponseModel
import com.sam.nimbletask.network.RestApi
import io.reactivex.Observable


class SurveysRepository(var restApi: RestApi) {

    fun getSurveys(page: Int, surveysPerPage: Int, accessToken: String): Observable<List<SurveyResponseModel>> {

        return restApi.getSurveys(page, surveysPerPage, accessToken)
            .onErrorReturn { null }
    }

    fun getAccessToken(): Observable<AccessTokenResponseModel> {
        return restApi.getToken(grant_type = GRANT_TYPE, username = USERNAME, password = PASSWORD)
    }
}
