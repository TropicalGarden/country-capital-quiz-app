package com.example.countrycapitalquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.countrycapitalquiz.adapter.CountryAdapter
import com.example.countrycapitalquiz.data.DataSource
import com.example.countrycapitalquiz.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {

    private lateinit var binding: FragmentLearnBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.learnRecyclerView.adapter =
            CountryAdapter(DataSource(requireContext()).loadCountries())
    }

}