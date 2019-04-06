package com.beeete2.android.androidmrvxexample.model.entity

data class User(
    val userId: Long,
    val userName: String,
    val gender: Gender,
    val isAdmin: Boolean = false
)
