package com.beeete2.android.androidmrvxexample.di

import android.app.Application
import com.beeete2.android.androidmrvxexample.App
import com.beeete2.android.androidmrvxexample.MainActivity
import com.beeete2.android.androidmrvxexample.ui.counter.CounterFragment
import com.beeete2.android.androidmrvxexample.ui.menu.MenuFragment
import com.beeete2.android.androidmrvxexample.ui.user.detail.UserDetailFragment
import com.beeete2.android.androidmrvxexample.ui.user.list.UserListFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        DatabaseModule::class,
        MainActivity.MainActivityModule::class,
        MenuFragment.MenuFragmentModule::class,
        UserListFragment.UserListFragmentModule::class,
        UserDetailFragment.UserDetailFragmentModule::class,
        CounterFragment.CounterFragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(app: App)
}
