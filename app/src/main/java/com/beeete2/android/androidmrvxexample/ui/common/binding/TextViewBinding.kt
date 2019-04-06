package com.beeete2.android.androidmrvxexample.ui.common.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.beeete2.android.androidmrvxexample.model.entity.Gender

@BindingAdapter("android:text")
fun TextView.setTextGender(gender: Gender) {
    text = when (gender) {
        Gender.MALE -> "Male"
        Gender.FEMALE -> "Female"
        Gender.UNKNOWN -> "Unknown"
    }
}
