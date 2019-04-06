package com.beeete2.android.androidmrvxexample.ui.user.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.beeete2.android.androidmrvxexample.model.entity.User

data class UserListState(
    val users: Async<List<User>> = Uninitialized,
    val isRefreshing: Boolean = false
) : MvRxState
