package com.example.hrautomation.presentation.view.restaurants

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentRestaurantsCardBinding

import com.example.hrautomation.presentation.model.restaurants.ListRestaurantItem
import timber.log.Timber

enum class CardAction {
    CrossClicked,
    DetailsClicked
}

fun interface OnCardClickListener {
    fun onClick(cardAction: CardAction)
}

class RestaurantCard(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs) {

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

        context.obtainStyledAttributes(attrs, R.styleable.RestaurantCard, defStyleAttrs, defStyleRes).recycle()
    }

    fun updateViewData(restaurant: ListRestaurantItem? = null) {
        restaurant?.let {
            Timber.e("Restaurant: $restaurant")
            with(binding) {
                restaurantName.text = restaurant.name
                restaurantRating.text = restaurant.rating.toString()
                restaurantAddress.text = restaurant.address
                restaurantStatus.text = restaurant.status.status
                restaurantCheck.text = restaurant.check.toString()
            }
            this.visibility = VISIBLE
        } ?: run {
            this.visibility = GONE
        }
    }

    private fun initListener() {
        binding.cross.setOnClickListener {
            this.listener?.onClick(CardAction.CrossClicked)
        }

        binding.details.setOnClickListener {
            this.listener?.onClick(CardAction.DetailsClicked)
        }
    }

    fun setListener(listener: OnCardClickListener) {
        this.listener = listener
    }
}