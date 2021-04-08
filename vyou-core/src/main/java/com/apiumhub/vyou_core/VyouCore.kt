package com.apiumhub.vyou_core

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_core.auth.AuthWebviewActivity

class VyouCore {

    private val activityResultLauncher: ActivityResultLauncher<String>

    private constructor(activity: ComponentActivity) {
        activityResultLauncher = activity.registerForActivityResult(getContract()) {
            Log.d("Result", "asdf")
        }
    }

    private constructor(fragment: Fragment) {
        activityResultLauncher = fragment.registerForActivityResult(getContract()) {
            Log.d("Result", "asdf")
        }
    }

    suspend fun signInWithAuth(): VyouCredentials? {
        activityResultLauncher.launch("")

        return null
    }

    private fun getContract(): ActivityResultContract<String, VyouCredentials?> = object : ActivityResultContract<String, VyouCredentials?>() {
        override fun createIntent(context: Context, input: String?): Intent = AuthWebviewActivity.getCallingIntent(context)

        override fun parseResult(resultCode: Int, intent: Intent?): VyouCredentials? {
            return null
        }
    }

    companion object {
        fun withActivity(activity: ComponentActivity): VyouCore = VyouCore(activity)

        fun withFragment(fragment: Fragment): VyouCore = VyouCore(fragment)
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