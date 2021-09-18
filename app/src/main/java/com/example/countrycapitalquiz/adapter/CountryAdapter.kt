package com.example.countrycapitalquiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.countrycapitalquiz.R
import com.example.countrycapitalquiz.model.Country
import com.example.countrycapitalquiz.model.Result

class CountryAdapter(private val countries: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.ResultViewHolder>() {

    constructor(result: Result) : this(result.answers.keys.toList()) {
        this.result = result
    }

    private var result: Result? = null

    class ResultViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val countryIndex: TextView = view.findViewById(R.id.country_index)
        val countryImage: ImageView = view.findViewById(R.id.item_country_image)
        val countryText: TextView = view.findViewById(R.id.item_country)
        val capitalText: TextView = view.findViewById(R.id.item_capital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.country_item, parent, false)

        return ResultViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val country = countries[position]
        val context = holder.view.context

        holder.countryIndex.text = position.inc().toString()
        holder.countryText.text =
            context.resources.getString(R.string.country_name_item, country.countryName)
        holder.capitalText.text =
            context.resources.getString(R.string.capital_name_item, country.capitalName)
        holder.countryImage.setImageResource(country.imageResourceId)

        if (result != null) {
            holder.countryIndex.setBackgroundColor(
                selectIndexBackgroundColor(result!!.answers[country]!!, context)
            )
        }
    }

    private fun selectIndexBackgroundColor(isAnswerCorrect: Boolean, context: Context): Int {
        return ContextCompat.getColor(
            context,
            if (isAnswerCorrect) {
                R.color.green_400
            } else {
                R.color.red_400
            }
        )
    }

    override fun getItemCount(): Int = countries.size

}