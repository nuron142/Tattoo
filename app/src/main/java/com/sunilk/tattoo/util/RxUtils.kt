package com.sunilk.tattoo.util

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * Created by Sunil on 20/10/18.
 */

object RxUtils {

    val TAG = RxUtils::class.java.simpleName

    fun <T> completable(func: Callable<out T>, onError: (() -> Unit)?,
                        subscribeScheduler: Scheduler): Disposable {

        return Flowable.fromCallable(func)
                .subscribeOn(subscribeScheduler)
                .subscribeWith(object : ResourceSubscriber<T>() {
                    override fun onNext(t: T) {

                    }

                    override fun onError(t: Throwable) {
                        onError?.invoke()
                        Log.e(TAG, "" + t.message)
                    }

                    override fun onComplete() {

                    }
                })
    }

    fun <T> delayCompletable(func: Callable<out T>, seconds: Long,
                             unit: TimeUnit, subscribeScheduler: Scheduler): Disposable {

        return Flowable.fromCallable(func)
                .delaySubscription(seconds, unit, subscribeScheduler)
                .subscribeWith(object : ResourceSubscriber<T>() {

                    override fun onError(t: Throwable) {
                        Log.e(TAG, "" + t.message)
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: T) {

                    }
                })
    }
}
