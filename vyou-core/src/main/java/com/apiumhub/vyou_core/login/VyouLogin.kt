package com.apiumhub.vyou_core.login

import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_core.login.domain.LoginRepository
import com.apiumhub.vyou_core.login.facebook.FacebookSignInCollaborator
import com.apiumhub.vyou_core.login.google.GoogleSignInCollaborator
import com.apiumhub.vyou_core.login.vyou_auth.VyouSignInCollaborator
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class VyouLogin internal constructor(actResultCaller: ActivityResultCaller) : KoinComponent {
    //Need to eager inject due to ActivityResultCaller needing to register for result before onStart

    private val googleSignIn: GoogleSignInCollaborator = get { parametersOf(actResultCaller) }
    private val vyouSignIn: VyouSignInCollaborator = get { parametersOf(actResultCaller) }
    private val facebookSignIn: FacebookSignInCollaborator = get { parametersOf(actResultCaller) }

    private val authRepository: LoginRepository by inject()

    suspend fun signInWithAuth() =
        authRepository.authenticateWithVyouCode(vyouSignIn.start())

    suspend fun signInWithGoogle() =
        authRepository.authenticateWithGoogle(googleSignIn.start())

    suspend fun signInWithFacebook(fragment: Fragment) =
        authRepository.authenticateWithFacebook(facebookSignIn.start(fragment))

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookSignIn.onActivityResult(requestCode, resultCode, data)
    }
}

/*
    func signInWithGoogle(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void)) √
    func signInWithFacebook(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void)) √
    func signInWithAuth(completion: @escaping ((Result<VYouCredentials, VYouError>) -> Void)) √
 */