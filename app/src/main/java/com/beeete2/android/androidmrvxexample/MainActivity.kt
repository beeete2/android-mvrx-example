package com.beeete2.android.androidmrvxexample

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.beeete2.android.androidmrvxexample.ui.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setupWithNavController(findNavController(R.id.nav_host_fragment))
    }

    @Module
    abstract class MainActivityModule {
        @ContributesAndroidInjector
        abstract fun contributeMainActivity(): MainActivity
    }

}
