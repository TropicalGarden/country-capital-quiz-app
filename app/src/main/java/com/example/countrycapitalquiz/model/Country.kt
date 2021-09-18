package com.example.countrycapitalquiz.model

import androidx.annotation.DrawableRes

data class Country(
    val countryName: String,
    val capitalName: String,
    @DrawableRes val imageResourceId: Int
)