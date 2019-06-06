package com.soundapp.mobile.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.soundapp.mobile.utils.Event

/**
 * @param liveData: LiveData to be observed
 * @param body: lambda function
 */
fun <T: Any, L: LiveData<T>>LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(this, Observer( body ))
}

fun MutableLiveData<Event<Unit>>.call() {
    value = Event(Unit)
}

fun Event<Unit>.consume(body: () -> Unit) {
    getContentIfNotHandled()?.let {
        body.invoke()
    }
}