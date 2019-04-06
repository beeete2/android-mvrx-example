package com.beeete2.android.androidmrvxexample.model.repository

import com.beeete2.android.androidmrvxexample.model.entity.User
import io.reactivex.Maybe
import io.reactivex.Single

interface UserRepository {

    fun fetchUsers(): Single<List<User>>

    fun fetchUser(userId: Long): Single<User?>

}
