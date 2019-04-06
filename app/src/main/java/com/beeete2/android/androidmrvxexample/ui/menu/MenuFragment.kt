package com.beeete2.android.androidmrvxexample.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.fragmentViewModel
import com.beeete2.android.androidmrvxexample.R
import com.beeete2.android.androidmrvxexample.ui.BaseFragment
import com.beeete2.android.androidmrvxexample.ui.user.MvRxEpoxyController
import com.beeete2.android.androidmrvxexample.ui.user.simpleController
import com.beeete2.android.androidmrvxexample.ui.view.menuItemView
import dagger.Module
import dagger.android.ContributesAndroidInjector

class MenuFragment : BaseFragment() {

    private val viewModel: MenuViewModel by fragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
            recyclerView.setController(epoxyController)
        }
    }

    override fun epoxyController(): MvRxEpoxyController = simpleController(viewModel) {
        menuItemView {
            id("user")
            label(R.string.user_menu_title)
            onClickListener { _ ->
                navigate(R.id.userListFragment)
            }
        }
        menuItemView {
            id("counter")
            label(R.string.counter_menu_title)
            onClickListener { _ ->
                navigate(R.id.counterFragment)
            }
        }
    }

    @Module
    abstract class MenuFragmentModule {
        @ContributesAndroidInjector
        abstract fun contributeMenuFragment(): MenuFragment
    }

}
