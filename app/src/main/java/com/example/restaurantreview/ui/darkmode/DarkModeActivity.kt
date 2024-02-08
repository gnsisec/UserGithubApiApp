package com.example.restaurantreview.ui.darkmode

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityDarkModeBinding

class DarkModeActivity : AppCompatActivity() {

    private lateinit var darkModeBinding: ActivityDarkModeBinding
    private lateinit var pref: SettingPreferences
    private val darkModeViewModel: DarkModeViewModel by viewModels { DarkModeViewModelFactory(pref) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        darkModeBinding = ActivityDarkModeBinding.inflate(layoutInflater)
        setContentView(darkModeBinding.root)

        pref = SettingPreferences.getInstance(application.dataStore)

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