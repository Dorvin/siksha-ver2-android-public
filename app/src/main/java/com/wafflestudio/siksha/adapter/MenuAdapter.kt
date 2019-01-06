package com.wafflestudio.siksha.adapter

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.siksha.R
import com.wafflestudio.siksha.model.Menu
import com.wafflestudio.siksha.model.Restaurant
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter(
        getItems: () -> List<Menu>,
        private val infoButtonListener: (Restaurant) -> Unit,
        private val favoriteButtonListener: (Restaurant) -> Unit
) : BaseAdapter<Menu, MenuAdapter.MenuHolder>(getItems) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_menu, parent, false)
        return MenuHolder(view, infoButtonListener) {
            favoriteButtonListener(it)
            refresh()
        }
    }

    class MenuHolder(
            view: View,
            private val infoButtonListener: (Restaurant) -> Unit,
            private val favoriteButtonListener: (Restaurant) -> Unit
    ) : BaseViewHolder<Menu>(view) {
        override fun bind(data: Menu) {
            view.text_restaurant_name.text = data.restaurant.krName
            view.list_meal.layoutManager = LinearLayoutManager(view.context)
            view.list_meal.adapter = MealAdapter { data.meals }
            view.list_meal.isNestedScrollingEnabled = false
            if (data.restaurant.favorite) {
                view.button_favorite.setImageResource(R.drawable.restaurant_star_s)
            } else {
                view.button_favorite.setImageResource(R.drawable.restaurant_star_n)
            }
            view.button_info.setOnClickListener { infoButtonListener(data.restaurant) }
            view.button_favorite.setOnClickListener { favoriteButtonListener(data.restaurant) }
        }
    }
}