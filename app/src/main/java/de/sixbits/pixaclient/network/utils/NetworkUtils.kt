package de.sixbits.pixaclient.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import javax.inject.Inject

private const val TAG = "NetworkUtils"

class NetworkUtils @Inject constructor(private val context: Context) {

    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return true
        }
        if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return true
        }
        if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return true
        }
        if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) && actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        ) {
            return true
        }
        if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) && actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        ) {
            return true
        }

        return false
    }
}