package com.vbta.currenciesta.presentation

import android.app.Application
import com.vbta.currenciesta.BuildConfig
import com.vbta.currenciesta.data.injection.dataModule
import com.vbta.currenciesta.domain.injection.domainModule
import com.vbta.currenciesta.presentation.injection.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class CApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupInjection()
        setupTimber()
    }

    private fun setupInjection() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@CApplication)
            modules(
                listOf(presentationModule, domainModule, dataModule)
            )
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}