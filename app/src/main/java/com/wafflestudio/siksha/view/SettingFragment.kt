package com.wafflestudio.siksha.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.wafflestudio.siksha.R
import com.wafflestudio.siksha.model.MenuResponse
import com.wafflestudio.siksha.network.SikshaApi
import com.wafflestudio.siksha.preference.SikshaPreference
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_setting.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SettingFragment : Fragment() {

    @Inject
    lateinit var preference: SikshaPreference
    @Inject
    lateinit var api: SikshaApi

    companion object {
        fun newInstance(): SettingFragment = SettingFragment()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        view.text_siksha_information.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_setting_view, SettingVersionFragment.newInstance())
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        view.img_new.visibility = View.INVISIBLE
        view.text_reorder.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_setting_view, SettingReorderMainFragment.newInstance())
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        view.text_reorder_favorite.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_setting_view, SettingReorderFavoriteFragment.newInstance())
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        view.img_check.setImageResource(if (preference.visibleNoMenu) R.drawable.check else R.drawable.check_s)
        view.img_check.setOnClickListener {
            var preBoolValue = preference.visibleNoMenu
            preference.visibleNoMenu = !preBoolValue
            view.img_check.setImageResource(if (preference.visibleNoMenu) R.drawable.check else R.drawable.check_s)
        }
        view.text_refresh.text = preference.latestUpdate
        view.text_reload.setOnClickListener {
            view.img_refresh.isEnabled = false
            val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
            view.img_refresh.startAnimation(rotateAnimation)
            api.fetchMenus().enqueue(object : Callback<MenuResponse> {
                override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                    Toast.makeText(context, "????????? ??????????????? ??????????????????", Toast.LENGTH_LONG).show()
                    rotateAnimation.cancel()
                    rotateAnimation.reset()
                    view.img_refresh.isEnabled = true
                }

                override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            preference.menuResponse = it
                            preference.latestUpdate = SimpleDateFormat("MM. dd. HH:mm ", Locale.KOREA).format(Date())
                            view.text_refresh.text = preference.latestUpdate
                            Toast.makeText(context, "????????? ??????????????? ??????????????????", Toast.LENGTH_LONG).show()
                        }
                    } else Toast.makeText(context, "????????? ??????????????? ??????????????????", Toast.LENGTH_LONG).show()
                    rotateAnimation.cancel()
                    rotateAnimation.reset()
                    view.img_refresh.isEnabled = true
                }
            })
        }
        return view
    }
}