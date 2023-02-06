package stavka.stavki.games.base

import android.app.Application
import stavka.stavki.games.di.appModule
import stavka.stavki.games.di.networkModule
import stavka.stavki.games.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import stavka.stavki.games.BuildConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(listOf(appModule, networkModule, viewModelModule))
        }
    }
}