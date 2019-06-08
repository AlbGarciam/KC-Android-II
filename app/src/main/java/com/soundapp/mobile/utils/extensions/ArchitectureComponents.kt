package com.soundapp.mobile.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.soundapp.mobile.utils.Event
import org.threeten.bp.Instant

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

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

fun Instant.timeAgo(): String {
    var time = this.toEpochMilli()
    if (time < 1000000000000L) {
        time *= 1000
    }

    val now = Instant.now().toEpochMilli()
    if (time > now || time <= 0) {
        return "in the future"
    }

    val diff = now - time
    return when {
        diff < MINUTE_MILLIS -> "moments ago"
        diff < 2 * MINUTE_MILLIS -> "a minute ago"
        diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
        diff < 2 * HOUR_MILLIS -> "an hour ago"
        diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
        diff < 48 * HOUR_MILLIS -> "yesterday"
        else -> "${diff / DAY_MILLIS} days ago"
    }
}