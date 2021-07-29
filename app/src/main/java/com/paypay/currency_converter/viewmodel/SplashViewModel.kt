package com.paypay.currency_converter.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
class SplashViewModel @ViewModelInject constructor(
    application: Application) : AndroidViewModel(application) {

    }