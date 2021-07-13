package kg.tutorialapp.weatherproject

import android.app.Application
import kg.tutorialapp.weatherproject.di.dataModule
import kg.tutorialapp.weatherproject.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application()  {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(vmModule, dataModule))
        }
    }
}