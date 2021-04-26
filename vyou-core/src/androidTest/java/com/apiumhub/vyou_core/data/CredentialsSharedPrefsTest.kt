package com.apiumhub.vyou_core.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.apiumhub.vyou_core.di.sharedPrefsModule
import com.apiumhub.vyou_core.login.data.CredentialsSharedPrefs
import com.apiumhub.vyou_core.login.domain.VYouCredentials
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class CredentialsSharedPrefsTest : KoinTest {

    private val sut: CredentialsSharedPrefs by inject()
    private val credentials = VYouCredentials(
        idToken = "idToken",
        accessToken = "accessToken",
        tokenType = "tokenType",
        refreshToken = "refreshToken",
        tenantCompliant = true,
        tenantConsentCompliant = true,
        expiresIn = 0,
        scope = "scope"
    )

    @Before
    fun setUp() {
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(sharedPrefsModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        sut.clearCredentials()
    }

    @Test
    fun shouldReturnNullWhenReadingBeforeWriting() {
        val actual = sut.readVYouCredentials()
        assertEquals(null, actual)
    }

    @Test
    fun shouldWriteAndReadFromEncryptedSharedPrefs() {
        sut.storeVYouCredentials(credentials)
        val actual = sut.readVYouCredentials()
        assertEquals(credentials, actual)
    }
}