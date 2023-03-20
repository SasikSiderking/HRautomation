package com.example.hrautomation.presentation.view.restaurants.restaurant_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View.OnClickListener
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityRestaurantReviewBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.presentation.model.restaurants.ReviewActivityResult
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams

class RestaurantReviewActivity : BaseActivity<ActivityRestaurantReviewBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityRestaurantReviewBinding
        get() = { ActivityRestaurantReviewBinding.inflate(layoutInflater) }

    private val restaurantName: String by lazy { intent.getStringExtra(RESTAURANT_NAME)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = restaurantName
    }

    override fun initInject() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun initUI() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(contentLayout, checkLayout, reviewRating, cancelButton, addButton),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    //                Тут сразу показываю CONTENT потому что в общем-то подгрузок никаких нет
                    //                и логически свитчить не где
                    //                btw возможно тогда и лоадер не нужен, оставляю для структурной идентичности
                    //                с кнопками перезагрузки то же самое
                    initState = ContentLoadingState.CONTENT
                )
            )
            reusableReload.reloadButton.setOnClickListener {
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }
        }
    }

    override fun initObserves() = Unit

    override fun initListeners() {
        binding.addButton.setOnClickListener(onAddButtonClickListener)
        binding.cancelButton.setOnClickListener(onCancelButtonClickListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override val exceptionObserver: Observer<Throwable?>
        //    Тут можно поругаться на меня
        get() = Observer {}

    private val onAddButtonClickListener = OnClickListener {
        with(binding) {
            if (checkField.text.toString().isNotEmpty()) {
                val reviewActivityResult = ReviewActivityResult(
                    contentField.text.toString(),
                    checkField.text.toString().toFloat().toInt(),
                    reviewRating.rating
                )
                intent.putExtra(RESULT, reviewActivityResult)
                setResult(RESULT_CODE, intent)

                finish()
            }
        }
    }

    private val onCancelButtonClickListener = OnClickListener {
        finish()
    }

    companion object {

        const val RESULT_CODE = 993

        const val RESTAURANT_NAME = "restaurantName"

        const val RESULT = "reviewActivityResult"

        fun createIntent(context: Context, restaurantName: String): Intent {
            val intent = Intent(context, RestaurantReviewActivity::class.java)
            intent.putExtra(RESTAURANT_NAME, restaurantName)
            return intent
        }
    }
}