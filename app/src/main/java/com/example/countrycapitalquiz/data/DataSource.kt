package com.example.countrycapitalquiz.data

import android.content.Context
import com.example.countrycapitalquiz.R
import com.example.countrycapitalquiz.model.Country

const val DEF_TYPE = "drawable"

class DataSource(private val context: Context) {

    fun loadCountries(): List<Country> {
        context
        val countries = context.resources.getStringArray(R.array.countries)
        return countries.map {
            Country(
                formatName(it.substringAfter("|")),
                formatName(it.substringBefore("|")),
                context.resources.getIdentifier(
                    it.replace("|", "_"),
                    DEF_TYPE,
                    context.packageName
                )
            )
        }
    }

    fun loadSortedCountries(): List<Country> {
        return loadCountries().sortedBy { it.countryName }
    }

    private fun formatName(name: String): String {
        return name.split("_")
            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
    }
}
