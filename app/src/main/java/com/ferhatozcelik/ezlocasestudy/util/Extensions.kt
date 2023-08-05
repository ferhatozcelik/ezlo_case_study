package com.ferhatozcelik.ezlocasestudy.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.ferhatozcelik.ezlocasestudy.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String.debug(message: String) {
    Log.d(this, message)
}

fun EditText.modifyText(numberText: String) {
    this.setText(numberText)
    this.setSelection(numberText.length)
}

fun String?.generatorImage(): Int {
    return when (this) {
        "Sercomm G450" -> R.drawable.vera_plus_big
        "Sercomm G550" -> R.drawable.vera_secure_big
        "MiCasaVerde VeraLite" -> R.drawable.vera_edge_big
        "Sercomm NA900" -> R.drawable.vera_edge_big
        "Sercomm NA301" -> R.drawable.vera_edge_big
        "Sercomm NA930" -> R.drawable.vera_edge_big
        else -> R.drawable.vera_edge_big
    }
}

fun Context.goURL(url: String) {
    try {
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(myIntent)
    } catch (e: ActivityNotFoundException) {
        this.toast("No application can handle this request. Please install a webbrowser")
        e.printStackTrace()
    }
}