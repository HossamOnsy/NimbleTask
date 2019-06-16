package com.sam.nimbletask.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.sam.nimbletask.base.BaseViewModel
import com.sam.nimbletask.di.component.DaggerViewModelComponent
import com.sam.nimbletask.models.AccessTokenResponseModel
import com.sam.nimbletask.models.SurveyResponseModel
import com.sam.nimbletask.repository.SurveysRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import java.util.HashMap


class SurveriesViewModel : BaseViewModel() {


    @Inject
    lateinit var surveysRepository: SurveysRepository
    private var subscription: Disposable

    val surveyResponseModelList: MutableLiveData<List<SurveyResponseModel>> = MutableLiveData()
    val accessTokenMutableLiveData: MutableLiveData<AccessTokenResponseModel> = MutableLiveData()


    init {
        subscription = CompositeDisposable()

        DaggerViewModelComponent.builder().build().inject(this)
    }

    fun getAccessToken() {
        subscription =
            surveysRepository.getAccessToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    onRetrieveDataStart() }
                .doOnTerminate {
                    onRetrieveDataFinish() }
                .subscribe(
                    {

                        onRetrieveAccessTokenSuccess(it)

                    },
                    {
                            error -> onRetrieveDataError(error) }
                )
    }

    private fun onRetrieveAccessTokenSuccess(it: AccessTokenResponseModel) {

        accessTokenMutableLiveData.value = it

    }

    fun getSurveys(page: Int, surveysPerPage: Int,accessToken:String) {
//        val pageMP = MultipartBody.Part.createFormData("page", page.toString())
//        val surveysPerPageMP = MultipartBody.Part.createFormData("per_page", surveysPerPage.toString())
//        val accessTokenMP = MultipartBody.Part.createFormData("access_token", accessToken.toString())
//
//        val mPartArrayList = ArrayList<MultipartBody.Part>()
//        mPartArrayList.add(pageMP)
//        mPartArrayList.add(surveysPerPageMP)
//        mPartArrayList.add(accessTokenMP)


        val confirmRequest = HashMap<String, String>()
        confirmRequest["page"] = page.toString()
        confirmRequest["per_page"] = surveysPerPage.toString()
        confirmRequest[" access_token"] = accessToken



        subscription =
            surveysRepository.getSurveys(page,surveysPerPage,accessToken)
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

    private fun onRetrieveSurveysDataSuccess(surveyList: List<SurveyResponseModel>) {

        loadingVisibility.value = View.GONE
        surveyResponseModelList.value = surveyList

    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


}