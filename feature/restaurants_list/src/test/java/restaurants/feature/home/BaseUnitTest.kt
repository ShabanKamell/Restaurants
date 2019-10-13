package com.restaurants.app

import android.content.Context
import restaurants.common.shared.koin.appModule
import restaurants.common.shared.util.SharedPref
import restaurants.feature.home.search.searchModule
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.test.AutoCloseKoinTest
import org.koin.test.mock.declareMock
import org.mockito.Mockito

open class BaseUnitTest: AutoCloseKoinTest() {

    @Before
    fun before() {
        startKoin {
            androidContext(Mockito.mock(Context::class.java))
            printLogger(Level.DEBUG)
            modules(listOf(appModule, searchModule))

            declareMock<SharedPref> {

            }
        }
    }
}