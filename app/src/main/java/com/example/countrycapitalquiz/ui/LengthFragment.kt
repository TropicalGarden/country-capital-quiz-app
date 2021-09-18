package com.example.countrycapitalquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.countrycapitalquiz.constant.GameLength
import com.example.countrycapitalquiz.data.DataSource
import com.example.countrycapitalquiz.databinding.FragmentLengthBinding
import com.example.countrycapitalquiz.model.view.GameViewModel

class LengthFragment : Fragment() {

    private val gameViewModel: GameViewModel by activityViewModels()

    private lateinit var binding: FragmentLengthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLengthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lengthFragment = this@LengthFragment
            lengthShort = GameLength.SHORT
            lengthMedium = GameLength.MEDIUM
            lengthLong = GameLength.LONG
        }
    }

    fun startGameFragment(gameLength: GameLength) {
        with(gameViewModel) {
            setLength(gameLength.length)
            setCountries(DataSource(requireContext()).loadCountries())
            nextQuestion()
        }
        findNavController().navigate(LengthFragmentDirections.actionLengthFragmentToGameFragment())
    }

}