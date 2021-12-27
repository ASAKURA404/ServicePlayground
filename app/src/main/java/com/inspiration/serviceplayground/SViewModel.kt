package com.inspiration.serviceplayground

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SViewModel : ViewModel() {
    var status = MutableLiveData(false)

}
