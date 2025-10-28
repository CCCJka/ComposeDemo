package com.jsl.Example.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import java.io.File
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date

//静态类将class修改为object
object CommonUtils {

    fun <T>navigation(context: Context, className: Class<T>){
        context.startActivity(Intent(context, className))
    }

    fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
        var tip = tipPercent / 100 * amount
        if (roundUp) {
            tip = Math.ceil(tip)
        }
        return NumberFormat.getCurrencyInstance().format(tip)
    }

    /**
     * Find the closest Activity in a given Context.
     */
    fun Context.findActivity(): Activity {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        throw IllegalStateException("Permissions should be called in the context of an Activity")
    }

    fun Context.createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            externalCacheDir      /* directory */
        )
        return image
    }
}