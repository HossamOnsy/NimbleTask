package com.sam.nimbletask.models

import android.content.Context
import com.sam.nimbletask.R
import com.sam.nimbletask.utils.AppUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyServiceInterceptor @Inject constructor(val context: Context) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()

        if (request.header("No-Authentication") == null) {
            val sessionToken = AppUtils.getFromSharedPref(context, context.getString(R.string.AccessToken).toString())
            if (sessionToken != null)
                requestBuilder.addHeader("access_token", sessionToken)
        }

        return chain.proceed(requestBuilder.build())
    }
}