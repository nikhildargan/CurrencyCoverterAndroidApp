package com.paypay.currency_converter.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.graphics.Rect
import android.net.ConnectivityManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ScrollView
import androidx.databinding.BindingAdapter
import com.paypay.currency_converter.model.Currency
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Utility and extension functions that are used across the project.
 * @JvmStatic annotations are used so Data Binding can recognize them.
 */
object Utils {

    enum class Order(val position: Int) {
        INVALID(-1),
        FIRST(0),
        SECOND(1),
        THIRD(2),
        FOURTH(3)
    }
    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

    fun BigDecimal.roundToTwoDecimalPlaces(): BigDecimal = setScale(2, RoundingMode.HALF_DOWN)

    fun Long.toMillis() = this * 1_000L

    val String.Companion.EMPTY get() = ""
}