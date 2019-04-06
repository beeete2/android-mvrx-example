package com.beeete2.android.androidmrvxexample.ui.user.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.beeete2.android.androidmrvxexample.*
import com.beeete2.android.androidmrvxexample.ui.BaseFragment
import com.beeete2.android.androidmrvxexample.ui.user.AppAssistedInjectableModule
import com.beeete2.android.androidmrvxexample.ui.user.MvRxEpoxyController
import com.beeete2.android.androidmrvxexample.ui.user.simpleController
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject


@SuppressLint("ParcelCreator")
@Parcelize
data class UserDetailArgs(val userId: Long) : Parcelable

class UserDetailFragment : BaseFragment() {

    private val viewModel: UserDetailViewModel by fragmentViewModel()

    @Inject
    lateinit var viewModelFactory: UserDetailViewModel.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_detail, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
            recyclerView.setController(epoxyController)
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun epoxyController(): MvRxEpoxyController = simpleController(viewModel) { state ->
        when (val user = state.user) {
            is Loading -> {
                rowLoading {
                    id("loading")
                }
            }
            is Success -> {
                user().also {
                    if (it == null) {
                        rowEmpty {
                            id("error")
                        }
                    } else {
                        userDetail {
                            id(it.userId)
                            user(it)
                        }
                    }
                }
            }
            is Fail -> {
                rowError {
                    id("error")
                }
            }
        }
    }

    @Module
    abstract class UserDetailFragmentModule {
        @ContributesAndroidInjector(modules = [AppAssistedInjectableModule::class])
        abstract fun contributeUserDetailFragment(): UserDetailFragment
    }
}
