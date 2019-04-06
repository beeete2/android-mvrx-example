package com.beeete2.android.androidmrvxexample.ui.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.airbnb.mvrx.*
import com.beeete2.android.androidmrvxexample.R
import com.beeete2.android.androidmrvxexample.ui.MvRxViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_counter.*
import java.util.concurrent.TimeUnit

data class CounterState(val count: Int = 0, val countRequest: Async<Int> = Uninitialized) : MvRxState

class CounterViewModel(state: CounterState) : MvRxViewModel<CounterState>(state) {
    fun incrementCount() = withState { state ->
        Observable.just(state.count + 1)
            .delay(1, TimeUnit.SECONDS)
            .execute { copy(countRequest = it, count = it() ?: count) }
    }
}

/**
 * Taken from https://gist.github.com/gpeal/91bec13458bbba2938e6ae597b70ef0f
 */
class CounterFragment : BaseMvRxFragment() {

    private val viewModel: CounterViewModel by fragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_counter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        counter_text.setOnClickListener {
            viewModel.incrementCount()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        counter_loading.isVisible = state.countRequest is Loading
        if (state.countRequest is Uninitialized) {
            counter_text.text = "Uninitialized"
        } else {
            counter_text.text = state.count.toString()
        }
    }

    @Module
    abstract class CounterFragmentModule {
        @ContributesAndroidInjector
        abstract fun contributeCounterFragment(): CounterFragment
    }

}
