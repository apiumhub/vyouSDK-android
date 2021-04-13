package com.apiumhub.vyou_core.data

import com.apiumhub.vyou_core.VyouCredentials
import com.apiumhub.vyou_core.domain.AuthRepository
import com.apiumhub.vyou_core.facebook.FacebookAuthBody
import com.apiumhub.vyou_core.google.GoogleAuthBody
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class AuthRetrofitRepositoryTest {

    private val vyouApi: VyouApi = mockk()
    private val sharedPrefs: CredentialsSharedPrefs = mockk(relaxed = true)
    private val manifestReader: ManifestReader = mockk()

    private val vyouCredentials: VyouCredentials = mockk()

    private val sut: AuthRepository = AuthRetrofitRepository(vyouApi, sharedPrefs, manifestReader)

    @Test
    fun shouldWriteToPreferencesWhenVyouLogin() = runBlockingTest {
        every {
            manifestReader.readVyouRedirectUri()
        } returns REDIRECT_URI

        coEvery {
            vyouApi.webAccessToken(VYOU_CODE, REDIRECT_URI)
        } returns vyouCredentials

        sut.authenticateWithVyouCode(VYOU_CODE)
        verify {
            sharedPrefs.storeVyouCredentials(vyouCredentials)
        }
    }

    @Test
    fun shouldWriteToPreferencesWhenGoogleLogin() = runBlockingTest {
        every {
            manifestReader.readGoogleClientId()
        } returns GOOGLE_CLIENT_ID

        coEvery {
            vyouApi.loginWithGoogle(GoogleAuthBody(GOOGLE_TOKEN, GOOGLE_CLIENT_ID))
        } returns vyouCredentials

        sut.authenticateWithGoogle(GOOGLE_TOKEN)
        verify {
            sharedPrefs.storeVyouCredentials(vyouCredentials)
        }
    }

    @Test
    fun shouldWriteToPreferencesWhenFacebookLogin() = runBlockingTest {
        coEvery {
            vyouApi.loginWithFacebook(FacebookAuthBody(FACEBOOK_TOKEN))
        } returns vyouCredentials

        sut.authenticateWithFacebook(FACEBOOK_TOKEN)
        verify {
            sharedPrefs.storeVyouCredentials(vyouCredentials)
        }
    }

    companion object {
        const val VYOU_CODE = "vyouCode"
        const val REDIRECT_URI = "redirectUri"

        const val GOOGLE_TOKEN = "googleToken"
        const val GOOGLE_CLIENT_ID = "googleClientId"

        const val FACEBOOK_TOKEN = "facebookToken"
    }
}