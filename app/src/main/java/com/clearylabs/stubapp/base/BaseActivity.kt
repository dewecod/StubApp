package com.clearylabs.stubapp.base

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.clearylabs.stubapp.R
import com.google.android.material.snackbar.Snackbar
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    fun showSnackBarMessage(message: String?) {
        message?.let {
            val snackBar = Snackbar.make(findViewById(android.R.id.content), message as CharSequence, Snackbar.LENGTH_SHORT)
            val sbView = snackBar.view
            val textView = sbView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(ContextCompat.getColor(this, R.color.background))
            snackBar.show()
        }
    }
}