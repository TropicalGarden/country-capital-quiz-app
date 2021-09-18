package com.example.countrycapitalquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.countrycapitalquiz.R
import com.example.countrycapitalquiz.adapter.CountryAdapter
import com.example.countrycapitalquiz.constant.Performance
import com.example.countrycapitalquiz.databinding.FragmentResultBinding
import com.example.countrycapitalquiz.model.view.GameViewModel

class ResultFragment : Fragment() {

    private val gameViewModel: GameViewModel by activityViewModels()

    private lateinit var binding: FragmentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customBackNavigation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            resultFragment = this@ResultFragment
            resultRecyclerView.adapter = CountryAdapter(this@ResultFragment.gameViewModel.result)
            gameViewModel = this@ResultFragment.gameViewModel
            performanceText.text = performanceText()
            performanceImage.setImageResource(performanceImage())
        }
    }

    fun done() {
        findNavController().navigate(ResultFragmentDirections.actionResultFragmentToMenuFragment())
    }

    private fun performanceText(): String {
        return when (gameViewModel.calculatePerformance()) {
            Performance.EXCELLENT -> getString(R.string.excellent)
            Performance.GOOD -> getString(R.string.good)
            Performance.MEDIOCRE -> getString(R.string.mediocre)
            else -> getString(R.string.subpar)
        }
    }

    private fun performanceImage(): Int {
        return when (gameViewModel.calculatePerformance()) {
            Performance.EXCELLENT -> R.drawable.trophy_excellent
            Performance.GOOD -> R.drawable.trophy_good
            Performance.MEDIOCRE -> R.drawable.trophy_mediocre
            else -> R.drawable.trophy_subpar
        }
    }

    private fun customBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(ResultFragmentDirections.actionResultFragmentToMenuFragment())
                }
            })
    }
}