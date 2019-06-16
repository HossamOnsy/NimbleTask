package com.sam.nimbletask.network


import com.sam.nimbletask.models.AccessTokenResponseModel
import com.sam.nimbletask.models.SurveyResponseModel
import io.reactivex.Observable
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.*

interface RestApi {

//    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("/surveys.json")
    fun getTempSurveys(
        @QueryMap mPartArrayList:Map<String, String>
    ): Observable<List<SurveyResponseModel>>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("/surveys.json")
    fun getSurveys(
        @Query("page") pageNumber: Int,
        @Query("per_page") surveysPerPage: Int,
        @Query("access_token") access_token:String
    ): Observable<List<SurveyResponseModel>>


    @FormUrlEncoded
    @POST("/oauth/token")
    fun getToken(
        @Field("grant_type") grant_type : String ="password",
        @Field("username") username : String ="carlos@nimbl3.com",
        @Field("password") password : String ="antikera"
    ): Observable<AccessTokenResponseModel>

}