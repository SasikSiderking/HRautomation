package com.example.hrautomation.presentation.view.restaurants.map

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.FrameLayout
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentRestaurantsCardBinding
import com.example.hrautomation.presentation.model.restaurants.BuildingItem
import com.example.hrautomation.utils.Updatable
import java.io.Closeable

class RestaurantCard(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs), Updatable<BuildingItem>, Closeable {

    private var listener: OnCardClickListener? = null

    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) : this(context, attrs, defStyleAttrs, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var _binding: FragmentRestaurantsCardBinding? = null
    private val binding: FragmentRestaurantsCardBinding
        get() = _binding!!

    private lateinit var animationVisible: Animation
    private lateinit var animationInvisible: Animation

    init {
        animationVisible = loadAnimation(context, R.anim.card_visible)
        animationInvisible = loadAnimation(context, R.anim.card_invisible)

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

    override fun update(item: BuildingItem) {
        item.let {
            with(binding) {
                restaurantName.text = item.address
                restaurantRating.text = item.address
                restaurantAddress.text = item.address
                restaurantStatusCheck.text = item.address
            }
            startAnimation(animationVisible)
            this.visibility = VISIBLE
        }
    }

    override fun close() {
        this.visibility = INVISIBLE
        startAnimation(animationInvisible)
    }
}

enum class CardAction {
    CLOSE,
    GO_TO
}

fun interface OnCardClickListener {
    fun onClick(cardAction: CardAction)
}