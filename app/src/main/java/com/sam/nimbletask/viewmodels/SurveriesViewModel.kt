package com.sam.nimbletask.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.sam.nimbletask.MyApplication.Companion.applicationContext
import com.sam.nimbletask.base.BaseViewModel
import com.sam.nimbletask.di.component.DaggerMainComponent
import com.sam.nimbletask.di.modules.NetworkModule
import com.sam.nimbletask.models.AccessTokenResponseModel
import com.sam.nimbletask.models.SurveyResponseModel
import com.sam.nimbletask.repository.SurveysRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class SurveriesViewModel (var surveysRepository: SurveysRepository) : BaseViewModel() {


    private var subscription: Disposable

    val surveyResponseModelList: MutableLiveData<List<SurveyResponseModel>> = MutableLiveData()
    val accessTokenMutableLiveData: MutableLiveData<AccessTokenResponseModel> = MutableLiveData()
    var morePages = true

    init {
        subscription = CompositeDisposable()
    }

    fun getAccessToken() {
        subscription =
            surveysRepository.getAccessToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    onRetrieveDataStart()
                }
                .doOnTerminate {
                    onRetrieveDataFinish()
                }
                .subscribe(
                    {

                        onRetrieveAccessTokenSuccess(it)

                    },
                    { error -> onRetrieveDataError(error) }
                )
    }

    private fun onRetrieveAccessTokenSuccess(it: AccessTokenResponseModel) {

        accessTokenMutableLiveData.value = it

    }

    fun getSurveys(page: Int, surveysPerPage: Int, accessToken: String) {
        if(morePages){
        val confirmRequest = HashMap<String, String>()
        confirmRequest["page"] = page.toString()
        confirmRequest["per_page"] = surveysPerPage.toString()
        confirmRequest[" access_token"] = accessToken


        subscription =
            surveysRepository.getSurveys(page, surveysPerPage, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveDataStart() }
                .doOnTerminate { onRetrieveDataFinish() }
                .subscribe(
                    {
                        onRetrieveSurveysDataSuccess(it)
                    },
                    { error -> onRetrieveDataError(error) }
                )
        }
    }

    private fun onRetrieveSurveysDataSuccess(surveyList: List<SurveyResponseModel>) {

        if(surveyList.size<=0){
            morePages=false
        }

        loadingVisibility.value = View.GONE
        surveyResponseModelList.value = surveyList

    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


}