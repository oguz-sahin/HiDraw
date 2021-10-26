package com.huawei.hidraw.util.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.huawei.hidraw.util.Event

/**
 * Created by Oguz Sahin on 10/26/2021.
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this) {
        it?.let { t -> observer(t) }
    }
}

fun <T> LifecycleOwner.observeEvent(liveData: LiveData<Event<T>>, observer: (T) -> Unit) {
    liveData.observe(this) {
        it?.getContentIfNotHandled()?.let { t -> observer(t) }
    }
}