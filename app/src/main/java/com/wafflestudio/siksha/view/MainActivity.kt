package com.wafflestudio.siksha.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wafflestudio.siksha.R
import com.wafflestudio.siksha.model.MenuResponse
import com.wafflestudio.siksha.network.SikshaApi
import com.wafflestudio.siksha.preference.SikshaPreference
import dagger.android.AndroidInjection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {
    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    @Inject
    lateinit var api: SikshaApi
    @Inject
    lateinit var preference: SikshaPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api.fetchMenus().enqueue(object : Callback<MenuResponse> {
            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                Timber.d("${t.message}")
            }

            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                response.body()?.let {
                    preference.menuResponse = it
                } ?: let {
                    Timber.d("${response.errorBody()?.string()}")
                }
            }
        })
    }
}
