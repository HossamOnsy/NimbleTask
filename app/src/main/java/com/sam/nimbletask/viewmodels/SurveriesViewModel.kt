package com.sam.nimbletask.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.sam.nimbletask.base.BaseViewModel
import com.sam.nimbletask.di.component.DaggerViewModelComponent
import com.sam.nimbletask.repository.SurveysRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SurveriesViewModel : BaseViewModel() {


    @Inject
    lateinit var surveysRepository: SurveysRepository

    private var subscription: Disposable

    val jsonObjectLiveData: MutableLiveData<JSONObject> = MutableLiveData()


    var isScrolling: Boolean = false
    var isUpdatingText: Boolean = false
    var isFirstCall: Boolean = true

    init {
        subscription = CompositeDisposable()

        DaggerViewModelComponent.builder().build().inject(this)
    }

    fun getSurveys(page : Int,surveysPerPage : Int) {
        subscription =
            Observable.interval(1, TimeUnit.SECONDS)
                .flatMap {
                    surveysRepository.getSurveys(page,surveysPerPage)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveDataStart() }
                .doOnTerminate{ onRetrieveDataFinish() }
                .subscribe(
                    {

                        onRetrieveDataSuccess(it)

                    },
                    { error -> onRetrieveDataError(error) }
                )
    }

    private fun onRetrieveDataSuccess(jsonObject: JSONObject?) {

        loadingVisibility.value = View.GONE

        if (isFirstCall) {
            isFirstCall = false
            jsonObjectLiveData.value = jsonObject
        } else if (!isScrolling&&!isUpdatingText){
            jsonObjectLiveData.value = null
        }

    }



    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


}