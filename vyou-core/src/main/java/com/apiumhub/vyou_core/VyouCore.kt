package com.apiumhub.vyou_core

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_core.auth.AuthWebviewActivity
import com.apiumhub.vyou_core.auth.ManifestReader
import com.apiumhub.vyou_core.data.VyouApi
import com.apiumhub.vyou_core.di.retrofitModule
import com.apiumhub.vyou_core.di.vyouCoreModule
import com.apiumhub.vyou_core.domain.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import kotlin.properties.Delegates

class VyouCore : KoinComponent {

    private val activityResultLauncher: ActivityResultLauncher<Void?>
    private val authRepository: AuthRepository by inject()

    private lateinit var authResultCode: String

    private constructor(activity: ComponentActivity) {
        activityResultLauncher = activity.registerForActivityResult(getContract()) {
            authResultCode = it
        }
    }

    private constructor(fragment: Fragment) {
        activityResultLauncher = fragment.registerForActivityResult(getContract()) {
            authResultCode = it
        }
    }

    suspend fun signInWithAuth(): VyouCredentials? {
        activityResultLauncher.launch(null)
        while (!::authResultCode.isInitialized) {
            delay(1000)
        }
        return authRepository.authenticateWithVyouCode(authResultCode)
    }

    private fun getContract(): ActivityResultContract<Void?, String> = object : ActivityResultContract<Void?, String>() {
        override fun createIntent(context: Context, input: Void?): Intent = AuthWebviewActivity.getCallingIntent(context)

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            intent?.let {
                intent.getStringExtra("code")
            }
    }

    companion object {
        fun withActivity(activity: ComponentActivity): VyouCore = VyouCore(activity)

        fun withFragment(fragment: Fragment): VyouCore = VyouCore(fragment)

        fun initialize(application: Application) {
            startKoin {
                androidContext(application)
                loadKoinModules(listOf(retrofitModule, vyouCoreModule))
            }
        }
    }
}

/*
    func signInWithGoogle(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void))
    func signInWithFacebook(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void))
    func signInWithApple(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void))
    func signInWithAuth(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void))
    func register(dto: RegisterDTO, completion: @escaping ((Result<Void, VYouError>) -> Void))
    func editProfile(dto: EditProfileDTO, completion: @escaping ((Result<Void, VYouError>) -> Void))
    func tenantProfile(completion: @escaping ((Result<VYouProfile, VYouError>) -> Void))
    func tenant(completion: @escaping ((Result<VYouTenant, VYouError>) -> Void))
    func signout(completion: @escaping ((Result<Void, VYouError>) -> Void))
*
* */