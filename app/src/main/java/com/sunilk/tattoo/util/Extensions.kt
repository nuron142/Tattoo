package com.sunilk.tattoo.util

import android.databinding.ObservableField
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposables


/**
 * Created by Sunil on 20/10/18.
 */

fun String?.isNotEmpty(): Boolean {
    return this?.length ?: 0 > 0
}

fun String?.isNotEmpty(block: (String) -> Unit) {
    if (this != null && this.isNotEmpty()) {
        block(this)
    }
}

fun <T> ObservableField<T>.toFlowable(): Flowable<T> {

    return Flowable.create({ emitter ->

        val field = this
        val value = field.get()
        if (value != null) {
            emitter.onNext(value)
        }

        val callback = object : android.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {

                val newValue = field.get()
                if (newValue != null) {
                    emitter.onNext(newValue)
                }
            }
        }

        field.addOnPropertyChangedCallback(callback)
        emitter.setDisposable(Disposables.fromAction { field.removeOnPropertyChangedCallback(callback) })

    }, BackpressureStrategy.BUFFER)
}