package com.sam.nimbletask.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sam.nimbletask.R
import com.sam.nimbletask.base.BaseViewModel

class LoginActivity : AppCompatActivity() {


    lateinit var baseViewModel: BaseViewModel
    lateinit var toast: Toast


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initateData()

        loadingObserve()
        successObserve()
        errorObserve()

    }
    @SuppressLint("ShowToast")
    private fun initateData() {
        baseViewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)
        toast = Toast.makeText(applicationContext, "Error Occured , please try again later ...", Toast.LENGTH_SHORT)
    }



    private fun loadingObserve() {
        baseViewModel.loadingVisibility.observe(this, Observer {


        })

    }

    private fun errorObserve() {
        baseViewModel.errorMessage.observe(this, Observer {
            toast.cancel()
            if (it != null) {
                toast = Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT)
                toast.show()
            }
        })

    }

    private fun successObserve() {


    }


}
