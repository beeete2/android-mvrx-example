package com.beeete2.android.androidmrvxexample.ui.user.list

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.beeete2.android.androidmrvxexample.MainActivity
import com.beeete2.android.androidmrvxexample.model.entity.User
import com.beeete2.android.androidmrvxexample.model.repository.UserRepository
import com.beeete2.android.androidmrvxexample.ui.MvRxViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class UserListViewModel @AssistedInject constructor(
    @Assisted initialState: UserListState,
    private val userRepository: UserRepository
) : MvRxViewModel<UserListState>(initialState) {

    init {
        fetch()
    }

    fun fetch() {
        withState { state ->
            if (state.users is Loading) return@withState

            userRepository
                .fetchUsers()
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .execute { copy(users = it) }
        }
    }

    fun fetchEmpty() {
        withState { state ->
            if (state.users is Loading) return@withState

            Single.just<List<User>>(emptyList())
                .delay(3, TimeUnit.SECONDS, true)
                .execute { copy(users = it) }
        }
    }

    fun fetchError() {
        withState { state ->
            if (state.users is Loading) return@withState

            Single.error<List<User>>(Exception("Error"))
                .delay(3, TimeUnit.SECONDS, true)
                .execute { copy(users = it) }
        }
    }

    fun refresh() {
        withState { state ->
            if (state.users is Loading) return@withState

            userRepository
                .fetchUsers()
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .execute {
                    if (it is Loading) {
                        copy(users = it, isRefreshing = true)
                    } else {
                        copy(users = it, isRefreshing = false)
                    }
                }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: UserListState): UserListViewModel
    }

    companion object : MvRxViewModelFactory<UserListViewModel, UserListState> {
        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: UserListState): UserListViewModel? {
            val fragmentViewModelContext = viewModelContext as FragmentViewModelContext
            return (fragmentViewModelContext.fragment as UserListFragment).viewModelFactory.create(state)
        }
    }

}
