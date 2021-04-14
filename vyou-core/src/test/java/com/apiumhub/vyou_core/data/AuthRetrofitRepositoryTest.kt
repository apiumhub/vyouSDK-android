package com.apiumhub.vyou_core.data

import com.apiumhub.vyou_core.di.Base64Encoder
import com.apiumhub.vyou_core.login.data.AuthApi
import com.apiumhub.vyou_core.login.data.AuthRetrofitRepository
import com.apiumhub.vyou_core.login.domain.LoginRepository
import com.apiumhub.vyou_core.login.facebook.FacebookAuthBody
import com.apiumhub.vyou_core.login.google.GoogleAuthBody
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class AuthRetrofitRepositoryTest {

    private val authApi: AuthApi = mockk(relaxed = true)
    private val manifestReader: ManifestReader = mockk()
    private val base64Encoder: Base64Encoder = mockk()

    private val sut: LoginRepository = AuthRetrofitRepository(authApi, manifestReader, base64Encoder)

    @Before
    fun setUp() {
        every {
            base64Encoder.vyouClientIdEncodedForAuth
        } returns ENCODED_CLIENT_ID
    }

    @Test
    fun shouldWriteToPreferencesWhenVyouLogin() = runBlockingTest {
        every {
            manifestReader.readVyouRedirectUri()
        } returns REDIRECT_URI

        sut.authenticateWithVyouCode(VYOU_CODE)
        coVerify {
            authApi.webAccessToken(VYOU_CODE, REDIRECT_URI)
        }
    }

    @Test
    fun shouldWriteToPreferencesWhenGoogleLogin() = runBlockingTest {
        every {
            manifestReader.readGoogleClientId()
        } returns GOOGLE_CLIENT_ID

        sut.authenticateWithGoogle(GOOGLE_TOKEN)
        coVerify {
            authApi.loginWithGoogle("Basic $ENCODED_CLIENT_ID", GoogleAuthBody(GOOGLE_TOKEN, GOOGLE_CLIENT_ID))
        }
    }

    @Test
    fun shouldWriteToPreferencesWhenFacebookLogin() = runBlockingTest {
        sut.authenticateWithFacebook(FACEBOOK_TOKEN)
        coVerify {
            authApi.loginWithFacebook("Basic $ENCODED_CLIENT_ID", FacebookAuthBody(FACEBOOK_TOKEN))
        }
    }

    companion object {
        const val ENCODED_CLIENT_ID = "encodedClientId"

        const val VYOU_CODE = "vyouCode"
        const val REDIRECT_URI = "redirectUri"

        const val GOOGLE_TOKEN = "googleToken"
        const val GOOGLE_CLIENT_ID = "googleClientId"

        const val FACEBOOK_TOKEN = "facebookToken"
    }
}