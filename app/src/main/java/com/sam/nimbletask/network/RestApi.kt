package com.sam.nimbletask.network


import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("/surveys.json")
    fun getSurveys(
        @Query("page") pageNumber: Int,
        @Query("per_page") surveysPerPage: Int
    ): Observable<JSONObject>


    @FormUrlEncoded
    @GET("/oauth/token")
    fun getToken(
        @Field("grant_type") grant_type : String ="password",
        @Field("username") username : String ="carlos@nimbl3.com",
        @Field("password") password : String ="antikera"
    ): Observable<JSONObject>

}