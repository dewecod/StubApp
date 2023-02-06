package stavka.stavki.games.di

import stavka.stavki.games.ui.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel(get()) }
}