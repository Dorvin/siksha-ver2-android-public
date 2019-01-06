package com.wafflestudio.siksha.di

import com.wafflestudio.siksha.view.FavoriteFragment
import com.wafflestudio.siksha.view.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentModule {
    @ContributesAndroidInjector(modules = [MainFragmentFragmentModule::class])
    abstract fun mainFragment(): MainFragment

    @ContributesAndroidInjector(modules = [FavoriteFragmentFragmentModule::class])
    abstract fun favoriteFragment(): FavoriteFragment
}