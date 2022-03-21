package thedragonspb.testjokesapp.app

import android.app.Application
import thedragonspb.testjokesapp.app.di.AppComponent
import thedragonspb.testjokesapp.app.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(this).build()
        Timber.plant(DebugTree())
    }
}