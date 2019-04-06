package com.beeete2.android.androidmrvxexample.model.repository

import com.beeete2.android.androidmrvxexample.model.entity.Gender
import com.beeete2.android.androidmrvxexample.model.entity.User
import io.reactivex.Single

class UserLocalRepository : UserRepository {

    override fun fetchUsers(): Single<List<User>> {
        return Single.just(
            listOf(
                User(userId = 1, userName = "Adam", gender = Gender.MALE),
                User(userId = 2, userName = "Benjamin", gender = Gender.MALE),
                User(userId = 3, userName = "Cameron", gender = Gender.MALE)
            )
        )
    }

    override fun fetchUser(userId: Long): Single<User?> {
        return fetchUsers()
            .flatMap { users ->
                Single.just(users.firstOrNull { userId == it.userId })
            }
    }

}
