package com.clearylabs.stubapp.di

import com.clearylabs.stubapp.ui.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel(get()) }
}