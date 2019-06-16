package com.sam.nimbletask.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json


class AccessTokenResponseModel {


    @SerializedName( "access_token")
    var accessToken: String? = null
    @SerializedName( "token_type")
    var tokenType: String? = null
    @SerializedName( "expires_in")
    var expiresIn: Int? = null
    @SerializedName( "created_at")
    var createdAt: Int? = null

//    @Json(name = "access_token")
//    var accessToken: String? = null
//    @Json(name = "token_type")
//    var tokenType: String? = null
//    @Json(name = "expires_in")
//    var expiresIn: Int? = null
//    @Json(name = "created_at")
//    var createdAt: Int? = null

}