package com.example.videoapp.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun getGenresFromIds(genreIds: List<Int>): List<String> {
    return genreIds.mapNotNull { genreMap[it] }
}


fun checkNetworkConnectivity(context: Context): Boolean{
    var isConnected = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    if(networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
        isConnected = true
    }

    return isConnected
}

fun shareMovieLink(context: Context, deepLink: String) {

    // Create an intent to share the deep link URL
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, deepLink)
        type = "text/plain"
    }

    // Start the share dialog
    context.startActivity(Intent.createChooser(shareIntent, "Share Movie"))
}