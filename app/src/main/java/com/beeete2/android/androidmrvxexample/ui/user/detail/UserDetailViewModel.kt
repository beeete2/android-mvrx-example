package com.beeete2.android.androidmrvxexample.ui.user.detail

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.beeete2.android.androidmrvxexample.model.repository.UserRepository
import com.beeete2.android.androidmrvxexample.ui.MvRxViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class UserDetailViewModel @AssistedInject constructor(
    @Assisted initialState: UserDetailState,
    private val userRepository: UserRepository
) : MvRxViewModel<UserDetailState>(initialState) {

    init {
        fetch()
    }

    private fun fetch() {
        withState { state ->
            if (state.user is Loading) return@withState

            state.args?.let { args ->
                userRepository.fetchUser(args.userId)
                    .delay(3, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .execute { copy(user = it) }
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: UserDetailState): UserDetailViewModel
    }

    companion object : MvRxViewModelFactory<UserDetailViewModel, UserDetailState> {
        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: UserDetailState): UserDetailViewModel? {
            val fragmentViewModelContext = viewModelContext as FragmentViewModelContext
            return (fragmentViewModelContext.fragment as UserDetailFragment).viewModelFactory.create(state)
        }

        override fun initialState(viewModelContext: ViewModelContext): UserDetailState? {
            val args = viewModelContext.args() as UserDetailArgs
            return UserDetailState(args = args)
        }
    }

}
