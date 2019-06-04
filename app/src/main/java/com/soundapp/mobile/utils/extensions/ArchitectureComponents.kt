package com.soundapp.mobile.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * @param liveData: LiveData to be observed
 * @param body: lambda function
 */
fun <T: Any, L: LiveData<T>>LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(this, Observer( body ))
}