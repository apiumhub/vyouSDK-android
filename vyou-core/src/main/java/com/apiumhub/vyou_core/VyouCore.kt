package com.apiumhub.vyou_core

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import com.apiumhub.vyou_core.auth.AuthWebviewActivity

object VyouCore {
    suspend fun signInWithAuth(activity: ComponentActivity): VyouCredentials? {
        val authActivity = activity.registerForActivityResult(getContract()) {
            Log.d("Result", "Result")
        }
        authActivity.launch("")
        return null
    }

    private fun getContract(): ActivityResultContract<String, VyouCredentials?> = object: ActivityResultContract<String, VyouCredentials?>() {
        override fun createIntent(context: Context, input: String?): Intent = Intent(context, AuthWebviewActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): VyouCredentials? {
            return null
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