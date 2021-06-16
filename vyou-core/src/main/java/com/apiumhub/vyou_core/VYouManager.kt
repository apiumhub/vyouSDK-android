package com.apiumhub.vyou_core

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.apiumhub.vyou_core.data.VYouServerException
import com.apiumhub.vyou_core.domain.VYouException
import com.apiumhub.vyou_core.domain.VYouResult
import com.apiumhub.vyou_core.domain.VYouResult.Success
import com.apiumhub.vyou_core.login.vyou_auth.VYouAuthException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.lang.Exception

abstract class VYouManager internal constructor() : KoinComponent {
    private val context: Context by inject()

    protected suspend fun <T>networkCall(func: suspend () -> T) = if(isConnected())
        runCatching { func.invoke() }
            .fold(::Success, this::handleError) else VYouResult.Failure(VYouAuthException.NoConnection)

    private fun <T>handleError(throwable: Throwable): VYouResult.Failure<T> = when(throwable) {
        is HttpException -> VYouResult.Failure(VYouServerException.fromCode(throwable.code()))
        else -> VYouResult.Failure(VYouException(throwable))
    }

    private fun isConnected(): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            info != null && info.isAvailable && info.isConnected
        } catch (ex: Exception) {
            Log.e("Error", "Failed to get connectivity", ex)
            false
        }
    }
}