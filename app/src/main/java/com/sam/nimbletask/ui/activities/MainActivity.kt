package com.sam.nimbletask.ui.activities

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.sam.nimbletask.MyApplication
import com.sam.nimbletask.R
import com.sam.nimbletask.di.component.DaggerMainComponent
import com.sam.nimbletask.di.modules.NetworkModule
import com.sam.nimbletask.factory.SurveriesViewModelFactory
import com.sam.nimbletask.ui.adapters.SurveysAdapter
import com.sam.nimbletask.utils.AppUtils
import com.sam.nimbletask.utils.AppUtils.setToolbarColor
import com.sam.nimbletask.utils.CenterTitleToolbar
import com.sam.nimbletask.utils.PaginationScrollListener
import com.sam.nimbletask.viewmodels.SurveriesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModeFactory: SurveriesViewModelFactory
    lateinit var surveysViewModel: SurveriesViewModel
    lateinit var toast: Toast
    lateinit var surveysAdapter: SurveysAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page = 1
    var accessToken: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stylingToolBar()

        initateData()

        setObservers()
        initiateRecycler()
        setListenersOfButtons()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh_button -> {
                requestSurveys(
                    AppUtils.getFromSharedPref(
                        this,
                        getString(R.string.AccessToken)
                    ).toString()
                )
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }


    private fun stylingToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(resources.getString(R.string.surveys))

        val margin = resources.getDimensionPixelSize(com.sam.nimbletask.R.dimen._25sdp)
        val bitmap = (ContextCompat.getDrawable(this, R.drawable.refresh) as BitmapDrawable).bitmap
        val newdrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, margin, margin, true))
        newdrawable.setColorFilter(
            ContextCompat.getColor(this, R.color.colorWhite),
            PorterDuff.Mode.SRC_ATOP
        )
        toolbar?.navigationIcon = newdrawable
        val drawable = ContextCompat.getDrawable(
            applicationContext,
            R.drawable.ic_menu
        )
        toolbar.overflowIcon = drawable
    }

    private fun setListenersOfButtons() {
        toolbar?.setNavigationOnClickListener {
            surveysViewModel.morePages = true
            surveysAdapter.clearList()
            page = 1
            requestSurveys(
                AppUtils.getFromSharedPref(
                    this,
                    getString(R.string.AccessToken)
                ).toString()
            )
        }
    }

    private fun requestSurveys(accessToken: String) {
        surveysViewModel.getSurveys(page = page++, surveysPerPage = 10, accessToken = accessToken)
    }

    private fun initiateRecycler() {


        surveysAdapter = SurveysAdapter(ArrayList())
        rv_surveys.adapter = surveysAdapter
        rv_surveys.layoutManager = LinearLayoutManager(this)
        rv_surveys.setHasFixedSize(false)




        rv_surveys.addOnScrollListener(object :
            PaginationScrollListener(rv_surveys.layoutManager as LinearLayoutManager) {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (surveysAdapter.list.size > 0)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val rvLM = recyclerView.layoutManager as LinearLayoutManager
                        val currentSurvey = surveysAdapter.list.get(rvLM.findFirstVisibleItemPosition())

                        Glide.with(this@MainActivity)
                            .asBitmap()
                            .load(currentSurvey.coverImageUrl)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                                ) {

                                    setToolbarColor(this@MainActivity, resource)
                                }


                                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                            })
                    }
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                //you have to call loadmore items to get more data
                if (!isLastPage)
                    requestSurveys(
                        AppUtils.getFromSharedPref(
                            this@MainActivity,
                            getString(R.string.AccessToken)
                        ).toString()
                    )

            }

        })


        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rv_surveys)
        indicator2.attachToRecyclerView(rv_surveys, pagerSnapHelper)
        surveysAdapter.registerAdapterDataObserver(indicator2.adapterDataObserver)

        if (progressbar != null)
            progressbar.visibility = View.VISIBLE
    }


    @SuppressLint("ShowToast")
    private fun initateData() {
        DaggerMainComponent.builder().networkModule(NetworkModule(MyApplication.applicationContext())).build()
            .inject(this)
        surveysViewModel = ViewModelProviders.of(this, this.viewModeFactory).get(SurveriesViewModel::class.java)
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
                accessToken = it.accessToken.toString()
                AppUtils.setIntoSharedPref(
                    this,
                    getString(R.string.AccessToken).toString(),
                    accessToken
                )

                requestSurveys(accessToken)
            }

        })
    }


    private fun loadingObserve() {
        surveysViewModel.loadingVisibility.observe(this, Observer {

            progressbar.visibility = it

        })

    }

    @SuppressLint("ShowToast")
    private fun errorObserve() {
        surveysViewModel.errorMessage.observe(this, Observer {
            toast.cancel()
            if (it != null) {
                toast = Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT)
            }
        })
    }

    private fun successObserve() {
        surveysViewModel.surveyResponseModelList.observe(this, Observer {
            surveysAdapter.updateList(it)
        })
    }
}
