package com.apiumhub.vyou_core

import android.app.Application
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_core.auth.VyouSignInCollaborator
import com.apiumhub.vyou_core.data.ManifestReader
import com.apiumhub.vyou_core.di.retrofitModule
import com.apiumhub.vyou_core.di.sharedPrefsModule
import com.apiumhub.vyou_core.di.vyouCoreModule
import com.apiumhub.vyou_core.domain.AuthRepository
import com.apiumhub.vyou_core.facebook.FacebookSignInCollaborator
import com.apiumhub.vyou_core.google.GoogleSignInCollaborator
import com.facebook.FacebookSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

class VyouCore(actResultCaller: ActivityResultCaller) : KoinComponent {

    //Need to eager inject due to ActivityResultCaller needing to register for result before onStart
    private val googleSignIn: GoogleSignInCollaborator = get { parametersOf(actResultCaller) }
    private val vyouSignIn: VyouSignInCollaborator = get { parametersOf(actResultCaller) }
    private val facebookSignIn: FacebookSignInCollaborator = get { parametersOf(actResultCaller) }

    private val authRepository: AuthRepository by inject()

    val loggedIn: Boolean get() = authRepository.isUserLoggedIn

    suspend fun signInWithAuth() =
        authRepository.authenticateWithVyouCode(vyouSignIn.start())

    suspend fun signInWithGoogle() =
        authRepository.authenticateWithGoogle(googleSignIn.start())

    suspend fun signInWithFacebook(fragment: Fragment) =
        authRepository.authenticateWithFacebook(facebookSignIn.start(fragment))

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookSignIn.onActivityResult(requestCode, resultCode, data)
    }

    fun signOut() {
        authRepository.signOut()
    }

    companion object {
        fun initialize(application: Application) {
            startKoin {
                androidContext(application)
                loadKoinModules(listOf(retrofitModule, vyouCoreModule, sharedPrefsModule))
            }
            val manifestReader = ManifestReader(application)
            FacebookSdk.setApplicationId(manifestReader.readFacebookClientId())
            FacebookSdk.sdkInitialize(application)

        }
    }
}

/*
    func signInWithGoogle(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void)) √
    func signInWithFacebook(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void)) √
    func signInWithAuth(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void)) √
    func register(dto: RegisterDTO, completion: @escaping ((Result<Void, VYouError>) -> Void))
    func editProfile(dto: EditProfileDTO, completion: @escaping ((Result<Void, VYouError>) -> Void))
    func tenantProfile(completion: @escaping ((Result<VYouProfile, VYouError>) -> Void))
    func tenant(completion: @escaping ((Result<VYouTenant, VYouError>) -> Void))
    func signout(completion: @escaping ((Result<Void, VYouError>) -> Void))
* */