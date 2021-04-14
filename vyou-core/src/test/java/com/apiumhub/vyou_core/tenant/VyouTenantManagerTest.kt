package com.apiumhub.vyou_core.tenant

import com.apiumhub.vyou_core.TestUtils
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_core.di.Base64Encoder
import com.apiumhub.vyou_core.di.retrofitModule
import com.apiumhub.vyou_core.tenant.di.tenantModule
import com.apiumhub.vyou_core.tenant.domain.CreateCustomerDto
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VyouTenantManagerTest : KoinTest {

    private val base64Encoder: Base64Encoder = mockk()

    private val testRetrofitModule = module {
        single(override = true) {
            Retrofit
                .Builder()
                .baseUrl("https://test.vyou-app.com:8380")
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        single(override = true) { base64Encoder }
    }

    private val sut = Vyou.tenantManager

    private val createCustomerDto = TestUtils.randomizer.nextObject(CreateCustomerDto::class.java).copy(
        email = "${TestUtils.randomizer.nextObject(String::class.java)}@gmail.com",
        infoAccepted = true,
        privacyAccepted = true,
        termsConditionsAccepted = true,
        tenantRoles = emptyList(),
        dynamicFields = emptyMap(),
        mandatoryFields = emptyMap()
    )

    @Before
    fun setUp() {
        every{
            base64Encoder.vyouClientIdEncodedForAuth
        } returns "OFh4Ykd6YkJBQWJhcFE5dHBwRWFKdk5hOGp6S0R3OWgwS0JXUVl2VDJQNjdUNDJsQWxyWnJHN0tXZzo4WHhiR3piQkFBYmFwUTl0cHBFYUp2TmE4anpLRHc5aDBLQldRWXZUMlA2N1Q0MmxBbHJackc3S1dn"

        startKoin {
            modules(retrofitModule, tenantModule, testRetrofitModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getTenantShouldReturnValidOne() = runBlocking {
        assertNotNull(sut.tenant())
    }

    @Test
    fun register() = runBlocking {
        sut.register(createCustomerDto)
    }
}