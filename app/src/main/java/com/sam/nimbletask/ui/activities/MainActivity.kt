package com.sam.nimbletask.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.nimbletask.ui.adapters.SurveysAdapter
import com.sam.nimbletask.utils.PaginationScrollListener
import com.sam.nimbletask.viewmodels.SurveriesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.PagerSnapHelper
import cz.intik.overflowindicator.SimpleSnapHelper


class MainActivity : AppCompatActivity() {

    lateinit var surveysViewModel: SurveriesViewModel
    lateinit var toast: Toast
    lateinit var surveysAdapter: SurveysAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page = 1
    var accessToken : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sam.nimbletask.R.layout.activity_main)

        initateData()
        setObservers()
        initiateRecycler()
        setListenersOfButtons()


    }

    private fun setListenersOfButtons() {

        refresh_btn.setOnClickListener {
            surveysAdapter.clearList()
            page=1
            requestSurveys()
        }
    }

    private fun requestSurveys() {
        surveysViewModel.getSurveys(page = page++, surveysPerPage = 10,accessToken =accessToken)
    }

    private fun initiateRecycler() {


        surveysAdapter = SurveysAdapter(ArrayList())
        rv_surveys.adapter = surveysAdapter
        rv_surveys.layoutManager = LinearLayoutManager(this)
        rv_surveys.setHasFixedSize(false)


        rv_surveys.addOnScrollListener(object :
            PaginationScrollListener(rv_surveys.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                //you have to call loadmore items to get more data
                if (!isLastPage)
                    requestSurveys()

            }

        })


//        val snapHelper = SimpleSnapHelper(indicator)
//        snapHelper.attachToRecyclerView(rv_surveys)
        indicator.attachToRecyclerView(rv_surveys)
        val snapHelper = SimpleSnapHelper(indicator)
        snapHelper.attachToRecyclerView(rv_surveys)

        if (progressbar != null)
            progressbar.visibility = View.VISIBLE
    }


    @SuppressLint("ShowToast")
    private fun initateData() {
        surveysViewModel = ViewModelProviders.of(this).get(SurveriesViewModel::class.java)
        surveysViewModel.getAccessToken()
        toast = Toast.makeText(applicationContext, "Error Occured , please try again later ...", Toast.LENGTH_SHORT)
    }


    private fun setObservers() {
        loadingObserve()
        errorObserve()
        successObserve()
        accessTokenObserve()
    }

    private fun accessTokenObserve() {

        surveysViewModel.accessTokenMutableLiveData.observe(this, Observer {
            if (it != null) {
                accessToken= it.accessToken.toString()
                requestSurveys()
            }

        })
    }


    private fun loadingObserve() {
        surveysViewModel.loadingVisibility.observe(this, Observer {

            progressbar.visibility = it

        })

    }

    private fun errorObserve() {
        surveysViewModel.errorMessage.observe(this, Observer {
            toast.cancel()
            if (it != null) {
                toast = Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT)
                toast.show()
            }
        })

    }

    private fun successObserve() {
        surveysViewModel.surveyResponseModelList.observe(this, Observer {
            surveysAdapter.updateList(it)
        })
    }
}
