package com.beeete2.android.androidmrvxexample.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.beeete2.android.androidmrvxexample.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MenuItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val label by lazy { findViewById<TextView>(R.id.label) }
    private val clickOverlay by lazy { findViewById<View>(R.id.click_overlay) }

    init {
        inflate(context, R.layout.item_menu, this)
    }

    @TextProp
    fun setLabel(text: CharSequence) {
        label.text = text
    }

    @CallbackProp
    fun onClickListener(listener: View.OnClickListener?) {
        clickOverlay.setOnClickListener(listener)
    }

}
