package com.sunilk.tattoo.util

import android.content.Context
import android.os.Handler
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.gson.Gson
import java.nio.charset.Charset

/**
 * Created by Sunil on 20/10/18.
 */
object Utilities {

    fun showKeyBoard(context: Context?, editText: EditText?) {

        if (context != null && editText != null) {
            Handler().postDelayed({
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0))
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0))

            }, 200)

        }
    }

    fun hideKeyBoard(context: Context?, view: View?) {

        if (context != null && view != null && view.windowToken != null) {

            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun <T> getSampleResponseData(classType: Class<T>, fileName: String): T? {

        var data: T? = null

        try {

            val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)

            if (inputStream != null) {

                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()

                val json = String(buffer, Charset.defaultCharset())
                data = Gson().fromJson(json, classType)
                return data
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return data
    }
}