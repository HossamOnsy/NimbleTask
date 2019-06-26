package com.sam.nimbletask.network


import com.sam.nimbletask.models.AccessTokenResponseModel
import com.sam.nimbletask.models.SurveyResponseModel
import io.reactivex.Observable
import retrofit2.http.*

interface RestApi {

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("/surveys.json")
    fun getSurveys(
        @Query("page") pageNumber: Int,
        @Query("per_page") surveysPerPage: Int,
        @Query("access_token") access_token: String
    ): Observable<List<SurveyResponseModel>>


    @FormUrlEncoded
    @Headers("No-Authentication:true")
    @POST("/oauth/token")
    fun getToken(
        @Field("grant_type") grant_type: String ,
        @Field("username") username: String ,
        @Field("password") password: String
    ): Observable<AccessTokenResponseModel>

}