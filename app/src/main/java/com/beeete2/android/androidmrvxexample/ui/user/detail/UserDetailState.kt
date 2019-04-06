package com.beeete2.android.androidmrvxexample.ui.user.detail

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.beeete2.android.androidmrvxexample.model.entity.User

data class UserDetailState(
    val args: UserDetailArgs? = null,
    val user: Async<User?> = Uninitialized
) : MvRxState
