package com.example.restaurantreview.ui.darkmode

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityDarkModeBinding
import com.example.restaurantreview.ui.main.SearchViewModel

class DarkModeActivity : AppCompatActivity() {

    private lateinit var darkModeBinding: ActivityDarkModeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        darkModeBinding = ActivityDarkModeBinding.inflate(layoutInflater)
        setContentView(darkModeBinding.root)
        val searchViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SearchViewModel::class.java]


        val pref = SettingPreferences.getInstance(application.dataStore)
        val darkModeViewModel =
            ViewModelProvider(this, DarkModeViewModelFactory(pref))[DarkModeViewModel::class.java]

        darkModeViewModel.getThemeSettings().observe(this) { isDarkActive: Boolean ->
            if (isDarkActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeBinding.switchMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeBinding.switchMode.isChecked = false
                darkModeBinding.switchMode.trackTintList =
                ColorStateList.valueOf(R.color.github_black_1.toInt())
            }
        }

        darkModeBinding.switchMode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            darkModeViewModel.saveThemeSetting(isChecked)
        }
    }
}