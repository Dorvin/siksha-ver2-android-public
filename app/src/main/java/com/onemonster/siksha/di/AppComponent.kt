package com.onemonster.siksha.di

import com.onemonster.siksha.SikshaApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityModule::class,
    NetworkModule::class])
interface AppComponent : AndroidInjector<SikshaApplication>
