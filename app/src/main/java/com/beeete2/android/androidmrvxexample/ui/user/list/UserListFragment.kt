package com.beeete2.android.androidmrvxexample.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.airbnb.mvrx.*
import com.beeete2.android.androidmrvxexample.*
import com.beeete2.android.androidmrvxexample.R
import com.beeete2.android.androidmrvxexample.ui.BaseFragment
import com.beeete2.android.androidmrvxexample.ui.user.AppAssistedInjectableModule
import com.beeete2.android.androidmrvxexample.ui.user.MvRxEpoxyController
import com.beeete2.android.androidmrvxexample.ui.user.detail.UserDetailArgs
import com.beeete2.android.androidmrvxexample.ui.user.simpleController
import com.google.android.material.snackbar.Snackbar
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject

class UserListFragment : BaseFragment() {

    private val viewModel: UserListViewModel by fragmentViewModel()

    @Inject
    lateinit var viewModelFactory: UserListViewModel.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
            recyclerView.setController(epoxyController)
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.asyncSubscribe(UserListState::users,
            onSuccess = {
                swipe_refresh.isRefreshing = false
            },
            onFail = {
                swipe_refresh.isRefreshing = false
                Snackbar.make(view, "occurred error!", Snackbar.LENGTH_LONG).show()
            }
        )

        reload_button.setOnClickListener {
            viewModel.fetch()
        }

        empty_button.setOnClickListener {
            viewModel.fetchEmpty()
        }

        error_button.setOnClickListener {
            viewModel.fetchError()
        }

        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            // keep list when refreshing by swiped
            if (!state.isRefreshing) {
                super.invalidate()
            }
        }
    }

    override fun epoxyController(): MvRxEpoxyController = simpleController(viewModel) { state ->
        when (val users = state.users) {
            is Loading -> {
                rowLoading {
                    id("loading")
                }
            }
            is Success -> {
                users().also {
                    if (it.isEmpty()) {
                        rowEmpty {
                            id("empty")
                        }
                    } else {
                        it.forEach { user ->
                            userList {
                                id(user.userId)
                                user(user)
                                onClick { _, _, _, _ ->
                                    navigate(R.id.userDetailFragment, UserDetailArgs(userId = user.userId))
                                }
                            }
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
    abstract class UserListFragmentModule {
        @ContributesAndroidInjector(modules = [AppAssistedInjectableModule::class])
        abstract fun contributeUserListFragment(): UserListFragment
    }

}
