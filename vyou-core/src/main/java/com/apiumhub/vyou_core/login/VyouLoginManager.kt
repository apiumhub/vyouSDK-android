package com.apiumhub.vyou_core.login

import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_core.domain.VyouResult
import com.apiumhub.vyou_core.domain.VyouResult.Failure
import com.apiumhub.vyou_core.domain.VyouResult.Success
import com.apiumhub.vyou_core.login.domain.LoginRepository
import com.apiumhub.vyou_core.login.facebook.FacebookSignInCollaborator
import com.apiumhub.vyou_core.login.google.GoogleSignInCollaborator
import com.apiumhub.vyou_core.login.vyou_auth.VyouSignInCollaborator
import com.apiumhub.vyou_core.session.domain.SessionRepository
import com.apiumhub.vyou_core.session.domain.VyouSession
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class VyouLoginManager internal constructor(actResultCaller: ActivityResultCaller) : KoinComponent {
    //Need to eager inject due to ActivityResultCaller needing to register for result before onStart
    private val googleSignIn: GoogleSignInCollaborator = get { parametersOf(actResultCaller) }
    private val vyouSignIn: VyouSignInCollaborator = get { parametersOf(actResultCaller) }
    private val facebookSignIn: FacebookSignInCollaborator = get { parametersOf(actResultCaller) }

    private val authRepository: LoginRepository by inject()
    private val sessionRepository: SessionRepository by inject()

    suspend fun signInWithAuth() =
        runCatching {
            when (val result = vyouSignIn.start()) {
                is Success -> authRepository.authenticateWithVyouCode(result.value).run(sessionRepository::storeSession)
                is Failure -> throw result.error
            }
        }.fold(::Success, ::Failure)

    suspend fun signInWithGoogle() =
        runCatching {
            when (val result = googleSignIn.start()) {
                is Success -> authRepository.authenticateWithGoogle(result.value).run(sessionRepository::storeSession)
                is Failure -> throw result.error
            }
        }.fold(::Success, ::Failure)

    suspend fun signInWithFacebook(fragment: Fragment) =
        runCatching {
            when (val result = facebookSignIn.start(fragment)) {
                is Success -> authRepository.authenticateWithFacebook(result.value).run(sessionRepository::storeSession)
                is Failure -> throw result.error
            }
        }.fold(::Success, ::Failure)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookSignIn.onActivityResult(requestCode, resultCode, data)
    }
}