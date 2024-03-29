package com.example.hrautomation.presentation.view.restaurants.map

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.marginBottom
import com.example.hrautomation.databinding.FragmentRestaurantsCardBinding
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantItem
import com.example.hrautomation.utils.Updatable
import java.io.Closeable

class RestaurantCard(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs), Updatable<ListRestaurantItem>, Closeable {

    private var listener: OnCardClickListener? = null

    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) : this(context, attrs, defStyleAttrs, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var _binding: FragmentRestaurantsCardBinding? = null
    private val binding: FragmentRestaurantsCardBinding
        get() = _binding!!

    init {

        _binding = FragmentRestaurantsCardBinding.inflate(LayoutInflater.from(context), this)

        initListener()
    }

    private fun initListener() {
        binding.closeButton.setOnClickListener {
            this.listener?.onClick(CardAction.CLOSE)
        }

        binding.details.setOnClickListener {
            this.listener?.onClick(CardAction.GO_TO)
        }
    }

    fun setCardClickListener(listener: OnCardClickListener) {
        this.listener = listener
    }

    override fun update(item: ListRestaurantItem) {
        visibility = VISIBLE
        with(binding) {
            restaurantName.text = item.name
            restaurantRating.text = item.rating.toString()
            restaurantAddress.text = item.address
            restaurantStatusCheck.text = item.statusAndCheck
        }
        ObjectAnimator.ofFloat(this, "translationY", BASE_POSITION).apply {
            duration = ANIMATION_DURATION
            start()
        }
    }

    override fun close() {
        ObjectAnimator.ofFloat(this, "translationY", this.height.toFloat() + this.marginBottom.toFloat()).apply {
            duration = ANIMATION_DURATION
            start()
        }
    }

    companion object {
        const val ANIMATION_DURATION = 500L
        const val BASE_POSITION = 0f
    }
}

enum class CardAction {
    CLOSE,
    GO_TO
}

fun interface OnCardClickListener {
    fun onClick(cardAction: CardAction)
}